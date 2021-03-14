package com.vikras;


import com.vikras.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(Locale.ENGLISH);
        FXMLLoader loader = new FXMLLoader(App.class.getResource("main.fxml"));
        Parent root = loader.load();
        stage.setTitle("Color converter");
        stage.setScene(new Scene(root, 1000, 750));
        MainController controller = loader.getController();


        Image icon = new Image(App.class.getResourceAsStream("main_icon.jpg"));
        stage.getIcons().add(icon);

        stage.show();

        controller.init(stage);
    }


    public static void main(String[] args) {
        launch();
    }

}