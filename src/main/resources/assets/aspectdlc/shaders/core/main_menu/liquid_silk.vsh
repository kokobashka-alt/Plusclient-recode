#version 150

in vec3 Position;
in vec2 UV0;
in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 fragcoord;
out vec4 fragcolor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    fragcoord = UV0;
    fragcolor = Color;
}
