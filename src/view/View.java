package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class View extends Application {
//    private double xOffset = 0;
//    private double yOffset = 0;

    public static void main(String[] args) {
        // temporary stuff

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//            makeDraggable(primaryStage, root);
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
