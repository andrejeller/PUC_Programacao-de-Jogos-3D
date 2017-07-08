import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.floor;
import static java.lang.Math.pow;
import static java.lang.Math.round;

/**
 * Created by andrejeller on 05/06/2017.
 */
public class equalize {

    static int[] calcularHistogramRed(BufferedImage img){
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

    static int[] calcularHistogramGreen(BufferedImage img){
        int[] cores = new int[256];

        for (int y = 0; y < img.getHeight(); y++){
            for (int x = 0; x < img.getWidth(); x++){
                //0 - 255
                Color pixel = new Color(img.getRGB(x, y));
                int c = pixel.getGreen(); // considerando cinza rgb igual
                //System.out.println(c);
                cores [c] += 1;
            }
        }
        return cores;
    }

    static int[] calcularHistogramBlue(BufferedImage img){
        int[] cores = new int[256];

        for (int y = 0; y < img.getHeight(); y++){
            for (int x = 0; x < img.getWidth(); x++){
                //0 - 255
                Color pixel = new Color(img.getRGB(x, y));
                int c = pixel.getBlue(); // considerando cinza rgb igual
                //System.out.println(c);
                cores [c] += 1;
            }
        }
        return cores;
    }


    static int[] acumHistogramRed(int[] coresTabela){
        int[] acumHist = new int[256];

        for (int i = 0; i < coresTabela.length; i++){
            if (i == 0) acumHist[i] = coresTabela[i];
            else acumHist[i] = coresTabela[i] + acumHist[i-1];
        }

        return acumHist;
    }

    static int[] acumHistogramGreen(int[] coresTabela){
        int[] acumHist = new int[256];

        for (int i = 0; i < coresTabela.length; i++){
            if (i == 0) acumHist[i] = coresTabela[i];
            else acumHist[i] = coresTabela[i] + acumHist[i-1];
        }

        return acumHist;
    }

    static int[] acumHistogramBlue(int[] coresTabela){
        int[] acumHist = new int[256];

        for (int i = 0; i < coresTabela.length; i++){
            if (i == 0) acumHist[i] = coresTabela[i];
            else acumHist[i] = coresTabela[i] + acumHist[i-1];
        }

        return acumHist;
    }

    public BufferedImage equalizar(BufferedImage img){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        float primeiroTomR = 0;
        float primeiroTomG = 0;
        float primeiroTomB = 0;

        int[] NovosTonsR = new int[256];
        int[] NovosTonsG = new int[256];
        int[] NovosTonsB = new int[256];

        int[] histogramR = calcularHistogramRed(img);
        int[] histogramG = calcularHistogramGreen(img);
        int[] histogramB = calcularHistogramBlue(img);


        int[] accumulatedR = acumHistogramRed(histogramR);
        int[] accumulatedG = acumHistogramGreen(histogramG);
        int[] accumulatedB = acumHistogramBlue(histogramB);


        for(int i = 0; i < histogramR.length; i++){
            if(histogramR[i] != 0)
                primeiroTomR = i;
            break;
        }

        for(int i = 0; i < histogramG.length; i++){
            if(histogramG[i] != 0)
                primeiroTomG = i;
            break;
        }

        for(int i = 0; i < histogramB.length; i++){
            if(histogramB[i] != 0)
                primeiroTomB = i;
            break;
        }


        //h(v) = round((ha(v) - hmin) x (pixels - hmim)) x (tons - 1)
        //Novo Tom = round((HistAcum[i] - Primeiro Tom) x (pixels da Img - Primeiro Tom)) x 255
        for (int i = 0; i < histogramR.length; i++)
            NovosTonsR[i] = Math.round(((accumulatedR[i] - primeiroTomR) / (img.getWidth() * img.getHeight() - primeiroTomR)) * 255);

        for (int i = 0; i < histogramG.length; i++)
            NovosTonsG[i] = Math.round(((accumulatedG[i] - primeiroTomG) / (img.getWidth() * img.getHeight() - primeiroTomG)) * 255);

        for (int i = 0; i < histogramB.length; i++)
            NovosTonsB[i] = Math.round(((accumulatedB[i] - primeiroTomB) / (img.getWidth() * img.getHeight() - primeiroTomB)) * 255);


        for(int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){

                Color color = new Color(img.getRGB(x, y));

                int tomR = color.getRed();
                int tomG = color.getGreen();
                int tomB = color.getBlue();

                int tomFinalR = NovosTonsR[tomR];
                int tomFinalG = NovosTonsG[tomG];
                int tomFinalB = NovosTonsB[tomB];

                Color newColor = new Color(tomFinalR, tomFinalG, tomFinalB);
                out.setRGB(x, y, newColor.getRGB());
            }
        }

        return out;
    }

    public void run () throws IOException {
        File PATH = new File("C:\\Users\\andrejeller\\Desktop\\PUC\\Programação 3D\\img\\cor");
        BufferedImage lara = ImageIO.read(new File(PATH, "lara.png"));


        BufferedImage eqLara = equalizar(lara);


        ImageIO.write(eqLara, "png", new File(PATH, "eqLara.png"));

    }

    public static void main (String[] args) throws IOException {
        new equalize().run();
    }

}
