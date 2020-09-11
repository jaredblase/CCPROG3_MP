package driver;

import controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Driver extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new MainController(stage);
    }
}
