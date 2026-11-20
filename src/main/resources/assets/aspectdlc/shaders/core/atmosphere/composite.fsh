#version 150

// главный проход атмосферы — читает копию кадра (цвет + глубина) и пишет итог:
// 1) покрытие мира: на всё, что смотрит вверх и открыто небу (по карте высот), ложится снег с искрами на насте
//    или рассыпаются лепестки сакуры (под кронами деревьев — тоже);
// 2) своё небо: градиент по времени суток, солнце с гало, луна, звёзды, северное сияние;
// 3) атмосферная перспектива: даль и низины растворяются в цвете горизонта, со стороны солнца — теплее;
// 4) цветокоррекция: тени и света в разные оттенки, насыщенность, кривая; 5) иней по краям, виньетка
uniform sampler2D Sampler0; // цвет кадра
uniform sampler2D Sampler1; // глубина кадра
uniform sampler2D Sampler2; // карта высот 64x64: r,g — высота поверхности, b — сверху листва (255) / вода (128)
uniform sampler2D Sampler3; // объёмный туман (¼ разрешения): rgb — свет, a — непрозрачность
uniform sampler2D Sampler4; // лучи света (¼ разрешения)

uniform mat4 InvPV;
uniform vec2 Texel;
uniform float Time;
uniform float Mode;       // 0 — зима, 1 — сакура
uniform float Amount;     // плавное появление
uniform float Outdoor;    // 0 — под землёй / в закрытом помещении
uniform vec4 LayersA;     // покрытие, небо, дымка, цветокоррекция
uniform vec4 LayersB;     // иней, сияние, искры, -
uniform vec3 CamFrac;
uniform vec3 CamBlock;    // целая часть позиции камеры по модулю 4096
uniform float HmBase;     // (minY - 1) - camY
uniform vec3 SunDir;
uniform float Day;
uniform float Night;
uniform vec3 SkyZen;
uniform vec3 SkyHor;
uniform vec3 SunCol;
uniform vec3 ShadowTint;
uniform vec3 HighTint;
uniform vec3 Lift;
uniform vec4 Grade;       // насыщенность, контраст, гамма, экспозиция
uniform vec4 Haze;        // плотность у ног, спад по высоте, дальняя дымка, максимум
uniform float Cover;      // сила покрытия
uniform float Aspect;
uniform float PixAng;     // угол одного пикселя (для сглаживания узоров вдали)
uniform vec4 LayersC;     // объёмный туман, лучи
uniform vec3 RayCol;
uniform vec2 QTexel;      // тексель текстур в ¼ разрешения
uniform vec2 WindDir;

in vec2 uv;

out vec4 outcolor;

float hash12(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.x + p3.y) * p3.z);
}

vec3 hash32(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * vec3(0.1031, 0.1030, 0.0973));
    p3 += dot(p3, p3.yxz + 33.33);
    return fract((p3.xxy + p3.yzz) * p3.zyx);
}

float hash13(vec3 p3) {
    p3 = fract(p3 * 0.1031);
    p3 += dot(p3, p3.zyx + 31.32);
    return fract((p3.x + p3.y) * p3.z);
}

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    f = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash12(i), hash12(i + vec2(1.0, 0.0)), f.x), mix(hash12(i + vec2(0.0, 1.0)), hash12(i + vec2(1.0, 1.0)), f.x), f.y);
}

float fbm(vec2 p, int oct) {
    float v = 0.0, a = 0.5;
    for (int i = 0; i < oct; i++) {
        v += noise(p) * a;
        p = p * 2.03 + vec2(1.7, 9.2);
        a *= 0.5;
    }
    return v;
}

float luma(vec3 c) {
    return dot(c, vec3(0.2126, 0.7152, 0.0722));
}

vec3 recon(vec2 t, float d) {
    vec4 w = InvPV * vec4(t * 2.0 - 1.0, d * 2.0 - 1.0, 1.0);
    return w.xyz / w.w;
}

// лепесток в локальных координатах (-1..1): узкое основание, скруглённая вершина с выемкой
float petal(vec2 p, float aa) {
    float t = clamp((p.y + 1.0) * 0.5, 0.0, 1.0);
    float hw = 0.62 * pow(sin(3.14159 * pow(t, 0.72)), 0.62);
    float body = smoothstep(-aa, aa, hw - abs(p.x));
    float notch = smoothstep(-aa, aa, p.y - (0.70 + 1.25 * abs(p.x)));
    return body * (1.0 - notch) * step(abs(p.y), 1.0);
}

