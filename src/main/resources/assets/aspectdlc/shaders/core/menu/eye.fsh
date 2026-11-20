#version 150

uniform vec2 Size;
uniform float Px;
uniform vec2 Look;
uniform float Blink;
uniform vec4 Theme1;
uniform vec4 Theme2;
uniform vec4 ColorModulator;

in vec2 fragcoord;
in vec4 fragcolor;

out vec4 outcolor;

// миндаль (линза) — пересечение двух окружностей: полуширина a, полувысота b
float lens(vec2 p, float a, float b) {
    float r = (a * a + b * b) / (2.0 * b);
    float d1 = length(p - vec2(0.0, b - r)) - r;
    float d2 = length(p - vec2(0.0, r - b)) - r;
    return max(d1, d2);
}

float cov(float d, float aa) {
    return 1.0 - smoothstep(-aa, aa, d);
}

void main() {
    vec2 hs = Size * 0.5;
    vec2 p = (fragcoord - 0.5) * Size;
    float aa = max(Px, 0.2) * 0.9;

    float th = max(Px * 1.6, Size.y * 0.075);
    float a = hs.x - th;
    float b0 = hs.y - th;
    // при полностью закрытых веках верх и низ сходятся в одну ровную линию толщиной с контур
    float b = max(b0 * (1.0 - Blink), Px * 0.35);

    vec3 grd = mix(Theme1.rgb, Theme2.rgb, fragcoord.x);
    vec3 lit = mix(grd, vec3(1.0), 0.30);

    // контур
    float ld = lens(p, a, b);
    float out1 = cov(abs(ld) - th * 0.5, aa);

    // радужка и зрачок живут внутри век и смещаются за взглядом
    float inside = cov(ld + th * 0.35, aa);
    vec2 c = Look * vec2(a * 0.30, b0 * 0.20);
    float dc = length(p - c);
    float ri = b0 * 0.62;
    float ring = cov(abs(dc - ri) - th * 0.42, aa) * inside;
    float disk = cov(dc - ri, aa) * inside;
    float rp = b0 * 0.24;
    float pup = cov(dc - rp, aa) * inside;
    float spec = cov(length(p - c - vec2(-rp * 0.9, -rp * 0.9)) - rp * 0.32, aa) * inside;

    vec3 col = grd * 0.0;
    float alp = 0.0;

    // мягкая заливка радужки для глубины
    col = mix(col, grd, disk * 0.22);
    alp = max(alp, disk * 0.22);

    col = mix(col, grd, ring);
    alp = max(alp, ring);

    col = mix(col, lit, pup);
    alp = max(alp, pup);

    col = mix(col, vec3(1.0), spec * 0.9);
    alp = max(alp, spec * 0.9);

    col = mix(col, lit, out1);
    alp = max(alp, out1);

    if (alp < 0.002) discard;
    outcolor = vec4(col, alp * fragcolor.a) * ColorModulator;
}
