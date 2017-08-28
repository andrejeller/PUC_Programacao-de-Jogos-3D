package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import br.pucpr.mage.*;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class RotatingSquare implements Scene {
	private float angle;

	private int vao;
	private ArrayBuffer positions;
	private ArrayBuffer colors;
	private IndexBuffer indices;
	private Shader shader;


	@Override
	public void init() {
		glClearColor(0.0f, 0.0f, 0.2f, 1.0f);

		float[] positionData = new float[] { 
			 -0.5f,  0.5f,   //Vertice 0
			  0.5f,  0.5f,   //Vertice 1
			 -0.5f, -0.5f,   //Vertice 2
			  0.5f, -0.5f    //Vertice 3 			 
		};
		
		float[] colorData = new float[] {
			1.0f, 0.0f, 0.0f,
			1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 1.0f,
		};
		
		int indexData[] = new int[] {
			0, 2, 3,
			0, 3, 1
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

		//Atribuição dos índices
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indexData.length);
		indexBuffer.put(indexData).flip();
		indices = new IndexBuffer(indexBuffer);

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
				.setUniform("uWorld",new Matrix4f().rotateY(angle));

		//Indices
		indices.draw();

		shader.setAttribute("aPosition", null)
				.setAttribute("aColor", null)
				.unbind();
		indices.unbind();
		glBindVertexArray(0);
		glUseProgram(0);
	}

	@Override
	public void deinit() {
	}

	public static void main(String[] args) {
		new Window(new RotatingSquare()).show();
	}

	@Override
	public void keyPressed(long window, int key, int scancode, int action, int mods) {
		if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
			glfwSetWindowShouldClose(window, GLFW_TRUE); // We will detect this
															// in our rendering
															// loop
	}
}
