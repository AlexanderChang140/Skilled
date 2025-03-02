#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 color = texture(DiffuseSampler, texCoord);
    vec3 lumaFactors = vec3(0.299, 0.587, 0.114);
    float luma = dot(color.rgb, lumaFactors);
    fragColor = vec4(luma, luma, luma, 1.0);
}
