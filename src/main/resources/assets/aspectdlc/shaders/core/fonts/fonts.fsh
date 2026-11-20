#version 150

uniform sampler2D Sampler0;
uniform vec2 TextureSize;
uniform float Range;
uniform float EdgeStrength;
uniform float Thickness;
uniform vec4 Color;
uniform int Outline;
uniform float OutlineThickness;
uniform vec4 OutlineColor;
// толщина обводки в пикселях экрана (добавляется к OutlineThickness)
uniform float OutlinePx;

in vec2 texCoord;
in vec4 vertexColor;

out vec4 fragColor;

float median(float r, float g, float b) {
    return max(min(r, g), min(max(r, g), b));
}

void main() {
    vec4 texColor = texture(Sampler0, texCoord);

    float dx = dFdx(texCoord.x) * TextureSize.x;
    float dy = dFdy(texCoord.y) * TextureSize.y;
    float toPixels = Range * inversesqrt(dx * dx + dy * dy);

    float sigDist = median(texColor.r, texColor.g, texColor.b) - 0.5 + Thickness;

    float alpha = smoothstep(-EdgeStrength, EdgeStrength, sigDist * toPixels);

    if (Outline == 1) {
        // обводка — только снаружи контура: сама буква остаётся ровно такой же.
        // ширина не больше запаса поля расстояний в атласе — иначе вокруг букв проступили бы квадраты
        float w = OutlineThickness * toPixels + OutlinePx;
        w = min(w, max(0.0, (0.5 - Thickness) * toPixels - EdgeStrength - 0.1));
        float outer = smoothstep(-EdgeStrength, EdgeStrength, sigDist * toPixels + w);
        float oa = max(outer - alpha, 0.0) * OutlineColor.a;
        float ta = alpha + oa;
        vec3 rgb = ta > 0.0001 ? (Color.rgb * vertexColor.rgb * alpha + OutlineColor.rgb * oa) / ta : Color.rgb;
        fragColor = vec4(rgb, ta * Color.a * vertexColor.a);
        return;
    }

    fragColor = vec4(Color.rgb * vertexColor.rgb, Color.a * vertexColor.a * alpha);
}
