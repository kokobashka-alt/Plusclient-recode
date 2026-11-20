#version 150

in vec3 Position;
in vec2 UV0;
in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec2 Size;

out vec2 Local;
out vec2 TexCoord;
out vec4 Tint;

const vec2 CORNERS[4] = vec2[](vec2(0.0, 0.0), vec2(0.0, 1.0), vec2(1.0, 1.0), vec2(1.0, 0.0));

void main() {
    Local = (CORNERS[gl_VertexID % 4] - 0.5) * Size;
    TexCoord = UV0;
    Tint = Color;
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
}
