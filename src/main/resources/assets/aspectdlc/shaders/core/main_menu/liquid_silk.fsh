#version 150

uniform float Time;
uniform vec2 Resolution;
uniform vec4 ColorModulator;

in vec2 fragcoord;
in vec4 fragcolor;

out vec4 outcolor;

mat2 rot(float a) {
    float c = cos(a);
    float s = sin(a);
    return mat2(c, -s, s, c);
}

float fld(vec2 p, float t) {
    vec2 q = p;

    vec2 w1 = vec2(
        sin(q.y * 0.78 + t * 0.18),
        cos(q.x * 0.68 - t * 0.14)
    );
    q += rot(0.32) * w1 * 0.72;

    vec2 w2 = vec2(
        cos(q.y * 0.94 - t * 0.16),
        sin(q.x * 0.82 + t * 0.12)
    );
    q += rot(-0.38) * w2 * 0.52;

    vec2 w3 = vec2(
        sin(q.x * 1.02 + q.y * 0.72 + t * 0.10),
        cos(q.y * 0.92 - q.x * 0.60 - t * 0.08)
    );
    q += rot(0.42) * w3 * 0.32;

    float h = sin(q.x * 0.76 + q.y * 0.56) * 0.54
            + cos(q.y * 0.90 - q.x * 0.48) * 0.34
            + sin(q.x * 0.48 - q.y * 0.80 + t * 0.09) * 0.20;
    return h;
}

void main() {
    vec2 asp = vec2(Resolution.x / max(Resolution.y, 1.0), 1.0);
    vec2 pos = (fragcoord - 0.5) * asp * 2.0;
    pos = rot(-0.12) * pos;
    float tim = Time * 0.22;

    vec2 eps = vec2(0.005, 0.0);
    float h = fld(pos, tim);
    float hx1 = fld(pos + eps.xy, tim);
    float hx2 = fld(pos - eps.xy, tim);
    float hy1 = fld(pos + eps.yx, tim);
    float hy2 = fld(pos - eps.yx, tim);

    float dx = (hx1 - hx2) / (eps.x * 2.0);
    float dy = (hy1 - hy2) / (eps.x * 2.0);
    vec3 nrm = normalize(vec3(-dx * 1.3, -dy * 1.3, 1.0));

    vec3 lgt = normalize(vec3(0.20, 0.84, 0.52));
    vec3 lgt2 = normalize(vec3(-0.36, -0.60, 0.42));
    vec3 vie = vec3(0.0, 0.0, 1.0);
    vec3 hlf = normalize(lgt + vie);
    vec3 hlf2 = normalize(lgt2 + vie);

    float dif = max(dot(nrm, lgt), 0.0);
    float dif2 = max(dot(nrm, lgt2), 0.0);
    float spc = pow(max(dot(nrm, hlf), 0.0), 36.0);
    float spc2 = pow(max(dot(nrm, hlf2), 0.0), 18.0);
    float sft = pow(max(dot(nrm, hlf), 0.0), 5.0);
    float frs = pow(clamp(1.0 - dot(nrm, vie), 0.0, 1.0), 2.6);

    float opd = frs * 2.0 + h * 0.6 + tim * 0.14;
    vec3 ird = 0.5 + 0.5 * cos(6.28318 * (opd * vec3(1.0, 1.12, 1.28) + vec3(0.0, 0.33, 0.67)));
    ird = mix(vec3(1.0), ird, 0.22);

    vec3 bas = vec3(0.055, 0.065, 0.095);
    vec3 mid = vec3(0.16, 0.19, 0.25);
    float fac = clamp(h * 0.48 + 0.50, 0.0, 1.0);
    vec3 col = mix(bas, mid, fac);

    float wc = clamp(h * 0.3 + 0.5, 0.0, 1.0);
    vec3 wrm = vec3(0.14, 0.12, 0.10);
    vec3 coo = vec3(0.08, 0.10, 0.15);
    col = mix(col, mix(coo, wrm, wc), 0.10);

    col += dif * 0.14 * vec3(0.72, 0.78, 0.88);
    col += dif2 * 0.07 * vec3(0.58, 0.64, 0.74);

    vec3 scc = mix(vec3(0.92, 0.95, 1.0), ird, 0.16);
    col += spc * 0.72 * scc;
    col += spc2 * 0.28 * scc;
    col += sft * 0.20 * vec3(0.62, 0.68, 0.78);

    vec3 frc = mix(vec3(0.58, 0.66, 0.78), ird, 0.20);
    col += frs * 0.35 * frc;

    vec2 ctr = fragcoord - 0.5;
    float vig = 1.0 - dot(ctr, ctr) * 0.35;
    col *= clamp(vig, 0.0, 1.0);

    outcolor = vec4(col, 1.0) * ColorModulator * fragcolor;
}
