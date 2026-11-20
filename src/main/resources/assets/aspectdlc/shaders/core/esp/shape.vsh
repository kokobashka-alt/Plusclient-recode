#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;
in vec3 Normal;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 Local;
out vec4 Tint;
flat out vec2 Half;
flat out float Rad;
flat out int Kind;
flat out vec3 Param;

const vec2 CORNERS[4] = vec2[](vec2(0.0, 0.0), vec2(0.0, 1.0), vec2(1.0, 1.0), vec2(1.0, 0.0));
const float PADS[6] = float[](6.0, 1.0, 1.0, 1.0, 2.5, 6.0);

void main() {
    Kind = clamp(UV2.y, 0, 5);
    Rad = float(UV2.x) / 64.0;
    Half = UV0 * 0.5;
    Param = Normal;
    Tint = Color;
    Local = (CORNERS[gl_VertexID % 4] - 0.5) * (UV0 + 2.0 * PADS[Kind]);
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
}
