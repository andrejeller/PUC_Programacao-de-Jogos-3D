import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by AndreJeller on 01/04/2017.
 */

public class pixelate {

    public int Saturate (int value){
        if(value > 255)
            return 255;
        if (value < 0)
            return 0;

        return value;
    }

    public float[][] embossKernel() {
        return new float[][]{
                {-2.0f, -1.0f, 0.0f},
                {-1.0f, 1.0f, 1.0f},
                {0.0f, 1.0f, 2.0f}
        };
    }
    public float[][] blurKernel() {
        return new float[][]{
                {1.0f/16.0f, 2.0f/16.0f, 1.0f/16.0f},
                {2.0f/16.0f, 4.0f/16.0f, 2.0f/16.0f},
                {1.0f/16.0f, 2.0f/16.0f, 1.0f/16.0f}
        };
    }
    public float[][] blurKernel2() {
        return new float[][]{
                {0.0f, 1.5f, 0.0f},
                {1.5f, 1.5f, 1.5f},
                {0.0f, 1.5f, 0.0f}
        };
    }

    public  BufferedImage pix3 (BufferedImage img, int tamPixel){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        out = embossEffect(img, embossKernel());
        out = blurEffect(out, blurKernel());
        out = PixaleteEffect(out, tamPixel);

        return out;
    }

    public BufferedImage PixaleteEffect(BufferedImage img, int tamPixel){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for(int y = 0; y < img.getHeight(); y += tamPixel){
            for(int x = 0; x < img.getWidth(); x+= tamPixel){

                int metadePx = tamPixel / 2;
                int metdX = (x + metadePx) > img.getWidth()? x : (x + metadePx);
                int metdY = (y + metadePx) > img.getHeight() ? y : (y + metadePx);

                Color midPixel = new Color(img.getRGB(metdX, metdY));

                for (int ky = 0; ky < tamPixel; ky++){
                    for (int kx = 0; kx < tamPixel; kx++){

                        int px = x + (kx - metadePx);
                        int py = y + (ky - metadePx);

                        if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight()) // caso seja maior que a img continue
                            continue;

                        out.setRGB(px, py, midPixel.getRGB());
                    }
                }
            }
        }
        return out;
    }

    public BufferedImage embossEffect(BufferedImage img, float[][] emboss){
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
                        r += color.getRed() * emboss[kx][ky];
                        g += color.getRed() * emboss[kx][ky];
                        b += color.getRed() * emboss[kx][ky];
                    }
                }
                Color newColor = new Color (Saturate(r), Saturate(g),Saturate(b));
                out.setRGB(x, y, newColor.getRGB());
            }
        }
        return out;
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

    public void run () throws IOException {
        File PATH = new File("C:\\Users\\AndreJeller.DELLER\\Desktop\\PUC\\Programação 3D\\img\\cor");
        BufferedImage puppy = ImageIO.read(new File(PATH, "puppy.png"));

        BufferedImage blurPuppy = blurEffect(puppy, blurKernel());
        BufferedImage embossPuppy = embossEffect(puppy, embossKernel());
        BufferedImage pixeletePuppy = PixaleteEffect(puppy, 7);
        BufferedImage pix3 = pix3(puppy, 4);

        ImageIO.write(pixeletePuppy, "png", new File(PATH, "pixeletePuppy.png"));
        ImageIO.write(embossPuppy, "png", new File(PATH, "embossPuppy.png"));
        ImageIO.write(blurPuppy, "png", new File(PATH, "blurPuppy.png"));
        ImageIO.write(pix3, "png", new File(PATH, "pix3puppy.png")); //  os 3 em 1

    }

    public static void main (String[] args) throws IOException {
        new pixelate().run();
    }
}
