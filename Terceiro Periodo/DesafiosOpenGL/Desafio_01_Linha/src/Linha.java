import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



/**
 * Created by andrejeller on 03/06/2017.
 */
public class Linha {

    BufferedImage linha(BufferedImage img, int x1, int y1, int x2, int y2, Color color){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int cor = color.getRGB();

        /* |x1 y1 1|
           |x2 y2 1|
           |x  y  1|

           x1*y2*1 + x2*y*1 + y1*1*x -(x*y2*1 + x2*y1*1 + y*x1*1)
           x1*y2 + x2*y + y1*x - x*y2 - x2*y1 + y*x1

        *
        * */


        for (int y = 0; y < out.getHeight(); y++) {
            for (int x = 0; x < out.getWidth(); x++) {

                if (((x1 * y2) + (x2 * y) + (y1 * x) - (x * y2) - (x2 * y1) - (y * x1)) == 0)
                    out.setRGB(x, y, cor);


            }
        }


        return out;
    }

    public void run() throws IOException {
        File PATH = new File ("C:\\Users\\andrejeller\\Desktop\\PUC\\Programação 3D\\img");
        BufferedImage img = ImageIO.read(new File(PATH, "black.png"));

        Color cor = new Color (255, 0, 0);
        BufferedImage newImg = linha(img, 100, 1, 1, 100, cor);

        ImageIO.write(newImg, "png", new File(PATH, "Linha.png"));
    }



    public static void main(String args[]) throws IOException {
        new Linha().run();
    }
}
