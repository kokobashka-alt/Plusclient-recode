#version 150

// заливка включённой функции одним куском: шапка (Head) сплошная, ниже цвет плавно «стекает» на Tail и растворяется;
// углы сверху RadTop, снизу RadBot; цвет темы слева направо
uniform vec2 Size;
uniform float Px;
uniform float Head;
uniform float Tail;
uniform float RadTop;
uniform float RadBot;
// мягкий левый край заливки (растёт справа налево): ширина растушёвки
uniform float Feather;
uniform vec4 Theme1;
uniform vec4 Theme2;
uniform vec4 ColorModulator;

in vec2 fragcoord;
in vec4 fragcolor;

out vec4 outcolor;

float boxsdf(vec2 p, vec2 b, float r) {
    vec2 d = abs(p) - b + vec2(r);
    return min(max(d.x, d.y), 0.0) + length(max(d, 0.0)) - r;
}

void main() {
    vec2 hs = Size * 0.5;
    vec2 pos = (fragcoord - 0.5) * Size;
    float r = pos.y < 0.0 ? RadTop : RadBot;
    r = min(r, min(hs.x, hs.y));
    float dst = boxsdf(pos, hs, r);
    float aa = max(Px, 0.2) * 0.75;
    float msk = 1.0 - smoothstep(-aa, aa, dst);

    // ниже шапки — мягкий спад по S-кривой (без видимой границы, где кончается цвет)
    float yy = fragcoord.y * Size.y;
    float t = Tail > 0.001 ? clamp((yy - Head) / Tail, 0.0, 1.0) : 1.0;
    float fade = yy <= Head ? 1.0 : 1.0 - t * t * (3.0 - 2.0 * t);
    fade *= fade;

    float fx = fragcoord.x * Size.x;
    if (Feather > 0.001) fade *= smoothstep(0.0, Feather, fx);

    vec3 col = mix(Theme1.rgb, Theme2.rgb, fragcoord.x);
    outcolor = vec4(col, fragcolor.a * msk * fade) * ColorModulator;
}
