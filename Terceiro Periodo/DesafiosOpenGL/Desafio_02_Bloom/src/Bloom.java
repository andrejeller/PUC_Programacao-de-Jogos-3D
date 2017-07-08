import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by andrejeller on 05/06/2017.
 */
public class Bloom {
    public int Saturate (int value){
        if(value > 255)
            return 255;
        if (value < 0)
            return 0;

        return value;
    }


    public float[][] blurKernel() {
        return new float[][]{
                {1.0f/16.0f, 2.0f/16.0f, 1.0f/16.0f},
                {2.0f/16.0f, 4.0f/16.0f, 2.0f/16.0f},
                {1.0f/16.0f, 2.0f/16.0f, 1.0f/16.0f}
        };
    }



    public BufferedImage blurEffect(BufferedImage img, float[][] blur){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++){
            for (int x = 0; x < img.getWidth(); x++){

                int r = 0, g = 0, b = 0;

                for (int kx = 0; kx < 3; kx++ ){
                    for (int ky = 0; ky < 3; ky++){
                        int px = x + (kx -1);
                        int py = y + (ky -1);

                        if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight())
                            continue;// se esta fora da imagem, esquece e continua

                        Color color = new Color(img.getRGB(px, py));
                        r += color.getRed() * blur[kx][ky];
                        g += color.getGreen() * blur[kx][ky];
                        b += color.getBlue() * blur[kx][ky];
                    }
                }
                Color newColor = new Color (Saturate(r), Saturate(g),Saturate(b));
                out.setRGB(x, y, newColor.getRGB());
            }
        }
        return out;
    }

    public BufferedImage soma(BufferedImage img,BufferedImage img1, BufferedImage img2, BufferedImage img3){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++){
            for (int x = 0; x < img.getWidth(); x++){
                int r = 0, g = 0, b = 0;

                Color color = new Color(img.getRGB(x, y) + img1.getRGB(x, y) + img2.getRGB(x, y) + img3.getRGB(x, y));
                r += color.getRed();
                g += color.getGreen();
                b += color.getBlue();

                Color newColor = new Color (Saturate(r), Saturate(g),Saturate(b));
                out.setRGB(x, y, newColor.getRGB());
            }
        }

        return out;
    }

    public void run () throws IOException {
        File PATH = new File("C:\\Users\\andrejeller\\Desktop\\PUC\\Programação 3D\\img\\cor");
        BufferedImage metroid = ImageIO.read(new File(PATH, "metroid1.jpg"));

        BufferedImage kernel5 = blurEffect(metroid, blurKernel());
        BufferedImage kernel11 = blurEffect(metroid, blurKernel());
        BufferedImage kernel21 = blurEffect(metroid, blurKernel());
        BufferedImage kernel45 = blurEffect(metroid, blurKernel());

        BufferedImage soma = soma(kernel5, kernel11, kernel21, kernel45);

       ImageIO.write(soma, "jpg", new File(PATH, "bloomMetroid.png"));

    }

    public static void main (String[] args) throws IOException {
        new Bloom().run();
    }

}
