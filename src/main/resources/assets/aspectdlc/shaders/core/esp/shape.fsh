#version 150

in vec2 Local;
in vec4 Tint;
flat in vec2 Half;
flat in float Rad;
flat in int Kind;
flat in vec3 Param;

uniform vec4 ColorModulator;
uniform float Time;

out vec4 OutColor;

const float TAU = 6.2831853;

// Superellipse corner: (x^n + y^n)^(1/n) - r, n = 2.6
float sdBox(vec2 p, vec2 b, float r) {
    r = min(r, min(b.x, b.y));
    vec2 q = abs(p) - b + r;
    vec2 c = max(q, 0.0);
    float corner = pow(pow(c.x, 2.6) + pow(c.y, 2.6), 1.0 / 2.6);
    return min(max(q.x, q.y), 0.0) + corner - r;
}

float coverage(float d) {
    float w = max(fwidth(d), 1e-4);
    return clamp(0.5 - d / w, 0.0, 1.0);
}

float sdSegment(vec2 p, vec2 a, vec2 b) {
    vec2 pa = p - a;
    vec2 ba = b - a;
    float h = clamp(dot(pa, ba) / dot(ba, ba), 0.0, 1.0);
    return length(pa - ba * h);
}

// Heart = unit square rotated 45deg U circles r = 0.5 at (+-sqrt(2)/4, sqrt(2)/4)
float sdHeart(vec2 p) {
    vec2 q = vec2(p.x + p.y, p.y - p.x) * 0.70710678;
    vec2 e = abs(q) - vec2(0.5);
    float square = length(max(e, 0.0)) + min(max(e.x, e.y), 0.0);
    float lobe = length(vec2(abs(p.x), p.y) - vec2(0.35355339, 0.35355339)) - 0.5;
    return min(square, lobe);
}

// Porter-Duff over, straight alpha: a = at + ab(1 - at)
vec4 over(vec4 top, vec4 bottom) {
    float a = top.a + bottom.a * (1.0 - top.a);
    if (a <= 0.0) {
        return vec4(0.0);
    }
    return vec4((top.rgb * top.a + bottom.rgb * bottom.a * (1.0 - top.a)) / a, a);
}

vec4 plate() {
    float d = sdBox(Local, Half, Rad);
    float body = coverage(d);
    float ny = Local.y / Half.y;
    float t = clamp(ny * 0.5 + 0.5, 0.0, 1.0);
    vec3 col = Tint.rgb + vec3(0.07) * (1.0 - t) * (1.0 - t);
    float rim = clamp(1.0 + d, 0.0, 1.0) * smoothstep(0.2, -0.9, ny);
    col += vec3(0.08) * rim;
    float density = mix(1.0, 0.8, t);
    float ds = sdBox(Local - vec2(0.0, 1.0), Half, Rad);
    float shade = (1.0 - smoothstep(-1.5, 4.5, ds)) * 0.26 * Param.x * (1.0 - body);
    return over(vec4(col, body * density), vec4(0.0, 0.0, 0.0, shade)) * vec4(1.0, 1.0, 1.0, Tint.a);
}

vec4 solid() {
    return vec4(Tint.rgb, Tint.a * coverage(sdBox(Local, Half, Rad)));
}

vec4 separator() {
    float d = sdBox(Local, Half, min(Half.x, Half.y));
    float ny = Local.y / max(Half.y, 1e-4);
    return vec4(Tint.rgb, Tint.a * coverage(d) * clamp(1.0 - ny * ny, 0.0, 1.0));
}

// Heart box: width 2(sqrt(2)/4 + 0.5) = 1.7071, y in [-sqrt(2)/2, sqrt(2)/4 + 0.5], center 0.0732
vec4 heart() {
    float s = min(Half.x * 2.0 / 1.7071, Half.y * 2.0 / 1.5607);
    vec2 q = vec2(Local.x, -Local.y) / s + vec2(0.0, 0.0732);
    float cov = coverage(sdHeart(q) * s);
    vec3 col = mix(Tint.rgb, vec3(1.0), 0.14 * smoothstep(0.1, 0.85, q.y));
    return vec4(col, Tint.a * cov);
}

vec4 corners() {
    vec2 p = abs(Local);
    float len = clamp(min(Half.x, Half.y) * 0.45, 3.0, 12.0);
    float d = min(sdSegment(p, vec2(Half.x - len, Half.y), Half), sdSegment(p, vec2(Half.x, Half.y - len), Half));
    vec4 core = vec4(Tint.rgb, coverage(d - 0.7));
    vec4 halo = vec4(0.0, 0.0, 0.0, coverage(d - 1.7) * 0.5);
    return over(core, halo) * vec4(1.0, 1.0, 1.0, Tint.a);
}

vec4 frame() {
    float d = sdBox(Local, Half, Rad);
    float inside = coverage(d);
    float stroke = coverage(abs(d) - 0.55);
    float ang = atan(Local.y, Local.x) / TAU;
    float wave = 0.5 + 0.5 * sin((ang + Time * 0.12) * TAU);
    vec3 col = mix(Tint.rgb, Param * 0.5 + 0.5, wave);
    float glow = exp(-max(d, 0.0) / 1.8) * (1.0 - inside) * 0.38;
    float a = stroke + (glow + inside * 0.05) * (1.0 - stroke);
    return vec4(col, clamp(a, 0.0, 1.0) * Tint.a);
}

void main() {
    vec4 c;
    if (Kind == 0) {
        c = plate();
    } else if (Kind == 1) {
        c = solid();
    } else if (Kind == 2) {
        c = separator();
    } else if (Kind == 3) {
        c = heart();
    } else if (Kind == 4) {
        c = corners();
    } else {
        c = frame();
    }
    if (c.a <= 0.002) {
        discard;
    }
    OutColor = c * ColorModulator;
}
