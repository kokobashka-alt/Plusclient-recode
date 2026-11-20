#version 150

in vec2 Coord;
in vec4 Tint;

uniform vec4 ColorModulator;
uniform float Mode;
uniform float Hidden;
uniform float Time;

out vec4 OutColor;

// Gaussian band: exp(-((x - c) / w)^2)
float band(float x, float c, float w) {
    float k = (x - c) / w;
    return exp(-k * k);
}

void main() {
    vec4 c;
    if (Mode < 0.5) {
        float a = 1.0 - smoothstep(0.45, 1.0, abs(Coord.x));
        if (Hidden > 0.5) {
            a *= 0.5 * step(0.45, fract(Coord.y * 5.0));
        }
        c = vec4(Tint.rgb, Tint.a * a);
    } else if (Mode < 1.5) {
        c = vec4(Tint.rgb, Tint.a * (Hidden > 0.5 ? 0.45 : 1.0));
    } else if (Mode < 2.5) {
        float r = length(Coord);
        float w = max(0.035, fwidth(r) * 1.2);
        float ring = band(r, 0.72, w);
        float glow = band(r, 0.72, 0.14) * 0.35;
        float pool = (1.0 - smoothstep(0.0, 0.72, r)) * 0.1;
        float ph = fract(Time * 0.62);
        float wave = band(r, 0.72 + ph * 0.26, max(0.03, w)) * (1.0 - ph) * 0.55;
        float a = clamp(ring + glow + pool + wave, 0.0, 1.0) * (1.0 - smoothstep(0.97, 1.0, r));
        if (Hidden > 0.5) {
            a *= 0.45;
        }
        c = vec4(mix(Tint.rgb, vec3(1.0), ring * 0.35), Tint.a * a);
    } else {
        float fade = 1.0 - Coord.y;
        float a = fade * fade * (1.0 - Coord.x * Coord.x) * 0.22;
        if (Hidden > 0.5) {
            a *= 0.45;
        }
        c = vec4(Tint.rgb, Tint.a * a);
    }
    if (c.a <= 0.002) {
        discard;
    }
    OutColor = c * ColorModulator;
}
