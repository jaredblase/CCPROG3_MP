package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("MainMenu.fxml"))));
            primaryStage.setTitle("Tracing App");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(Stage stage, String file) {
        try {
            stage.getScene().setRoot(FXMLLoader.load(getClass().getResource(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
