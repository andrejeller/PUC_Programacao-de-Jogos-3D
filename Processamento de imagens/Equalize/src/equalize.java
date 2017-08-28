import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.floor;
import static java.lang.Math.pow;
import static java.lang.Math.round;

/**
 * Created by AndreJeller on 02/04/2017.
 */
public class equalize {

   static int[] calcularHistogram(BufferedImage img){
        int[] cores = new int[256];

        for (int y = 0; y < img.getHeight(); y++){
            for (int x = 0; x < img.getWidth(); x++){
                //0 - 255
                Color pixel = new Color(img.getRGB(x, y));
                int c = pixel.getRed(); // considerando cinza rgb igual
                //System.out.println(c);
                cores [c] += 1;
            }
        }
        return cores;
    }

    static int[] acumHistogram(int[] coresTabela){
        int[] acumHist = new int[256];

        for (int i = 0; i < coresTabela.length; i++){
            if (i == 0) acumHist[i] = coresTabela[i];
            else acumHist[i] = coresTabela[i] + acumHist[i-1];
        }

        return acumHist;
    }

    public BufferedImage equalizar(BufferedImage img){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        float primeiroTom = 0;
        int[] NovosTons = new int[256];
        int[] histogram = calcularHistogram(img);
        int[] accumulated = acumHistogram(histogram);


        for(int i = 0; i < histogram.length; i++){
            if(histogram[i] != 0)
                primeiroTom = i;
                break;
        }

        for (int i = 0; i < histogram.length; i++)
            NovosTons[i] = Math.round(((accumulated[i] - primeiroTom) / (img.getWidth() * img.getHeight() - primeiroTom)) * 255);
        //h(v) = round((ha(v) - hmin) x (pixels - hmim)) x (tons - 1)
        //Novo Tom = round((HistAcum[i] - Primeiro Tom) x (pixels da Img - Primeiro Tom)) x 255

        for(int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){

                Color color = new Color(img.getRGB(x, y));

                int tom = color.getRed(); // considerando ser cinza
                int tomFinal = NovosTons[tom];

                Color newColor = new Color(tomFinal, tomFinal, tomFinal); // considerando ser cinza
                out.setRGB(x, y, newColor.getRGB());
            }
        }

        return out;
    }

    public void run () throws IOException {
        File PATH = new File("C:\\Users\\AndreJeller.DELLER\\Desktop\\PUC\\Programação 3D\\img\\gray");
        BufferedImage car = ImageIO.read(new File(PATH, "car.png"));
        BufferedImage cars = ImageIO.read(new File(PATH, "cars.jpg"));
        BufferedImage crowd = ImageIO.read(new File(PATH, "crowd.png"));
        BufferedImage montanha = ImageIO.read(new File(PATH, "montanha.jpg"));
        BufferedImage university = ImageIO.read(new File(PATH, "university.png"));

        BufferedImage eqCar = equalizar(car);
        BufferedImage eqCars = equalizar(cars);
        BufferedImage eqCrowd = equalizar(crowd);
        BufferedImage eqMontanha = equalizar(montanha);
        BufferedImage eqUniversity = equalizar(university);

        ImageIO.write(eqCar, "png", new File(PATH, "eqCar.png"));
        ImageIO.write(eqCars, "png", new File(PATH, "eqCars.png"));
        ImageIO.write(eqCrowd, "png", new File(PATH, "eqCrowd.png"));
        ImageIO.write(eqMontanha, "png", new File(PATH, "eqMontanha.png"));
        ImageIO.write(eqUniversity, "png", new File(PATH, "eqUniversity.png"));
    }

    public static void main (String[] args) throws IOException {
        new equalize().run();
    }

}
