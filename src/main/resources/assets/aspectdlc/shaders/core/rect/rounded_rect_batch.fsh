#version 150

#moj_import <aspectdlc:common.glsl>

in vec2 FragCoord;
flat in vec2 FragSize;
in vec4 FragColor;

uniform vec4 Radius;
uniform float Smoothness;
uniform vec4 ColorModulator;

out vec4 OutColor;

void main() {
    vec2 center = FragSize * 0.5;
    float distance = roundedBoxSDF(center - (FragCoord * FragSize), center - 1.0, Radius);

    float alpha = 1.0 - smoothstep(1.0 - Smoothness, 1.0, distance);
    vec4 finalColor = vec4(FragColor.rgb, FragColor.a * alpha);

    if (finalColor.a == 0.0) {
        discard;
    }

    OutColor = finalColor * ColorModulator;
}
