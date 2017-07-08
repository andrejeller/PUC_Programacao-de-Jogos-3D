package br.pucpr.mage.phong;

import org.joml.Vector3f;

import br.pucpr.mage.Shader;

public class Material {
    private Vector3f ambientColor;
    private Vector3f diffuseColor;
    private Vector3f specularColor;
    private float specularPower;
    
    public Material(Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, float specularPower) {
        super();
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.specularPower = specularPower;
    }

    public void apply(Shader shader) {
        shader.setUniform("uAmbientMaterial", ambientColor);
        shader.setUniform("uDiffuseMaterial", diffuseColor);
        shader.setUniform("uSpecularMaterial", specularColor);
        shader.setUniform("uSpecularPower", specularPower);
    }
}
