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


    public Vector3f getUp() {
        return up;
    }

    public Vector3f getTarget() {
        return target;
    }

    public Vector3f getPosition() {
        return position;
    }


    public float getFovy() {
        return fovy;
    }

    public void setFovy(float fovy) {
        this.fovy = fovy;
    }

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
    }

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

    public  void moveFront(float zoom){
        Vector3f Zoom = new Vector3f(target);
        Zoom.mul(zoom);
        position.add(Zoom);
    }

    public void strafeLeft(){


    }
    public void rotate(float angulo){
        new Matrix3f().rotateY(angulo).transform(target);
    }

}
