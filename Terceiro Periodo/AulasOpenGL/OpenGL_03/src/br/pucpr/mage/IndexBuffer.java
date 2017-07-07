package br.pucpr.mage;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 * Created by vinicius.mendonca on 24/04/2017.
 */
public class IndexBuffer {
    private int id;
    private int count;

    public IndexBuffer(IntBuffer data) {
        this.id = glGenBuffers();
        this.count = data.remaining();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public IndexBuffer bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        return this;
    }

    public IndexBuffer unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        return this;
    }

    public IndexBuffer draw() {
        bind();
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
        unbind();
        return this;
    }
}
