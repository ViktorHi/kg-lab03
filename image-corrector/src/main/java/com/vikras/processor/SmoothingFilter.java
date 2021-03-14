package com.vikras.processor;

import com.vikras.processor.util.Images;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SmoothingFilter {

    private final int[][] filter;
    private final int w;

    public SmoothingFilter(int[][] filter, int w) {
        this.filter = filter;
        this.w = w;
    }

    public Image generate(Image image) {
        var allColors = getAllColors(image);
        var lines = smoothingFilter(allColors);
        return generateImage(lines);
    }

    public Image linerTransformation(Image source) {

        int width = (int) source.getWidth();
        int height = (int) source.getHeight();

        WritableImage wImage = new WritableImage(width, height);

        //Reading color from the loaded image
        PixelReader pixelReader = source.getPixelReader();

        //getting the pixel writer
        PixelWriter writer = wImage.getPixelWriter();

        //Reading the color of the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //Retrieving the color of the pixel of the loaded image
                Color color = pixelReader.getColor(x, y);

                //Setting the color to the writable image
                writer.setColor(x, y, color.darker());
            }
        }

        return null;
    }

    protected List<List<Color>> getAllColors(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        List<List<Color>> ans = new ArrayList<>();
        for (int h = 0; h < image.getHeight(); h++) {
            var line = new ArrayList<Color>();
            for (int w = 0; w < image.getWidth(); w++) {
                Color color = pixelReader.getColor(w, h);
                line.add(color);
            }
            ans.add(line);
        }
        return ans;
    }

    protected List<List<Color>> convert(Function<Color, Color> converter, List<List<Color>> source) {
        return source.stream()
                .map(e -> e.stream()
                        .map(converter)
                        .collect(Collectors.toList())
                ).collect(Collectors.toList());

    }

    protected List<List<Color>> smoothingFilter(List<List<Color>> list) {
        var shift = filter.length / 2;
        List<List<Color>> ans = new ArrayList<List<Color>>();

        ans.add(list.get(0));
        for (int i = shift; i < list.size() - shift; i++) {
            List<Color> line = cloneList(list.get(i));
            for (int j = shift; j < list.get(0).size() - shift; j++) {
                line.set(j,convertColor(list, i, j, shift));
            }
            ans.add(line);
        }
        ans.add(list.get(list.size()-1));
        return ans;
    }

    private List<Color>cloneList(List<Color> list){
        return (List<Color>) ((ArrayList<Color>) list).clone();
    }
    protected Color convertColor(List<List<Color>> list, int i, int j, int shift) {
        double green = 0;
        double red = 0;
        double blue = 0;
        double opacity = 0;

        for (int f = -shift; f < filter.length - shift; f++) {
            for (int k = -shift; k < filter[0].length - shift; k++) {
                Color col = list.get(i + f).get(j + k);
                blue += col.getBlue() * filter[f + shift][k + shift];
                red += col.getRed() * filter[f + shift][k + shift];
                green += col.getGreen() * filter[f + shift][k + shift];
                opacity += col.getOpacity() * filter[f + shift][k + shift];
            }
        }

        return new Color(red / w, green / w, blue / w, opacity/w);
    }


    protected Image generateImage(List<List<Color>> list) {
        int width = list.get(0).size();
        int height = list.size();
        WritableImage wImage = new WritableImage(width, height);
        PixelWriter writer = wImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                writer.setColor(x, y, list.get(y).get(x));
            }
        }
        return wImage;
    }


}
