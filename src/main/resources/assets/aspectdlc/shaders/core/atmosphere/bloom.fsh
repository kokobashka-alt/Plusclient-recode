#version 150

// свечение в четверть разрешения: берём только светлые места кадра и широко размываем (13 выборок по кругу)
uniform sampler2D Sampler0; // кадр
uniform vec2 Texel;         // тексель полного кадра

in vec2 uv;

out vec4 outcolor;

vec3 bright(vec2 t) {
    vec3 c = texture(Sampler0, t).rgb;
    float l = dot(c, vec3(0.2126, 0.7152, 0.0722));
    return c * smoothstep(0.55, 0.95, l);
}

void main() {
    vec3 acc = bright(uv) * 0.16;
    float w = 0.16;
    for (int i = 0; i < 12; i++) {
        float a = float(i) * 0.5236;
        float r = (i % 2 == 0) ? 6.0 : 12.0;
        float k = (i % 2 == 0) ? 0.09 : 0.05;
        acc += bright(uv + vec2(cos(a), sin(a)) * Texel * r) * k;
        w += k;
    }
    outcolor = vec4(acc / w, 1.0);
}
