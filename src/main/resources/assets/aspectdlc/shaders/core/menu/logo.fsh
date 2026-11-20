#version 150

// надпись загрузки по полю расстояний (SDF): белые буквы с аккуратной чёрной обводкой
// (толщина в пикселях экрана) и лёгкой мягкой тенью вокруг
uniform sampler2D Sampler0;
uniform float OutlinePx;
uniform float Shadow;
uniform vec4 ColorModulator;

in vec2 fragcoord;
in vec4 fragcolor;

out vec4 outcolor;

void main() {
    float s = texture(Sampler0, fragcoord).r;
    // сколько единиц поля приходится на один пиксель экрана
    float fw = max(fwidth(s), 0.0005);
    float cov = smoothstep(0.5 - 0.6 * fw, 0.5 + 0.6 * fw, s);
    float oe = 0.5 - OutlinePx * fw;
    float ocov = smoothstep(oe - 0.6 * fw, oe + 0.6 * fw, s);
    // мягкая тень за обводкой — чтобы надпись не «плоско» лежала на фоне
    float sh = smoothstep(0.5 - (OutlinePx + 9.0) * fw, oe, s);
    float a = max(ocov, sh * sh * Shadow);
    a = max(a, cov);
    vec3 c = vec3(cov / max(a, 0.0001));
    outcolor = vec4(c, a * fragcolor.a) * ColorModulator;
}
