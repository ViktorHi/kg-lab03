package com.vikras.controller;

import com.sun.javafx.collections.ImmutableObservableList;
import com.vikras.processor.HistogramEqualization;
import com.vikras.processor.LinearContrast;
import com.vikras.processor.SmoothingFilter;
import com.vikras.processor.util.Images;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.vikras.processor.util.Images.getBufferedImage;

public class MainController {

    @FXML
    private Button selectBt;

    @FXML
    private Label selectLb;

    @FXML
    private ChoiceBox<ProcessorMode> modeCB;

    @FXML
    private ImageView beforeIV;

    @FXML
    private ImageView afterIV;

    @FXML
    private Label infoLabel;

    private static final int[][] filter = {
            {1, 3, 1},
            {3, 9, 3},
            {1, 3, 1}
    };
    private static final int w = 25;
    private static final SmoothingFilter smoothingFilter = new SmoothingFilter(filter, w);

    //    private static final ObservableList<ProcessorMode> MODES = new ImmutableObservableList<>(){
//        {
//            add(ProcessorMode.HISTOGRAM_EQUALIZATION);
//            add(ProcessorMode.LINEAR_CONTRAST);
//            add(ProcessorMode.SMOOTHING_FILTER);
//        }
//    };
    private static List<String> getSupportedExtensionList() {
        return new ArrayList<>() {
            {
                add(".jpg");
                add(".jpeg");
                add(".png");
                add(".bmp");
                add(".tif");
                add(".gif");
                add(".pcx");
            }
        };
    }

    private final DialogChoosers choosers = new DialogChoosers(getSupportedExtensionList());

    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;
        modeCB.getItems().add(ProcessorMode.HISTOGRAM_EQUALIZATION);
        modeCB.getItems().add(ProcessorMode.LINEAR_CONTRAST);
        modeCB.getItems().add(ProcessorMode.SMOOTHING_FILTER);
        modeCB.setValue(ProcessorMode.SMOOTHING_FILTER);
        modeCB.setOnAction(this::modeChanged);
        selectBt.setOnAction(this::selectBtActionHandler);
    }

    private void selectBtActionHandler(ActionEvent actionEvent) {
        Optional<File> file = choosers.showFileDialog(stage);
        file.ifPresent(e -> {
            try {
                Image image = Images.getImage(e.toPath());
                beforeIV.setImage(image);
                selectLb.setText("Selected: " + e.toString());
                processImage(modeCB.getValue());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void modeChanged(ActionEvent actionEvent) {
        ProcessorMode mode = modeCB.getValue();
        processImage(mode);
    }

    private void processImage(ProcessorMode mode) {
        Image image = beforeIV.getImage();
        if (image == null) return;

        Instant start = Instant.now();
        Image result = switch (mode) {
            case LINEAR_CONTRAST -> Images.buffIm2Image(LinearContrast.whiteBalanceBuffImage(getBufferedImage(image)));
            case HISTOGRAM_EQUALIZATION -> Images.buffIm2Image(HistogramEqualization.equalize(getBufferedImage(image)));
            case SMOOTHING_FILTER -> smoothingFilter.generate(image);
        };
        afterIV.setImage(result);
        Instant end = Instant.now();

        int seconds = (int) Duration.between(start, end).getSeconds();
        long nanos = Duration.between(start, end).getNano();

        var text = switch (seconds) {
            case 0 -> "Processed in: " + seconds + " seconds";
            default -> "Processed in: " + TimeUnit.NANOSECONDS.toMillis(nanos) + " milliseconds";
        };

        setInfo(text);
    }

    private void setInfo(String text) {
        infoLabel.setText(text);
    }


}
