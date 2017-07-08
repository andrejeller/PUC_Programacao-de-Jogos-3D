#version 330

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uWorld;
uniform float fator;
uniform vec3 uPosition;
uniform vec3 uCameraPosition;

in vec3 aPosition;
in vec3 aNormal;
in vec2 aTexCoord;


out vec3 vNormal;
out vec3 vViewPath;
out vec2 vTexCoord;
out vec3 vDirectionalLight;
out float Atenuacao;



void main() {
	vec4 worldPos = uWorld * vec4(aPosition, 1.0);
    gl_Position =  uProjection * uView * worldPos;
    vNormal = (uWorld * vec4(aNormal, 0.0)).xyz;
    vViewPath = uCameraPosition - worldPos.xyz;
    vTexCoord = aTexCoord;


    vDirectionalLight = worldPos.xyz-uPosition.xyz;
    Atenuacao = 1.0/pow(length(vDirectionalLight)*fator,2);
    vDirectionalLight = normalize(vDirectionalLight);
}