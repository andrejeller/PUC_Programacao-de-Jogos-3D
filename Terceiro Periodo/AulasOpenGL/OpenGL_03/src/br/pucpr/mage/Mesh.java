package br.pucpr.mage;

import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengles.GLES30.glGenVertexArrays;

/**
 * Created by AndreJeller on 24/04/2017.
 */
public class Mesh {
    private int id;
    private  Shader shader;
    private IndexBuffer indexBuffer;

    private Map<String, ArrayBuffer> attributes = new HashMap<>();
    private Map<String, Uniform> uniforms = new HashMap<>();

    Mesh() {
        id = glGenVertexArrays();
    }
    public int getId() {
        return id;
    }
    Mesh setIndexBuffer(IndexBuffer indexBuffer) {
        this.indexBuffer = indexBuffer;
        return this;
    }
    public Mesh setShader(Shader shader) {
        this.shader = shader;
        return this;
    }
    public Shader getShader() {
        return shader;
    }

    void addAttribute(String name, ArrayBuffer data) {
        if (attributes.containsKey(name)) {
            throw new IllegalArgumentException(
                    "Attribute already exists: " + name);
        }
        attributes.put(name, data);
    }
    private Mesh setUniform(String name, UniformType type,
                            Object value) {
        if (value == null)
            uniforms.remove(name);
        else {
            uniforms.put(name, new Uniform(type, value));
        }
        return this;
    }

    public Mesh setUniform(String name, Matrix4f matrix) {
        return setUniform(name, UniformType.Matrix4f, matrix);
    }

    public Mesh draw() {
        if (shader == null || attributes.size() == 0) {
            return this;
        }
        glBindVertexArray(id);
        shader.bind();
        for (Map.Entry<String, ArrayBuffer> attribute : attributes.entrySet()) {
            ArrayBuffer buffer = attribute.getValue();
            buffer.bind();
            shader.setAttribute(attribute.getKey(), buffer);
            buffer.unbind();
        }
        for (Map.Entry<String, Uniform> entry : uniforms.entrySet()) {
            entry.getValue().set(shader, entry.getKey());
        }
        if (indexBuffer == null) {
            attributes.values().iterator().next().draw();
        } else {
            indexBuffer.draw();
        }
        for (String attribute : attributes.keySet()) {
            shader.setAttribute(attribute, null);
        }
        shader.unbind();
        glBindVertexArray(0);
        return this;
    }

}
