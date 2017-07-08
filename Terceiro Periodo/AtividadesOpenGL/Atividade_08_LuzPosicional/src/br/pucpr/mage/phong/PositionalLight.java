package br.pucpr.mage.phong;

import br.pucpr.mage.Shader;
import org.joml.Vector3f;

/**
 * Created by Lucas on 19/06/2017.
 */
public class PositionalLight {
    private Vector3f position;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private float fator;

    public PositionalLight(Vector3f position, Vector3f ambient, Vector3f diffuse, Vector3f specular, float fator) {
        super();
        this.position = position;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.fator = fator;
    }

     public void apply(Shader shader) {
        shader.setUniform("uPosition", position);
        shader.setUniform("uAmbientLight", ambient);
        shader.setUniform("uDiffuseLight", diffuse);
        shader.setUniform("uSpecularLight", specular);
        shader.setUniform("fator", fator);
    }
}
