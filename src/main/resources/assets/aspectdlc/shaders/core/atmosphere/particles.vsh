#version 150

// частицы атмосферы целиком на видеокарте: буфер с «семенами» загружается один раз,
// позиция каждой частицы считается здесь — в коробке Box вокруг камеры, закреплённой в мире (с повтором)
in vec3 Position; // три случайных числа 0..1 — исходная точка в коробке
in vec2 UV0;      // угол квада
in vec4 Color;    // ещё четыре случайных числа

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform sampler2D Sampler0; // карта высот 64x64 (по модулю мировых координат)

uniform float Time;
uniform float Box;
uniform float Mode;     // 0 — снег, 1 — сакура
uniform float Light;
uniform float Density;  // доля видимых частиц
uniform vec3 CamMod;    // позиция камеры по модулю Box
uniform vec3 CamFrac;   // дробная часть позиции камеры
uniform vec3 CamBlock;  // целая часть позиции камеры по модулю 64
uniform vec3 Wind;      // накопленный снос ветром (по модулю Box)
uniform float HmBase;   // (minY - 1) - camY
uniform float HmOn;

out vec2 uv;
out vec4 tint;
out float kind;   // 0 — снежинка/лепесток, 1 — светящаяся пыльца
out float spark;

const float TAU = 6.2831853;

mat3 rot(vec3 ax, float a) {
    float s = sin(a), c = cos(a), oc = 1.0 - c;
    return mat3(oc * ax.x * ax.x + c, oc * ax.x * ax.y + ax.z * s, oc * ax.z * ax.x - ax.y * s,
                oc * ax.x * ax.y - ax.z * s, oc * ax.y * ax.y + c, oc * ax.y * ax.z + ax.x * s,
                oc * ax.z * ax.x + ax.y * s, oc * ax.y * ax.z - ax.x * s, oc * ax.z * ax.z + c);
}

void hide() {
    gl_Position = vec4(2.0, 2.0, 2.0, 1.0);
    uv = vec2(0.0);
    tint = vec4(0.0);
    kind = 0.0;
    spark = 0.0;
}

void main() {
    vec3 s = Position;
    vec4 r = Color;
    if (r.a > Density) { hide(); return; }

    bool sakura = Mode > 0.5;
    bool mote = sakura && r.b < 0.12;
    float speed, size, swa, swf;
    if (!sakura) {
        // снег: крупные хлопья падают быстрее, мелкие — медленно и долго кружат
        float big = r.r * r.r;
        speed = 0.55 + 1.05 * big + 0.25 * r.g;
        size = 0.045 + 0.12 * big;
        swa = 0.25 + 0.55 * r.g;
        swf = 0.35 + 0.8 * r.b;
    } else if (mote) {
        speed = 0.05 + 0.12 * r.g;
        size = 0.025 + 0.02 * r.r;
        swa = 0.5 + 0.6 * r.r;
        swf = 0.15 + 0.2 * r.g;
    } else {
        speed = 0.32 + 0.45 * r.g;
        size = 0.085 + 0.07 * r.r;
        swa = 0.6 + 0.9 * r.r;
        swf = 0.25 + 0.45 * r.b;
    }

    float ph = r.g * TAU;
    vec3 base = s * Box;
    vec3 mv = vec3(0.0, -speed * Time, 0.0);
    // кружение: у каждой частицы своя медленная «спираль»
    mv.x += sin(Time * swf + ph) * swa + sin(Time * swf * 2.3 + ph * 1.7) * swa * 0.35;
    mv.z += cos(Time * swf * 0.8 + ph * 1.3) * swa;
    if (mote) mv.y += sin(Time * 0.7 + ph) * 0.4 + speed * Time * 1.6;
    vec3 wind = Wind * (0.75 + 0.5 * r.r);
    vec3 p = mod(base + mv + wind - CamMod, Box) - Box * 0.5;

    // крыши: частица под поверхностью (по карте высот) не видна; барьеры в карте пропущены
    float edge = 1.0;
    if (HmOn > 0.5 && max(abs(p.x), abs(p.z)) < 31.0) {
        vec2 blk = floor(CamFrac.xz + p.xz) + CamBlock.xz;
        ivec2 tc = ivec2(mod(blk, 64.0));
        vec4 hv = texelFetch(Sampler0, tc, 0);
        float enc = floor(hv.r * 255.0 + 0.5) + floor(hv.g * 255.0 + 0.5) * 256.0;
        if (enc > 0.5) {
            float surf = enc + HmBase;
            edge = clamp((p.y - surf) / 0.25, 0.0, 1.0);
        }
    }

    // мягко появляются у границ коробки и не лезут в камеру вплотную
    float d = length(p);
    // лепестки крупные — у самой камеры растворяются раньше, чтобы не закрывать обзор
    float near = sakura && !mote ? 2.6 : 1.1;
    float fade = (1.0 - smoothstep(Box * 0.5 - 6.0, Box * 0.5 - 0.5, d)) * smoothstep(near * 0.45, near, d) * edge;
    if (fade < 0.004) { hide(); return; }

    vec2 c = UV0 * 2.0 - 1.0;
    vec4 vp;
    if (sakura && !mote) {
        // лепесток кувыркается в пространстве — то плашмя, то ребром
        vec3 ax = normalize(vec3(r.r, r.g, r.b) - 0.5 + vec3(0.0, 0.001, 0.0));
        mat3 m = rot(ax, Time * (1.2 + 1.6 * r.a) + ph);
        vec3 q = m * vec3(c.x * size, c.y * size * 1.15, 0.0);
        vp = ModelViewMat * vec4(p + q, 1.0);
    } else {
        vp = ModelViewMat * vec4(p, 1.0);
        vp.xy += c * size;
    }
    gl_Position = ProjMat * vp;
    uv = c;
    kind = mote ? 1.0 : 0.0;
    spark = r.b > 0.9 ? 0.5 + 0.5 * sin(Time * (5.0 + 4.0 * r.r) + ph * 3.0) : 0.0;
    tint = vec4(vec3(Light), fade);
}
