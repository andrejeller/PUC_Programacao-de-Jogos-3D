package br.pucpr.cg;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Vector3f;

import br.pucpr.mage.Mesh;
import br.pucpr.mage.MeshBuilder;

public class MeshFactory {

    
    public static Mesh loadTerrain(File file, float scale) throws IOException {        
        BufferedImage img = ImageIO.read(file);

        int width = img.getWidth();
        int depth = img.getHeight();
        
        float hw = width / 2.0f;
        float hd = depth / 2.0f;
        
        // Criação dos vértices
        List<Vector3f> positions = new ArrayList<>();        
        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {
                int tone = new Color(img.getRGB(x, z)).getRed();
                positions.add(new Vector3f(x - hw, tone * scale, z - hd));
            }
        }

        //Criação dos índices
        List<Integer> indices = new ArrayList<>();
        for (int z = 0; z < depth - 1; z++) {
            for (int x = 0; x < width - 1; x++) {
                int zero = x + z * width;
                int one = (x + 1) + z * width;
                int two = x + (z + 1) * width;
                int three = (x + 1) + (z + 1) * width;

                indices.add(zero);
                indices.add(three);
                indices.add(one);

                indices.add(zero);
                indices.add(two);
                indices.add(three);
            }
        }
        
        //Criacao da lista das normais
        List<Vector3f> normals = new ArrayList<Vector3f>();
        for (int i = 0; i < positions.size(); i++) {
            normals.add(new Vector3f());
        }
        
        //Calculo das normais
        for (int i = 0; i < indices.size(); i += 3) {
            int i1 = indices.get(i);
            int i2 = indices.get(i+1);
            int i3 = indices.get(i+2);
            
            Vector3f v1 = positions.get(i1);
            Vector3f v2 = positions.get(i2);
            Vector3f v3 = positions.get(i3);
                        
            Vector3f side1 = new Vector3f(v2).sub(v1);
            Vector3f side2 = new Vector3f(v3).sub(v1);
            
            Vector3f normal = new Vector3f(side1).cross(side2);

            normals.get(i1).add(normal);
            normals.get(i2).add(normal);
            normals.get(i3).add(normal);
        }
        
        for (Vector3f normal : normals) {
            normal.normalize();
        }
        
        return new MeshBuilder()
                    .addVector3fAttribute("aPosition", positions)
                    .addVector3fAttribute("aNormal", normals)
                    .setIndexBuffer(indices)
                    .loadShader("/br/pucpr/resource/Phong2")
                    .create();
    }

}
