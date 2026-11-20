#version 150

uniform vec2 Size;
uniform float Radius;
uniform float Hover;
uniform float Smoothness;
uniform vec4 ColorModulator;

in vec2 fragcoord;
in vec4 fragcolor;

out vec4 outcolor;

float boxsdf(vec2 p, vec2 b, float r) {
    vec2 d = abs(p) - b + vec2(r);
    return min(max(d.x, d.y), 0.0) + length(max(d, 0.0)) - r;
}

void main() {
    vec2 pos = (fragcoord - 0.5) * Size;
    float rad = min(Radius, min(Size.x, Size.y) * 0.5);
    float dst = boxsdf(pos, Size * 0.5, rad);
    float alp = 1.0 - smoothstep(-Smoothness, Smoothness, dst);

    if (alp < 0.001) {
        discard;
    }

    float ny = pos.y / max(Size.y * 0.5, 1.0);
    float ty = clamp(ny * 0.5 + 0.5, 0.0, 1.0);

    vec3 tc = mix(vec3(0.12, 0.14, 0.18), vec3(0.20, 0.22, 0.28), Hover);
    vec3 bc = mix(vec3(0.05, 0.06, 0.08), vec3(0.10, 0.12, 0.15), Hover);
    vec3 bod = mix(tc, bc, ty);

    float cap = max(abs(pos.x) - (Size.x * 0.5 - rad), 0.0) / max(rad, 1.0);
    bod *= (1.0 - cap * cap * 0.22);

    float tdst = pos.y + Size.y * 0.5;
    float rim = smoothstep(2.8, 0.4, tdst) * smoothstep(0.0, 1.0, -dst);
    float xf = 1.0 - smoothstep(Size.x * 0.5 - rad * 0.65, Size.x * 0.5, abs(pos.x));
    rim *= xf;

    vec3 rc = vec3(0.85, 0.88, 0.95);
    bod += rim * (0.24 + Hover * 0.25) * rc;

    float str = smoothstep(-1.2, -0.2, dst) * (1.0 - smoothstep(-0.2, 0.8, dst));
    bod += str * (0.05 + Hover * 0.10) * vec3(0.65, 0.72, 0.84);

    float oa = alp * fragcolor.a * (0.55 + Hover * 0.20);
    outcolor = vec4(bod, oa) * ColorModulator;
}
