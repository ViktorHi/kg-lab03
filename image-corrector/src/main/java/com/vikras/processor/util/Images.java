package com.vikras.processor.util;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import javafx.embed.swing.SwingFXUtils;

public interface Images {
    static Image getImage(Path path) throws IOException {
        return new Image(Files.newInputStream(path, StandardOpenOption.READ));
    }

    static BufferedImage getBufferedImage(Image image){
        return SwingFXUtils.fromFXImage(image, null);
    }

    static boolean saveBufferedImage(BufferedImage image, Path path) throws IOException {
        path.toFile().createNewFile();
        try {
            return ImageIO.write(image, "png", path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean saveImage(Image image, Path path) throws IOException {
        BufferedImage bImage = getBufferedImage(image);
        return saveBufferedImage(bImage, path);
    }

    static Image buffIm2Image(BufferedImage image){
        return SwingFXUtils.toFXImage(image, null);
    }

    static int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha; newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;

    }

    static ArrayList<int[]> imageHistogram(BufferedImage input) {
        int[] rhistogram = new int[256];
        int[] ghistogram = new int[256];
        int[] bhistogram = new int[256];

        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {

                int red = new Color(input.getRGB(i, j)).getRed();
                int green = new Color(input.getRGB(i, j)).getGreen();
                int blue = new Color(input.getRGB(i, j)).getBlue();

                // Increase the values of colors
                rhistogram[red]++; ghistogram[green]++; bhistogram[blue]++;
            }
        }

        ArrayList<int[]> hist = new ArrayList<>();
        hist.add(rhistogram);
        hist.add(ghistogram);
        hist.add(bhistogram);

        return hist;
    }
}
