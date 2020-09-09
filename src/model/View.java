package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class View extends Application {
//    private double xOffset = 0;
//    private double yOffset = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login Screen.fxml"));
//            makeDraggable(primaryStage, root);
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root, Color.TRANSPARENT));
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setTitle("Tracing App");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(Node node, String file) {
        try {
            Stage stage = (Stage) node.getScene().getWindow();
            stage.getScene().setRoot(FXMLLoader.load(getClass().getResource(file)));
            stage.getScene().setFill(Color.TRANSPARENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void makeDraggable(Stage primaryStage, Parent root) {
//        root.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                xOffset = mouseEvent.getSceneX();
//                yOffset = mouseEvent.getSceneY();
//            }
//        });
//
//        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                primaryStage.setX(mouseEvent.getSceneX() - xOffset);
//                primaryStage.setY(mouseEvent.getScreenY() - yOffset);
//            }
//        });
//    }
}
