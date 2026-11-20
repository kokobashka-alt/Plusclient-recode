#version 150

// «Стиль» — картинка в духе шейдерпаков (своя реализация, не копия): идёт после руки, перед интерфейсом.
// Style 1 — Complementary: тёплый солнечный свет и голубоватые тени, плёночная кривая с мягким плечом, живые цвета,
//           светлая обводка рёбер мира, слабое свечение, резкость, мягкая дымка к краю прорисовки.
// Style 2 — Sildur's: «кросс-процесс» — тёплый сочный фильтр с мягким сжатием светов, без тумана.
// В обоих — motion blur по движению камеры (из глубины и матриц прошлого кадра); рука не размывается и не обводится
uniform sampler2D Sampler0; // кадр с рукой
uniform sampler2D Sampler1; // глубина мира (до руки)
uniform sampler2D Sampler2; // глубина руки (после очистки — только рука)
uniform sampler2D Sampler3; // свечение (¼ разрешения)

uniform mat4 InvPV;
uniform mat4 PrevPV;
uniform vec3 CamDelta;     // смещение камеры с прошлого кадра
uniform vec2 Texel;
uniform float Style;
uniform float Blur;        // сила motion blur (0 — выкл)
uniform float Day;
uniform float Night;
uniform float Sunset;
uniform vec3 FogCol;
uniform float ViewDist;    // дальность прорисовки в блоках
uniform float Bloom;
uniform float Sharp;
uniform float Outline;

in vec2 uv;

out vec4 outcolor;

vec3 recon(vec2 t, float d) {
    vec4 w = InvPV * vec4(t * 2.0 - 1.0, d * 2.0 - 1.0, 1.0);
    return w.xyz / w.w;
}

float luma(vec3 c) {
    return dot(c, vec3(0.2126, 0.7152, 0.0722));
}

vec3 toLin(vec3 c) {
    return pow(max(c, 0.0), vec3(2.2));
}

vec3 toSrgb(vec3 c) {
    return pow(max(c, 0.0), vec3(1.0 / 2.2));
}

// плёночная кривая: степенной «носок» (контраст) и мягкое плечо; белый остаётся белым, средний серый почти на месте
vec3 filmic(vec3 x, float contrast) {
    const float k = 5.35;
    vec3 xa = pow(max(x, 0.0), vec3(contrast));
    return (1.0 + k) * xa / (xa + k);
}

