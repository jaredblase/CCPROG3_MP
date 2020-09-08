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
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("MainLayout.fxml"))));
            primaryStage.setTitle("Tracing App");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
