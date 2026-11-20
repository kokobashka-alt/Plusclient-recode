#version 150

uniform vec2 Size;
uniform float Radius;
uniform float Px;
uniform float Hover;
uniform float Press;
uniform float Time;
uniform vec2 Mouse;
uniform vec4 Theme1;
uniform vec4 Theme2;
uniform vec4 Base;
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
    float rad = min(Radius, min(hs.x, hs.y));
    float dst = boxsdf(pos, hs, rad);

    float aa = max(fwidth(dst), 0.0001);
    float msk = 1.0 - smoothstep(-aa, aa, dst);
    if (msk < 0.001) {
        discard;
    }

    float ty = fragcoord.y;
    float tx = fragcoord.x;
    float hov = clamp(Hover, 0.0, 1.0);

    // база — тот же тон, что у плашек HUD (тёмный с оттенком темы): сверху светлее, снизу глубже
    vec3 top = Base.rgb + vec3(0.11);
    vec3 bot = Base.rgb * 0.85 + vec3(0.02);
    vec3 col = mix(top, bot, smoothstep(0.0, 1.0, ty));
    float alp = 0.50;

    // заливка темой: поднимается снизу, верхний край — мягкая живая волна
    float wav = sin(tx * 6.2831 * 1.10 + Time * 2.6) * 0.028
              + sin(tx * 6.2831 * 2.30 - Time * 1.9) * 0.014;
    wav *= smoothstep(0.0, 0.18, hov) * (1.0 - smoothstep(0.80, 1.0, hov));
    float lvl = 1.0 - hov * 1.18 + wav;
    float fe = max(fwidth(ty) * 1.2, 0.004);
    float fil = smoothstep(lvl - fe, lvl + fe, ty);

    float gt = clamp(tx * 0.65 + (1.0 - ty) * 0.35, 0.0, 1.0);
    vec3 thm = mix(Theme1.rgb, Theme2.rgb, gt);
    thm *= 1.08 - 0.28 * ty;
    col = mix(col, thm, fil * 0.94);
    alp = mix(alp, 0.97, fil);

    // светящаяся кромка поверхности заливки
    float edg = exp(-pow((ty - lvl) / (fe * 3.0 + 0.025), 2.0));
    edg *= step(0.001, hov) * (1.0 - smoothstep(0.85, 1.0, hov));
    col += edg * 0.30 * mix(vec3(1.0), Theme2.rgb, 0.35);

    // блик, следующий за курсором
    vec2 mp = (Mouse - 0.5) * Size;
    float md = length(pos - mp) / max(hs.x, hs.y);
    col += (1.0 - smoothstep(0.0, 1.25, md)) * 0.13 * hov;

    // верхний глянец
    col += (1.0 - smoothstep(0.0, 0.52, ty)) * 0.055;

    // внутренняя обводка: яркая сверху, почти невидимая снизу, при наведении — в цвет темы
    float ins = -dst;
    float rim = 1.0 - smoothstep(0.0, max(Px, 0.2) * 1.4, ins);
    float rtp = 1.0 - smoothstep(0.0, 0.65, ty);
    vec3 rcl = mix(vec3(1.0), mix(Theme1.rgb, Theme2.rgb, tx) * 1.35, hov * 0.55);
    col += rim * mix(0.06, 0.30, rtp) * rcl;

    // нажатие
    col *= 1.0 - Press * 0.14;

    outcolor = vec4(col, alp * msk * fragcolor.a) * ColorModulator;
}