void main() {
    vec3 col = texture(Sampler0, uv).rgb;
    float hd = texture(Sampler2, uv).r;
    bool hand = hd < 0.99999;
    float d = texture(Sampler1, uv).r;
    bool sky = d >= 0.99999;

    // ---------- motion blur ----------
    if (Blur > 0.0 && !hand) {
        vec3 p = recon(uv, sky ? 0.9999 : d);
        vec4 pc = PrevPV * vec4(p + CamDelta, 1.0);
        if (pc.w > 0.001) {
            vec2 prev = pc.xy / pc.w * 0.5 + 0.5;
            vec2 vel = uv - prev;
            if (Style < 1.5) vel = vel / (1.0 + length(vel)) * Blur;
            else vel = vel * 0.6 * Blur;
            float len = length(vel / Texel);
            if (len > 0.75) {
                vec3 acc = col;
                float wsum = 1.0;
                // выборок тем больше, чем длиннее размытие — на большой силе без «двоения»
                int n = int(clamp(len / 2.5, 8.0, 32.0));
                for (int i = 1; i <= 32; i++) {
                    if (i > n) break;
                    vec2 c = uv - vel * (float(i) / float(n) - 0.5);
                    if (c.x < 0.0 || c.y < 0.0 || c.x > 1.0 || c.y > 1.0) continue;
                    // рука в кадре не размазывается в мир
                    if (texture(Sampler2, c).r < 0.99999) continue;
                    acc += texture(Sampler0, c).rgb;
                    wsum += 1.0;
                }
                col = acc / wsum;
            }
        }
    }

    if (Style < 1.5) {
        // ---------- Complementary ----------
        // резкость: усиливаем отличие от соседей
        if (Sharp > 0.0) {
            vec3 n = texture(Sampler0, uv + vec2(Texel.x, 0.0)).rgb + texture(Sampler0, uv - vec2(Texel.x, 0.0)).rgb
                   + texture(Sampler0, uv + vec2(0.0, Texel.y)).rgb + texture(Sampler0, uv - vec2(0.0, Texel.y)).rgb;
            vec3 sh = col + (col - n * 0.25) * Sharp;
            col = clamp(sh, min(col, n * 0.25) * 0.85, max(col, n * 0.25) * 1.15 + 0.02);
        }
        // светлая обводка рёбер: выпуклые края по глубине чуть подсвечиваются
        if (Outline > 0.0 && !hand && !sky) {
            float z = length(recon(uv, d));
            float inv = 0.0;
            inv += 1.0 / length(recon(uv + Texel * vec2(-1.0, -1.0), texture(Sampler1, uv + Texel * vec2(-1.0, -1.0)).r));
            inv += 1.0 / length(recon(uv + Texel * vec2(-1.0, 1.0), texture(Sampler1, uv + Texel * vec2(-1.0, 1.0)).r));
            inv += 1.0 / length(recon(uv + Texel * vec2(1.0, -1.0), texture(Sampler1, uv + Texel * vec2(1.0, -1.0)).r));
            inv += 1.0 / length(recon(uv + Texel * vec2(1.0, 1.0), texture(Sampler1, uv + Texel * vec2(1.0, 1.0)).r));
            float convex = (1.0 - z * inv * 0.25) * 2000.0;
            float o = clamp(convex, 0.0, 1.0) * Outline * 0.25 * (1.0 - smoothstep(ViewDist * 0.5, ViewDist * 0.9, z));
            col += min(col * o, vec3(o));
        }

        vec3 lin = toLin(col);
        float l = luma(lin);
        // свет и тень: днём тёплое солнце и голубоватые тени, на закате — оранжевее, ночью — холодная синь
        vec3 sunT = mix(vec3(1.10, 1.0, 0.84), vec3(1.22, 0.92, 0.70), Sunset);
        vec3 ambT = mix(vec3(0.86, 0.95, 1.12), vec3(0.95, 0.88, 1.06), Sunset);
        vec3 nightT = vec3(0.84, 0.92, 1.16);
        vec3 tint = mix(ambT, sunT, smoothstep(0.03, 0.45, l));
        tint = mix(tint, nightT, Night);
        lin *= tint;
        // мягкая дымка к краю прорисовки — вместо обрезанного ванильного тумана
        if (!hand && !sky) {
            float z = length(recon(uv, d));
            float f = pow(clamp(z / max(ViewDist, 16.0), 0.0, 1.0), 2.6) * 0.92;
            lin = mix(lin, toLin(FogCol), f);
        }
        // плёночная кривая (контраст 1.05) и путь к белому
        lin = filmic(lin * 1.08, 1.05);
        col = toSrgb(lin);
        // насыщенность: живые цвета, тусклые — чуть сильнее; в самых тёмных местах — лёгкое обесцвечивание
        float lc = luma(col);
        float sat = max(col.r, max(col.g, col.b)) - min(col.r, min(col.g, col.b));
        col = mix(vec3(lc), col, 1.12 + 0.18 * (1.0 - sat));
        col = mix(col, vec3(lc), smoothstep(0.12, 0.0, lc) * 0.25);
        // слабое свечение светлых мест
        if (Bloom > 0.0) col += texture(Sampler3, uv).rgb * Bloom;
    } else {
        // ---------- Sildur's ----------
        // кросс-процесс: красный вперёд, лёгкое вычитание соседних каналов, затем мягкое сжатие
        vec3 c = col;
        float r = c.r * 1.34 - (c.g + c.b) * 0.1;
        float g = c.g * 1.2 - (r + c.b) * 0.1;
        float b = c.b * 1.1 - (r + g) * 0.1;
        c = vec3(r, g, b);
        c = c / (c + 2.2) * 3.0;
        col = c;
    }

    outcolor = vec4(clamp(col, 0.0, 1.0), 1.0);
}
