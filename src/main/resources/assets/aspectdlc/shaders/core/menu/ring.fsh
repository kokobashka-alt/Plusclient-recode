#version 150

// кольцо прогресса: дорожка + дуга от 12 часов по часовой, концы скруглены, цвет темы плавно идёт вдоль дуги
uniform vec2 Size;
uniform float Px;
uniform float Thick;
uniform float Progress;
uniform float Track;
uniform vec4 Theme1;
uniform vec4 Theme2;
uniform vec4 ColorModulator;

in vec2 fragcoord;
in vec4 fragcolor;

out vec4 outcolor;

const float TAU = 6.28318530718;

float cov(float d, float aa) {
    return 1.0 - smoothstep(-aa, aa, d);
}

void main() {
    vec2 p = (fragcoord - 0.5) * Size;
    float aa = max(Px, 0.2) * 0.75;
    float hw = Thick * 0.5;
    float r = min(Size.x, Size.y) * 0.5 - hw - Px;
    float ring = abs(length(p) - r) - hw;

    // угол от верха по часовой (ось y смотрит вниз), 0..1
    float t = atan(p.x, -p.y) / TAU;
    if (t < 0.0) t += 1.0;

    float pr = clamp(Progress, 0.0, 1.0);
    float arc;
    // позиция вдоль дуги для цвета: за пределами дуги (на скруглённых концах) — цвет ближайшего конца
    float tc = clamp(t / max(pr, 0.001), 0.0, 1.0);
    if (pr >= 0.999) {
        arc = ring;
    } else {
        float ea = pr * TAU;
        vec2 c0 = vec2(0.0, -r);
        vec2 c1 = vec2(sin(ea), -cos(ea)) * r;
        float d0 = length(p - c0);
        float d1 = length(p - c1);
        float cap = min(d0, d1) - hw;
        arc = t <= pr ? min(ring, cap) : cap;
        if (t > pr) tc = d0 < d1 ? 0.0 : 1.0;
    }
    float aA = pr > 0.0005 ? cov(arc, aa) : 0.0;
    float tA = cov(ring, aa) * Track;

    // к полному кругу градиент перетекает в замкнутый (сверху Theme1, снизу Theme2) — без шва на 12 часах
    tc = mix(tc, 0.5 - 0.5 * cos(t * TAU), smoothstep(0.9, 1.0, pr));
    vec3 ac = mix(Theme1.rgb, Theme2.rgb, tc);
    float a = aA + tA * (1.0 - aA);
    vec3 col = a > 0.0001 ? (ac * aA + vec3(1.0) * tA * (1.0 - aA)) / a : ac;
    outcolor = vec4(col, a * fragcolor.a) * ColorModulator;
}
