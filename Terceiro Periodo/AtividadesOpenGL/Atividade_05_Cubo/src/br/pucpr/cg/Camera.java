package br.pucpr.cg;


import org.joml.*;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


/**
 * Created by AndreJeller on 08/05/2017.
 */

public class Camera {

    private Vector3f up = new Vector3f(0, 1, 0);
    private Vector3f target = new Vector3f(0,0,-1);
    private Vector3f position = new Vector3f(0,0,2);

    private float fovy = (float)Math.toRadians(60);

    private float near = 0.1f;
    private float far = 1000.0f;



    private float getAspect() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        long window = glfwGetCurrentContext();
        glfwGetWindowSize(window, w, h);
        return w.get() / (float)h.get();
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(position, target, up);
    }
    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().perspective(fovy, getAspect(), near, far);
    }

}