vec3 skyColor(vec3 dir) {
    float h = clamp(dir.y, -1.0, 1.0);
    vec3 c = mix(SkyHor, SkyZen, pow(clamp(h, 0.0, 1.0), 0.55));
    if (h < 0.0) c = mix(SkyHor, SkyHor * 0.82, clamp(-h * 3.0, 0.0, 1.0));
    return c;
}

void main() {
    vec3 scene = texture(Sampler0, uv).rgb;
    float depth = texture(Sampler1, uv).r;
    bool sky = depth >= 0.99999;
    vec3 pos = recon(uv, sky ? 0.9999 : depth);
    float dist = length(pos);
    vec3 dir = pos / max(dist, 0.0001);
    if (sky) dist = 1000.0;
    float sunA = dot(dir, SunDir);
    vec3 col = scene;
    float lum0 = luma(scene);

    // ---------- 1. покрытие мира ----------
    if (!sky && LayersA.x > 0.5 && dist < 110.0 && Cover > 0.01) {
        // нормаль по глубине: из соседних пикселей берём ближнюю разность — без ореолов на краях блоков
        float dR = texture(Sampler1, uv + vec2(Texel.x, 0.0)).r;
        float dL = texture(Sampler1, uv - vec2(Texel.x, 0.0)).r;
        float dU = texture(Sampler1, uv + vec2(0.0, Texel.y)).r;
        float dD = texture(Sampler1, uv - vec2(0.0, Texel.y)).r;
        vec3 ddx = abs(dR - depth) < abs(depth - dL) ? recon(uv + vec2(Texel.x, 0.0), dR) - pos : pos - recon(uv - vec2(Texel.x, 0.0), dL);
        vec3 ddy = abs(dU - depth) < abs(depth - dD) ? recon(uv + vec2(0.0, Texel.y), dU) - pos : pos - recon(uv - vec2(0.0, Texel.y), dD);
        vec3 n = normalize(cross(ddx, ddy));
        if (dot(n, dir) > 0.0) n = -n;
        float up = smoothstep(0.62, 0.92, n.y);

        if (up > 0.001) {
            // мировые координаты (по модулю 4096) — узоры привязаны к миру и не плавают
            vec3 wp = vec3(CamBlock.x, 0.0, CamBlock.z) + CamFrac + pos;
            float exposed = 1.0;
            float leaves = 0.0;
            float water = 0.0;
            if (max(abs(pos.x), abs(pos.z)) < 31.0) {
                vec2 blk = floor(CamFrac.xz + pos.xz) + CamBlock.xz;
                vec4 hv = texelFetch(Sampler2, ivec2(mod(blk, 64.0)), 0);
                float enc = floor(hv.r * 255.0 + 0.5) + floor(hv.g * 255.0 + 0.5) * 256.0;
                leaves = hv.b > 0.75 ? 1.0 : 0.0;
                water = hv.b > 0.3 && hv.b < 0.75 ? 1.0 : 0.0;
                if (enc > 0.5) exposed = smoothstep(-0.7, -0.08, pos.y - (enc + HmBase));
            }
            float fade = 1.0 - smoothstep(70.0, 110.0, dist);

            if (Mode < 0.5) {
                // снег: мягкий слой с лёгкой неровностью, в тени голубоватый; искры на насте
                float patchy = smoothstep(0.18, 0.5, fbm(wp.xz * 0.35, 3) + 0.22);
                // на воде снег не лежит
                float m = up * exposed * fade * Cover * (0.82 + 0.18 * patchy) * (1.0 - water);
                vec3 shade = mix(vec3(0.58, 0.68, 0.88), vec3(1.0, 1.0, 1.0), smoothstep(0.08, 0.62, lum0));
                vec3 snow = vec3(0.94, 0.96, 1.0) * shade * clamp(0.22 + 1.25 * lum0, 0.0, 1.08);
                col = mix(col, snow, m);
                if (LayersB.z > 0.5 && dist < 36.0 && m > 0.01) {
                    // искры на насте — точки, вспыхивают и гаснут при повороте камеры
                    vec2 sg = wp.xz * 6.0;
                    vec2 ci = floor(sg);
                    vec3 h = hash32(ci + floor(wp.y * 6.0) * 17.0);
                    if (h.x > 0.93) {
                        float d = length(fract(sg) - (0.2 + 0.6 * h.yz));
                        float px = dist * PixAng * 6.0 / max(n.y, 0.35);
                        float dotm = smoothstep(max(px * 1.2, 0.06), 0.0, d);
                        float tw = pow(max(0.0, sin(h.y * 431.0 + dot(dir, vec3(23.0, 41.0, 17.0)) * 7.0)), 14.0);
                        col += vec3(0.95, 0.97, 1.0) * dotm * tw * m * (0.35 + 1.6 * Day) * (1.0 - dist / 36.0) * 2.2;
                    }
                }
            } else {
                // лепестки: рассыпаны по сетке мира, у каждого свой поворот, размер и оттенок; под кронами гуще
                float dens = mix(0.28, 0.62, leaves) * Cover;
                float m = up * max(exposed, leaves) * fade;
                if (m > 0.01) {
                    float pa = 0.0;
                    vec3 pc = vec3(0.0);
                    if (dist < 30.0) {
                        // на воде лепестки медленно дрейфуют по ветру
                        vec2 g = (wp.xz - WindDir * Time * 0.06 * water) / 0.3;
                        vec2 gi = floor(g) - (1.0 - step(0.5, fract(g)));
                        // размер пикселя в координатах лепестка: на косых поверхностях и вдали края мягче
                        float aa = dist * PixAng / 0.3 / 0.4 / max(n.y, 0.3) * 1.3 + 0.03;
                        for (int j = 0; j < 4; j++) {
                            vec2 c = gi + vec2(float(j & 1), float(j >> 1));
                            vec3 h = hash32(c);
                            if (h.x > dens) continue;
                            vec2 o = g - c - (0.25 + 0.5 * h.yz);
                            float an = h.y * 6.2831;
                            vec2 q = mat2(cos(an), -sin(an), sin(an), cos(an)) * o / (0.32 + 0.16 * h.z);
                            float a = petal(q, aa);
                            if (a > pa) {
                                pa = a;
                                pc = mix(vec3(0.97, 0.62, 0.76), vec3(1.0, 0.90, 0.94), h.z);
                            }
                        }
                        pa *= 1.0 - smoothstep(18.0, 30.0, dist);
                    }
                    // вдали — просто розоватый налёт той же плотности
                    float far = smoothstep(14.0, 30.0, dist) * dens * 0.55;
                    vec3 lit = clamp(0.3 + 1.15 * lum0, 0.0, 1.05) * vec3(1.0);
                    col = mix(col, vec3(1.0, 0.78, 0.86) * lit, far * m);
                    col = mix(col, pc * lit, pa * m * 0.95);
                }
            }
        }
    }

    // ---------- 2. небо ----------
    if (sky && LayersA.y > 0.5) {
        vec3 sc = skyColor(dir);
        // солнце: диск, тёплое гало и широкое рассеяние; луна холодная
        float sa = max(sunA, 0.0);
        sc += SunCol * (smoothstep(0.99935, 0.99965, sunA) * 4.0 + pow(sa, 380.0) * 0.9 + pow(sa, 10.0) * 0.22) * Day;
        float ma = max(-sunA, 0.0);
        sc += vec3(0.80, 0.86, 1.0) * (smoothstep(0.99955, 0.99975, -sunA) * 1.4 + pow(ma, 220.0) * 0.25) * Night;
        // звёзды: точки на сфере, мерцают
        if (Night > 0.02 && dir.y > -0.05) {
            vec3 sd = dir * 260.0;
            vec3 ci = floor(sd);
            float h = hash13(ci);
            if (h > 0.9965) {
                float d = length(fract(sd) - 0.5);
                float tw = 0.6 + 0.4 * sin(Time * (1.5 + h * 3.0) + h * 80.0);
                sc += vec3(0.9, 0.93, 1.0) * smoothstep(0.32, 0.0, d) * tw * Night * smoothstep(-0.05, 0.25, dir.y) * 1.3;
            }
        }
        // северное сияние — зима, ночь
        if (Mode < 0.5 && LayersB.y > 0.5 && Night > 0.02 && dir.y > 0.02) {
            vec2 q = dir.xz / (dir.y + 0.18);
            float t = Time * 0.04;
            float acc = 0.0;
            for (int i = 0; i < 3; i++) {
                float fi = float(i);
                float n = fbm(q * 0.45 + vec2(t * (1.0 + fi * 0.3), fi * 3.1), 2);
                float band = q.y * 0.45 + 0.55 * sin(q.x * 0.6 + n * 2.6 + fi * 1.3 + t * 2.0) - 0.35 + fi * 0.4;
                float curtain = exp(-band * band * 7.0);
                float rays = 0.55 + 0.45 * noise(vec2(q.x * 9.0 + n * 3.0, t * 6.0 + fi));
                acc += curtain * rays;
            }
            vec3 ac = mix(vec3(0.15, 1.0, 0.6), vec3(0.55, 0.35, 1.0), clamp(dir.y * 1.4 + 0.1, 0.0, 1.0));
            sc += ac * acc * smoothstep(0.02, 0.3, dir.y) * Night * 0.5;
        }
        col = mix(scene, sc, 0.92);
    }

    // ---------- 3. атмосферная перспектива ----------
    if (LayersA.z > 0.5 && !sky) {
        float b = Haze.y;
        float dy = dir.y;
        float base = Haze.x * exp(-b * 1.6);
        float integ = abs(dy) < 0.001 ? base * dist : base * (1.0 - exp(-b * dy * dist)) / (b * dy);
        float fh = 1.0 - exp(-max(integ, 0.0));
        float fd = 1.0 - exp(-dist * Haze.z);
        float fog = min(1.0 - (1.0 - fh) * (1.0 - fd), Haze.w) * Outdoor;
        vec3 hc = skyColor(vec3(dir.x, max(dir.y, 0.0) * 0.35, dir.z)) + SunCol * pow(max(sunA, 0.0), 8.0) * 0.45 * Day;
        col = mix(col, hc, fog);
    }

    // ---------- 3б. объёмный туман и лучи (растянуты из ¼ разрешения с мягким сглаживанием) ----------
    if (LayersC.x > 0.5) {
        vec4 v = texture(Sampler3, uv) * 0.4
                + texture(Sampler3, uv + vec2(QTexel.x, QTexel.y) * 0.75) * 0.15
                + texture(Sampler3, uv + vec2(-QTexel.x, QTexel.y) * 0.75) * 0.15
                + texture(Sampler3, uv + vec2(QTexel.x, -QTexel.y) * 0.75) * 0.15
                + texture(Sampler3, uv + vec2(-QTexel.x, -QTexel.y) * 0.75) * 0.15;
        col = col * (1.0 - v.a) + v.rgb;
    }
    if (LayersC.y > 0.5) {
        float r = texture(Sampler4, uv).r;
        col += RayCol * r;
    }

    // ---------- 4. цветокоррекция ----------
    if (LayersA.w > 0.5) {
        float l = luma(col);
        col *= mix(ShadowTint, HighTint, smoothstep(0.04, 0.72, l));
        col = mix(vec3(luma(col)), col, Grade.x);
        col = max(col, 0.0);
        col = pow(col * Grade.w, vec3(Grade.z));
        // мягкая S-кривая контраста вокруг середины
        vec3 sc = col * col * (3.0 - 2.0 * clamp(col, 0.0, 1.0));
        col = mix(col, sc, clamp(Grade.y - 1.0, -1.0, 1.0));
        col += Lift * (1.0 - clamp(col, 0.0, 1.0));
        vec2 v = (uv - 0.5) * vec2(Aspect / 1.6, 1.0);
        col *= 1.0 - 0.2 * smoothstep(0.35, 0.95, length(v) * 1.25);
    }

    // ---------- 5. иней по краям экрана ----------
    if (Mode < 0.5 && LayersB.x > 0.5) {
        vec2 sc = (uv - 0.5) * vec2(Aspect, 1.0);
        float e = smoothstep(0.55, 1.05, length(sc * vec2(0.62, 1.0)) * 1.25) * Outdoor;
        if (e > 0.002) {
            float f = fbm(uv * vec2(Aspect, 1.0) * 14.0, 3);
            float g = noise(uv * vec2(Aspect, 1.0) * 52.0 + 3.0);
            float frost = e * smoothstep(0.42, 0.78, f + e * 0.35) * (0.55 + 0.45 * g) * 0.5;
            col = mix(col, vec3(0.86, 0.93, 1.0) * (0.55 + 0.45 * Day), frost);
        }
    }

    outcolor = vec4(mix(scene, col, Amount), 1.0);
}
