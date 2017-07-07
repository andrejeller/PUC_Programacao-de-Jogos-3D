#version 330

in vec3 vNormal;
in vec3 vViewPath;
out vec4 outColor;
//Cor da Luz
uniform vec3 uLightDir;
//cor da luz ambiente
uniform vec3 uAmbientLight;
//Cor da Luz difusa
uniform vec3 uDiffuseLight;
//Cor da Luz especular
uniform vec3 uSpecularLight;
//Sensibilidade do material a luz especular
uniform float uSpecularPower;

//Cor do Material ambiente
uniform vec3 uAmbientMaterial;
//Cor do Material Difuso
uniform vec3 uDiffuseMaterial;
//Sensibilidade do material especular
uniform float uSpecularMaterial;


void main() {
    vec3 N = normalize(vNormal);
    vec3 L = normalize(uLightDir);

    //calculo do componente ambiente
    vec3 ambient = uAmbientLight * uAmbientMaterial;

    // Calculo do componente difuso
    float intensity = dot(N, -L); // cossenp entre N eL
    vec3 diffuse = clamp(uDiffuseLight * intensity, 0.0, 1.0) * uDiffuseMaterial; // "Saturate"

    //Calculo do componennte especular
    float specularIntensity = 0.0;
    if (uSpecularPower > 0.0){
        vec3 V = normalize(vViewPath);
        vec3 R = reflect(L, N);
        specularIntensity = pow(max(dot(R, V), 0.0), uSpecularPower);
    }

    vec3 specular = specularIntensity * uSpecularLight * uSpecularMaterial;

    //Combina os componentes
    vec3 color = clamp(ambient + diffuse + specular, 0.0, 1.0);


    outColor = vec4(color, 1.0f);
}