package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Window;
import org.joml.Vector3f;

public class RotatingCube implements Scene {
    private Keyboard keys = Keyboard.getInstance();
    
    private Mesh mesh;
    private float angleY;
    private float angleX;

    private Camera camera = new Camera();

    @Override
    public void init(){
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.5f, 0.5f, 1.0f, 1.0f);

        Vector3f frontColor = new Vector3f(0.0f, 1.0f, 1.0f);
        Vector3f backColor= new Vector3f(1.0f, 0.0f, 1.0f);
        Vector3f topColor = new Vector3f(1.0f, 1.0f, 0.0f);
        Vector3f bottomColor = new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f rightColor = new Vector3f(0.0f, 1.0f, 0.0f);
        Vector3f leftColor = new Vector3f(0.0f, 0.0f, 1.0f);

        mesh = MeshFactory.createCube(frontColor, backColor, topColor, bottomColor, rightColor, leftColor);
    }

    @Override
    public void update(float secs) {
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }

        if (keys.isDown(GLFW_KEY_A)) {
            angleY += Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_D)) {
            angleY -= Math.toRadians(180) * secs;
        }
        if (keys.isDown(GLFW_KEY_W)) {
            angleX += Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_S)) {
            angleX -= Math.toRadians(180) * secs;
        }
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        //mesh.setUniform("uWorld", new Matrix4f().rotateY(angleX).rotateX(angleY));
        mesh.draw();

        mesh.getShader()
                .bind()
                .setUniform("uProjection", camera.getProjectionMatrix())
                .setUniform("uView", camera.getViewMatrix())
                .unbind();

        mesh.setUniform("uWorld", new Matrix4f().rotateY(angleY).rotateX(angleX));
        mesh.draw();
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new RotatingCube(), "Rotating cube", 800, 600).show();
    }


}
