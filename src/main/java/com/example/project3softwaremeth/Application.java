package com.example.project3softwaremeth;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 * class to help launch program
 */
public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Tuition Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * main method to run program
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}