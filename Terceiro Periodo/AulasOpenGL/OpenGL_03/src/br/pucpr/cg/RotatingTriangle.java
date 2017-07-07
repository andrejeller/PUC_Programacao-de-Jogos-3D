package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import br.pucpr.mage.ArrayBuffer;
import br.pucpr.mage.Shader;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import br.pucpr.mage.Scene;
import br.pucpr.mage.Window;

public class RotatingTriangle implements Scene {
	private float angle;
	private ArrayBuffer positions;
	private ArrayBuffer colors;
	private Shader shader;
	private int vao;

	@Override
	public void init() {		
		glClearColor(0.0f, 0.0f, 0.2f, 1.0f);

		float[] positionData = new float[] { 
			     0.0f,  0.5f, 
			    -0.5f, -0.5f, 
			     0.5f, -0.5f 
		};
		
		float[] colorData = new float[] {
				1.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 1.0f,
		};

		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		//Atribuição dos vértices
		FloatBuffer data = BufferUtils.createFloatBuffer(positionData.length);
		data.put(positionData).flip();		
		positions = new ArrayBuffer(2, data);
		
		//Atribuição das cores
		data = BufferUtils.createFloatBuffer(colorData.length);
		data.put(colorData).flip();		
		colors = new ArrayBuffer(3, data);

		//Carga/Compilação dos shaders
		shader = Shader.loadProgram("basic");
		
		//Faxina
		glBindVertexArray(0);
	}

	@Override
	public void update(float secs) {
		angle += Math.toRadians(180) * secs;
	}

	@Override
	public void draw() {		
		glClear(GL_COLOR_BUFFER_BIT);

		glBindVertexArray(vao);
		shader.bind()
                .setAttribute("aPosition", positions.bind())
                .setAttribute("aColor", colors.bind())
                .setUniform("uWorld", new Matrix4f().rotateY(angle));

		positions.draw();

		//Faxina
		positions.unbind();
        shader.setAttribute("aPosition", null)
            .setAttribute("aColor", null)
            .unbind();
		glBindVertexArray(0);

	}

	@Override
	public void deinit() {
	}

	public static void main(String[] args) {
		new Window(new RotatingTriangle()).show();
	}

	@Override
	public void keyPressed(long window, int key, int scancode, int action, int mods) {
		if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
			glfwSetWindowShouldClose(window, GLFW_TRUE); // We will detect this
															// in our rendering
															// loop
	}
}
