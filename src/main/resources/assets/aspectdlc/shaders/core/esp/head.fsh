#version 150

in vec2 Local;
in vec2 TexCoord;
in vec4 Tint;

uniform sampler2D Sampler0;
uniform vec2 Size;
uniform float Radius;
uniform vec4 ColorModulator;

out vec4 OutColor;

// Superellipse corner: (x^n + y^n)^(1/n) - r, n = 2.6
float sdBox(vec2 p, vec2 b, float r) {
    r = min(r, min(b.x, b.y));
    vec2 q = abs(p) - b + r;
    vec2 c = max(q, 0.0);
    float corner = pow(pow(c.x, 2.6) + pow(c.y, 2.6), 1.0 / 2.6);
    return min(max(q.x, q.y), 0.0) + corner - r;
}

void main() {
    float d = sdBox(Local, Size * 0.5, Radius);
    float cov = clamp(0.5 - d / max(fwidth(d), 1e-4), 0.0, 1.0);
    vec4 c = texture(Sampler0, TexCoord) * Tint;
    c.a *= cov;
    if (c.a <= 0.002) {
        discard;
    }
    OutColor = c * ColorModulator;
}
