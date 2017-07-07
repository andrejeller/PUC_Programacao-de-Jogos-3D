package br.pucpr.mage;

/**
 * Created by AndreJeller on 24/04/2017.
 */
public class Uniform {
    private UniformType type;
    private Object value;

    public Uniform(UniformType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public void set(Shader shader, String name) {
        type.set(shader, name, value);
    }
}
