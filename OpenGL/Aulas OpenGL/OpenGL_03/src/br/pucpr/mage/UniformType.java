package br.pucpr.mage;

/**
 * Created by AndreJeller on 24/04/2017.
 */
enum UniformType {
    Matrix4f{
        @Override
        public void set(Shader shader, String name, Object value) {
            shader.setUniform(name, (org.joml.Matrix4f) value);
        }
    };


    public abstract void set(Shader shader, String name, Object value);
}
