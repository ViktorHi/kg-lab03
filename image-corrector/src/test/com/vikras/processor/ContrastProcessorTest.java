package com.vikras.processor;

import com.vikras.processor.util.Images;
import javafx.scene.image.Image;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class ContrastProcessorTest {


    @Test
    public void smooth() throws IOException {
        int[][] filter = {
                {1, 3, 1},
                {3, 9, 3},
                {1, 3, 1}
        };
        SmoothingFilter pr = new SmoothingFilter(filter, 25);

        Path of = Path.of("/home/viktor/Загрузки/examples/1/");
        Image image = Images.getImage(Path.of(of.toString(), "image.jpeg"));


        Image generate = pr.generate(image);
        Images.saveImage(generate, Path.of(of.toString(), "smooth.png"));

//        List<List<Color>> allColors = pr.getAllColors(image);
//        OptionalDouble average = allColors.stream()
//                .flatMap(e -> e.stream().map(Color::getBlue))
//                .mapToDouble(e -> e).average();
//        System.out.println(average.orElse(0));
//
//        List<List<Color>> converted = pr.convert(Color::brighter, allColors);
//
//        Image resImage = pr.generateImage(converted);
//        Images.saveImage(resImage, Path.of("/home/viktor/Загрузки/images1.png"));
    }

    @Test
    public void linearContrast() throws IOException {

        Path of = Path.of("/home/viktor/Загрузки/examples/2/");
        Image image = Images.getImage(Path.of(of.toString(), "images.jpeg"));

        BufferedImage bufferedImage = Images.getBufferedImage(image);

        BufferedImage white = LinearContrast.whiteBalanceBuffImage(bufferedImage);
        BufferedImage equalize = HistogramEqualization.equalize(bufferedImage);


        Images.saveBufferedImage(white, Path.of(of.toString(), "images1.png"));
        Images.saveBufferedImage(equalize, Path.of(of.toString(), "images2.png"));

    }


}