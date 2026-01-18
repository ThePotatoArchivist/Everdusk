#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:globals.glsl>
#moj_import <minecraft:chunksection.glsl>
#moj_import <minecraft:projection.glsl>
#moj_import <minecraft:smooth_lighting.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler2;
uniform sampler2D NoSkyLightmap;
layout(std140) uniform LightDirection {
    vec3 direction;
} lightDirection;

out float sphericalVertexDistance;
out float cylindricalVertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;

void main() {
    vec3 pos = Position + (ChunkPosition - CameraBlockPos) + CameraOffset;
    gl_Position = ProjMat * ModelViewMat * vec4(pos, 1.0);

    sphericalVertexDistance = fog_spherical_distance(pos);
    cylindricalVertexDistance = fog_cylindrical_distance(pos);
    vec4 skyLightColor = minecraft_sample_lightmap(Sampler2, UV2);
    vec4 noSkyLightColor = minecraft_sample_lightmap(NoSkyLightmap, UV2);
    float amount = clamp(dot(Normal, lightDirection.direction), 0, 1);
//    vertexColor = vec4(lightDirection.direction, 1);
    vertexColor = Color * mix(noSkyLightColor, skyLightColor, amount);
//    vertexColor = Color * noSkyLightColor;
    texCoord0 = UV0;
}
