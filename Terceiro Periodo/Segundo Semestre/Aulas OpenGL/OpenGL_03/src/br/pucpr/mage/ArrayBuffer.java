package br.pucpr.mage;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by vinicius.mendonca on 24/04/2017.
 */
public class ArrayBuffer {
    private int id;
    private int elementCount;
    private int elementSize;

    public ArrayBuffer(int elementSize, FloatBuffer data) {
        this.id = glGenBuffers();
        this.elementSize = elementSize;
        this.elementCount = data.remaining() / elementSize;
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int getId() {
        return id;
    }

    public int getElementCount() {
        return elementCount;
    }

    public int getElementSize() {
        return elementSize;
    }

    public int getSize() {
        return elementCount * elementSize;
    }

    public ArrayBuffer bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
        return this;
    }

    public ArrayBuffer unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return this;
    }

    public ArrayBuffer draw() {
        glDrawArrays(GL_TRIANGLES, 0, getElementCount());
        return this;
    }
}
