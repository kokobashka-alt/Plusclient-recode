#version 150

// лучи света в четверть разрешения: от каждого пикселя шагаем к солнцу (или луне) на экране и копим,
// сколько по пути открытого неба — сквозь просветы в кронах, горах и облаках получаются лучи
uniform sampler2D Sampler1; // глубина кадра

uniform vec2 SunUV;
uniform float SunOn;
uniform float Aspect;
uniform float Samples;

in vec2 uv;

out vec4 outcolor;

void main() {
    if (SunOn < 0.004) {
        outcolor = vec4(0.0, 0.0, 0.0, 1.0);
        return;
    }
    vec2 d = SunUV - uv;
    int n = int(Samples);
    vec2 st = d / float(n) * 0.92;
    float ign = fract(52.9829189 * fract(dot(gl_FragCoord.xy, vec2(0.06711056, 0.00583715))));
    vec2 c = uv + st * ign;
    float acc = 0.0, w = 1.0, ws = 0.0;
    for (int i = 0; i < 32; i++) {
        if (i >= n) break;
        vec2 cc = clamp(c, 0.0, 1.0);
        float s = texture(Sampler1, cc).r >= 0.99999 ? 1.0 : 0.0;
        acc += s * w;
        ws += w;
        w *= 0.955;
        c += st;
    }
    acc /= max(ws, 0.0001);
    float r = length(d * vec2(Aspect, 1.0));
    acc *= exp(-r * 1.35) * SunOn;
    outcolor = vec4(acc, 0.0, 0.0, 1.0);
}
