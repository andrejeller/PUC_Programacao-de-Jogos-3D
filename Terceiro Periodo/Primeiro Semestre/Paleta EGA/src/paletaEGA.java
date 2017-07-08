import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.pow;

/**
 * Created by AndreJeller on 01/04/2017.
 */
public class paletaEGA {

    //Paleta EGA 64 cores.

    int[] paleta = {
            0x000000, 0x00AA00, 0x0000AA, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xAAAA00, 0xAAAAAA, 0x000055,
            0x0000FF, 0x00AA55, 0x00AAFF, 0xAA0055, 0xAA00FF, 0xAAAA55, 0xAAAAFF, 0x005500, 0x0055AA,
            0x00FF00, 0x00FFAA, 0xAA5500, 0xAA55AA, 0xAAFF00, 0xAAFFAA, 0x005555, 0x0055FF, 0x00FF55,
            0x00FFFF, 0xAA5555, 0xAA55FF, 0xAAFF55, 0xAAFFFF, 0x550000, 0x5500AA, 0x55AA00, 0x55AAAA,
            0xFF0000, 0xFF00AA, 0xFFAA00, 0xFFAAAA, 0x550055, 0x5500FF, 0x55AA55, 0x55AAFF, 0xFF0055,
            0xFF00FF, 0xFFAA55, 0xFFAAFF, 0x555500, 0x5555AA, 0x55FF00, 0x55FFAA, 0xFF5500, 0xFF55AA,
            0xFFFF00, 0xFFFFAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF
    };

    //retorna a cor da paleta na posX em formato Color...
    public Color retornaPaleta(int pos){
        int paletaPos = paleta[pos];
        return new Color (paletaPos);
    }

    //Calcula distancia entre duas cores na tabela
    public double calculaDistanciaPx(Color px, Color px1){
        return Math.sqrt( pow((px.getRed() - px1.getRed()), 2) + pow((px.getGreen() - px1.getGreen()), 2) + pow((px.getBlue() - px1.getBlue()), 2));
     }

    //"transforma" a paleta
    public BufferedImage converter(BufferedImage img){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < out.getHeight(); y++){
            for (int x =0; x < out.getWidth(); x++){

                Color pixel = new Color(img.getRGB(x, y));
                double dist = 255; // reseta a cada pixel
                int posEGA = 0;
                for(int i = 0; i < 64; i++) {

                    if (calculaDistanciaPx(pixel, retornaPaleta(i)) < dist ){
                        posEGA = i;
                        dist = calculaDistanciaPx(pixel, retornaPaleta(i));
                    }
                }
                Color newPixel = retornaPaleta(posEGA);
                out.setRGB(x, y, newPixel.getRGB());
            }
        }
        return out;
    }


    public void run() throws IOException {
        File PATH = new File ("C:\\Users\\AndreJeller.DELLER\\Desktop\\PUC\\Programação 3D\\img\\cor");
        BufferedImage img = ImageIO.read(new File(PATH, "puppy.png"));

        BufferedImage newImg = converter(img);

        ImageIO.write(newImg, "png", new File(PATH, "egaPuppy.png"));
    }


    public static void main(String args[]) throws IOException {
        new paletaEGA().run();
    }
}
