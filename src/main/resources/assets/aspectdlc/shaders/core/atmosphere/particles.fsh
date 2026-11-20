#version 150

uniform float Mode;
uniform float Alpha;
uniform vec3 PartTint;   // общий оттенок атмосферы (частицы рисуются после цветокоррекции)
uniform vec4 ColorModulator;

in vec2 uv;
in vec4 tint;
in float kind;
in float spark;

out vec4 outcolor;

void main() {
    float a;
    vec3 col;
    if (Mode < 0.5) {
        // снежинка: мягкий диск с плотным светлым ядром, редкие поблёскивают
        float r = length(uv);
        if (r > 1.0) discard;
        float body = 1.0 - smoothstep(0.3, 1.0, r);
        float core = 1.0 - smoothstep(0.0, 0.45, r);
        col = mix(vec3(0.80, 0.88, 1.0), vec3(1.0), core);
        a = body * (0.5 + 0.5 * core);
        a *= 0.85 + 0.6 * spark * core;
    } else if (kind > 0.5) {
        // светящаяся пыльца
        float r = length(uv);
        if (r > 1.0) discard;
        float g = 1.0 - smoothstep(0.0, 1.0, r);
        col = vec3(1.0, 0.86, 0.92);
        a = g * g * 0.9;
    } else {
        // лепесток сакуры: узкое основание, широкая скруглённая вершина с V-выемкой;
        // у основания насыщенно-розовый, к краю почти белый
        vec2 p = uv;
        float t = clamp((p.y + 1.0) * 0.5, 0.0, 1.0);
        float hw = 0.66 * pow(sin(3.14159 * pow(t, 0.72)), 0.62);
        float ex = hw - abs(p.x);
        float aa = max(fwidth(ex), 0.002) * 1.1;
        float body = smoothstep(-aa, aa, ex);
        float nv = p.y - (0.70 + 1.25 * abs(p.x));
        float notch = smoothstep(-aa, aa, nv);
        a = body * (1.0 - notch);
        if (a < 0.01) discard;
        col = mix(vec3(0.94, 0.50, 0.68), vec3(1.0, 0.86, 0.92), smoothstep(0.0, 0.55, t));
        col = mix(col, vec3(1.0, 0.95, 0.97), smoothstep(0.55, 1.0, t) * 0.6);
        // тонкие прожилки от основания
        col *= 1.0 - 0.07 * (1.0 - smoothstep(0.0, 0.04, abs(p.x))) * (1.0 - t);
        if (!gl_FrontFacing) col *= 0.88;
        a *= 0.96;
    }
    float A = a * tint.a * Alpha;
    // премультиплицированный альфа: снежинка больше подсвечивает, чем закрывает — ночью не превращается в тёмное пятно
    float occl = Mode < 0.5 ? 0.3 + 0.5 * tint.r : 1.0;
    outcolor = vec4(col * tint.rgb * PartTint * A, A * occl) * ColorModulator;
}
