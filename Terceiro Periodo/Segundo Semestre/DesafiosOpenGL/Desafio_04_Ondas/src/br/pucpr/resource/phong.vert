#version 330

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uWorld;
uniform float uSecs;

uniform vec3 uCameraPosition;

in vec3 aPosition;
in vec3 aNormal;


out vec3 vNormal;
out vec3 vViewPath;

void main() {
	vec4 worldPos = uWorld * vec4(aPosition.x, sin(length(aPosition.xz) + uSecs) * 5, aPosition.z, 1.0);
    gl_Position =  uProjection * uView * worldPos;
    vNormal = (uWorld * vec4(aNormal, 0.0)).xyz;
    vViewPath = uCameraPosition - worldPos.xyz;    
}