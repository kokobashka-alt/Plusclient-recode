#version 150

// объёмный туман в четверть разрешения: короткий проход лучом сквозь слой тумана над землёй (по карте высот).
// плотность — клочья из 3D-шума, которые медленно катятся по ветру; зимой у самой земли — позёмка струйками.
// свет: рассеяние вперёд со стороны солнца (на просвет туман светится) + мягкий свет неба.
// на выходе rgb — рассеянный свет (уже умножен на прозрачность), a — непрозрачность тумана
uniform sampler2D Sampler0; // запечённый 3D-шум: 32 слоя 32x32 в ряд (с отступом 1px), r — крупный, g — мелкий
uniform sampler2D Sampler1; // глубина кадра
uniform sampler2D Sampler2; // карта высот

uniform mat4 InvPV;
uniform vec3 CamFrac;
uniform vec3 CamBlock;
uniform float HmBase;
uniform float Time;
uniform float Mode;
uniform vec3 SunDir;
uniform vec3 SunCol;
uniform vec3 Ambient;
uniform float Day;
uniform float Night;
uniform vec4 Fog;      // плотность, высота слоя, позёмка, дальность
uniform vec4 Wind;     // направление (xz) и накопленный снос (xz)
uniform float Steps;
uniform float Amount;

in vec2 uv;

out vec4 outcolor;

float hash13(vec3 p3) {
    p3 = fract(p3 * 0.1031);
    p3 += dot(p3, p3.zyx + 31.32);
    return fract((p3.x + p3.y) * p3.z);
}

// трилинейная выборка шума: два текселя-слоя (билинейно железом) и смешивание между ними; p — в текселях
vec2 n3(vec3 p) {
    vec3 m = mod(p, 32.0);
    float z0 = floor(m.z);
    float z1 = mod(z0 + 1.0, 32.0);
    vec2 inv = vec2(1.0 / 1088.0, 1.0 / 34.0);
    vec2 a = texture(Sampler0, (vec2(z0 * 34.0 + 1.0, 1.0) + m.xy) * inv).rg;
    vec2 b = texture(Sampler0, (vec2(z1 * 34.0 + 1.0, 1.0) + m.xy) * inv).rg;
    return mix(a, b, m.z - z0);
}

vec3 recon(vec2 t, float d) {
    vec4 w = InvPV * vec4(t * 2.0 - 1.0, d * 2.0 - 1.0, 1.0);
    return w.xyz / w.w;
}

float ground(vec3 p) {
    if (max(abs(p.x), abs(p.z)) < 31.0) {
        vec2 blk = floor(CamFrac.xz + p.xz) + CamBlock.xz;
        vec4 hv = texelFetch(Sampler2, ivec2(mod(blk, 64.0)), 0);
        float enc = floor(hv.r * 255.0 + 0.5) + floor(hv.g * 255.0 + 0.5) * 256.0;
        if (enc > 0.5) return enc + HmBase;
    }
    return -1.6;
}

void main() {
    float depth = texture(Sampler1, uv).r;
    bool sky = depth >= 0.99999;
    vec3 pos = recon(uv, sky ? 0.9999 : depth);
    float dist = sky ? 1000.0 : length(pos);
    vec3 dir = pos / max(length(pos), 0.0001);
    float tmax = min(dist, Fog.w);
    int n = int(Steps);
    float dt = tmax / float(n);
    // неподвижный «шахматный» сдвиг старта — шаги не видны полосами, а размытие при растягивании сглаживает
    float ign = fract(52.9829189 * fract(dot(gl_FragCoord.xy, vec2(0.06711056, 0.00583715))));
    float t = dt * ign;

    // фазовая функция Хеньи-Гринштейна: со стороны солнца туман ярче
    float mu = dot(dir, SunDir);
    float g = 0.6;
    float hg = (1.0 - g * g) / pow(1.0 + g * g - 2.0 * g * mu, 1.5) * 0.5;
    float hgm = (1.0 - g * g) / pow(1.0 + g * g + 2.0 * g * mu, 1.5) * 0.5;
    vec3 lc = Ambient * 0.62 + SunCol * Day * min(hg, 5.0) * 0.32 + vec3(0.55, 0.62, 0.85) * Night * min(hgm, 4.0) * 0.06;

    vec2 wd = Wind.xy;
    vec2 wp2 = vec2(-wd.y, wd.x);
    vec3 base = vec3(CamBlock.x, 0.0, CamBlock.z) + CamFrac;
    float T = 1.0;
    vec3 L = vec3(0.0);
    for (int i = 0; i < 16; i++) {
        if (i >= n) break;
        vec3 p = dir * t;
        vec3 w = base + p;
        float h = p.y - ground(p);
        float hh = max(h, 0.0);
        // клочья тумана: крупный шум, медленно плывёт по ветру и чуть «дышит»
        vec2 nb = n3(w * 0.3 + vec3(Wind.z, Time * 0.015, Wind.w) * 0.3);
        float bank = clamp(nb.x * 1.15 + nb.y * 0.55 - 0.62, 0.0, 1.0);
        float d = Fog.x * exp(-hh / Fog.y) * bank;
        if (Mode < 0.5 && Fog.z > 0.0) {
            // позёмка: тонкие струи у земли, вытянутые по ветру и быстро бегущие
            float along = dot(w.xz, wd) * 0.35 - Time * 2.4;
            float across = dot(w.xz, wp2) * 1.6;
            float s = smoothstep(0.52, 0.86, n3(vec3(across, w.y * 1.8, along) * 4.0).x);
            d += Fog.z * exp(-hh / 0.42) * s;
        }
        float ext = d * dt;
        L += T * ext * lc;
        T *= exp(-ext);
        t += dt;
    }
    float a = (1.0 - T) * Amount;
    outcolor = vec4(L * Amount, a);
}
