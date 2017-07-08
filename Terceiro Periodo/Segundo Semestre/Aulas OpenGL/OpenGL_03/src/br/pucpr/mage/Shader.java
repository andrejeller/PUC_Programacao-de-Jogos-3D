package br.pucpr.mage;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public class Shader {
    private int id;

    private static String readInputStream(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load shader", e);
        }
    }

    public static Shader loadProgram(String ...shaders) {
        if (shaders.length == 1) {
            shaders = new String[] {
                    shaders[0] + ".vert",
                    shaders[0] + ".frag"
            };
        }
        int[] ids = new int[shaders.length];
        for (int i = 0; i < shaders.length; i++) {
            ids[i] = loadShader(shaders[i]);
        }
        return new Shader(linkProgram(ids));
    }

    private static int loadShader(String name) {
        name = "/br/pucpr/resource/" + name.toLowerCase();
        int type;

        if (name.endsWith(".vert") || name.endsWith(".vs"))
            type = GL_VERTEX_SHADER;
        else if (name.endsWith(".frag") || name.endsWith(".fs"))
            type = GL_FRAGMENT_SHADER;
        else if (name.endsWith(".geom") || name.endsWith(".gs"))
            type = GL_GEOMETRY_SHADER;
        else throw new IllegalArgumentException("Invalid shader name: " + name);

        String code = readInputStream(Shader.class.getResourceAsStream(name));
        return compileShader(type, code);
    }

    private static int compileShader(int type, String code) {
        int shader = glCreateShader(type);
        glShaderSource(shader, code);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            String typeStr = type == GL_VERTEX_SHADER ? "vertex" : type == GL_FRAGMENT_SHADER ? "fragment" : "geometry";
            throw new RuntimeException("Unable to compile " + typeStr + " shader." + glGetShaderInfoLog(shader));
        }
        return shader;
    }

    private static int linkProgram(int... shaders) {
        int program = glCreateProgram();
        for (int shader : shaders) {
            glAttachShader(program, shader);
        }

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Unable to link shaders." + glGetProgramInfoLog(program));
        }

        for (int shader : shaders) {
            glDetachShader(program, shader);
            glDeleteShader(shader);
        }

        return program;
    }

    private Shader(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Shader bind() {
        glUseProgram(id);
        return this;
    }

    public Shader unbind() {
        glUseProgram(0);
        return this;
    }

    public Shader setAttribute(String name, ArrayBuffer data) {
        int attribute = glGetAttribLocation(id, name);
        if (data == null) {
            glDisableVertexAttribArray(attribute);
        } else {
            glEnableVertexAttribArray(attribute);
            glVertexAttribPointer(attribute, data.getElementSize(), GL_FLOAT, false, 0, 0);
        }
        return this;
    }

    private int findUniform(String name) {
        return glGetUniformLocation(id, name);
    }

    public Shader setUniform(String name, Matrix4f matrix) {
        int uniform = findUniform(name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(uniform, false, buffer);
        return this;
    }

}












