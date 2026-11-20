#version 150

in vec3 Position;
in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 fragcoord;
out vec4 fragcolor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    int id = gl_VertexID % 4;
    if (id == 0) {
        fragcoord = vec2(0.0, 0.0);
    } else if (id == 1) {
        fragcoord = vec2(0.0, 1.0);
    } else if (id == 2) {
        fragcoord = vec2(1.0, 1.0);
    } else {
        fragcoord = vec2(1.0, 0.0);
    }
    fragcolor = Color;
}
