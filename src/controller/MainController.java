package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Citizen;
import model.UserSystem;

import java.io.IOException;

public class MainController {
    public static final int LOGIN_VIEW = 1;
    public static final int REGISTER_VIEW = 2;
    public static final int REGISTER_2_VIEW = 3;
    public static final int PROFILE_VIEW = 4;
    public static final int CASE_INFO_VIEW = 5;
    public static final int MODIFY_ROLE_VIEW = 6;

    /** This is the user logged in */
    private Citizen userModel;

    /** These are the view components */
    private final Stage primaryStage;

    public MainController(Stage stage) {
        // setup model
        UserSystem.loadSystem();

        // setup view
        primaryStage = stage;
        primaryStage.setTitle("Tracing App");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        changeScene(LOGIN_VIEW);
    }

    public void changeScene(int view) {
        FXMLLoader loader = new FXMLLoader();

        switch (view) {
            case 1 -> loader.setLocation(getClass().getResource("/view/Login Screen.fxml"));
            case 2 -> loader.setLocation(getClass().getResource("/view/Registration Form Part 1.fxml"));
            case 3 -> loader.setLocation(getClass().getResource("/view/Registration Form Part 2.fxml"));
            case 4 -> loader.setLocation(getClass().getResource("/view/Profile.fxml"));
            case 5 -> loader.setLocation(getClass().getResource("/view/Case Information.fxml"));
            case 6 -> loader.setLocation(getClass().getResource("/view/Modify Role.fxml"));
        }

        try {
            primaryStage.setScene(new Scene(loader.load(), Color.TRANSPARENT));
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Controller controller = loader.getController();
        controller.setMainController(this);
        primaryStage.show();
    }

    public void setUserModel(Citizen userModel) {
        this.userModel = userModel;
    }

    public Citizen getUserModel() {
        return userModel;
    }
}