package com.vikras.controller;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DialogChoosers {
    private FileChooser fileChooser = new FileChooser();
    private DirectoryChooser directoryChooser = new DirectoryChooser();

    public DialogChoosers(List<String> types) {
        var extensions = types.stream()
                .map(e -> new FileChooser.ExtensionFilter("Image", e))
                .collect(Collectors.toList());
//        fileChooser.getExtensionFilters().addAll(extensions);
    }


    public Optional<File> showFileDialog(Stage stage){
        return Optional.ofNullable(fileChooser.showOpenDialog(stage));
    }

    public Optional<File> showDirectoryDialog(Stage stage){
        return Optional.ofNullable( directoryChooser.showDialog(stage));
    }
}
