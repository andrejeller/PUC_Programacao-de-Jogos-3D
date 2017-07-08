package br.pucpr.cg;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import br.pucpr.mage.Mesh;
import br.pucpr.mage.MeshBuilder;

public class MeshFactory {


    public static Mesh esfera(int fatiasX, int fatiasZ) {

        // Criação dos vértices
        List<Vector3f> positions = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();

        for (int z = 0; z <= fatiasZ; z++) {
            double azimute = z * Math.PI/fatiasZ;
            double senAzimute = Math.sin(azimute);
            double cosAzimute = Math.cos(azimute);

            for (int x = 0; x <= fatiasX; x++) {
                double zenete = x * 2 * Math.PI/fatiasX;
                double senZenete = Math.sin(zenete);
                double cosZenete = Math.cos(zenete);

                Vector3f r = new Vector3f((float) (senAzimute * cosZenete), (float) cosAzimute, (float) (senAzimute*senZenete));
                positions.add(r);
                normals.add(r.normalize(new Vector3f()));
            }
        }

        //Criação dos índices
        List<Integer> indices = new ArrayList<>();
        for (int z = 0; z < fatiasZ ; z++) {
            for (int x = 0; x < fatiasX; x++) {
                int zero = x + z * fatiasX;
                int one = (x + 1) + z * fatiasX;
                int two = x + (z + 1) * fatiasX;
                int three = (x + 1) + (z + 1) * fatiasX;

                indices.add(zero);
                indices.add(three);
                indices.add(one);

                indices.add(zero);
                indices.add(two);
                indices.add(three);
            }
        }

        return new MeshBuilder()
                .addVector3fAttribute("aPosition", positions)
                .addVector3fAttribute("aNormal", normals)
                .setIndexBuffer(indices)
                .loadShader("/br/pucpr/resource/phong")
                .create();
    }
}
