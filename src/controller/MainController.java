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
    public static final int MAIN_MENU_VIEW = 4;

//    /** These are the controllers */
//    private LoginController loginController;
//    private RegistrationController registrationController;
//    private Registration2Controller registration2Controller;
//    private MainMenuController mainMenuController;

    /** This is the user logged in */
    private Citizen userModel;

    /** These are the view components */
    private Stage primaryStage;
    private Scene scene;

    public MainController(Stage stage) {
        UserSystem.loadSystem();
        primaryStage = stage;
        primaryStage.setTitle("Tracing App");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        initControllers();

        changeScene(LOGIN_VIEW);
    }
//
//    private void initControllers() {
//        loginController = new LoginController(this);
//        registrationController = new RegistrationController(this);
//        registration2Controller = new Registration2Controller(this);
//        mainMenuController = new MainMenuController(this);
//    }

    public void changeScene(int view) {
        FXMLLoader loader = new FXMLLoader();

        switch (view) {
            case 1 -> loader.setLocation(getClass().getResource("/view/Login Screen.fxml"));
            case 2 -> loader.setLocation(getClass().getResource("/view/Registration Form Part 1.fxml"));
            case 3 -> loader.setLocation(getClass().getResource("/view/Registration Form Part 2.fxml"));
            case 4 -> loader.setLocation(getClass().getResource("/view/Main Menu.fxml"));
        }


        try {
            primaryStage.setScene(new Scene(loader.load(), Color.TRANSPARENT));
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Controller controller = loader.getController();
        controller.setMainController(this);
        controller.update();
    }

    public void setUserModel(Citizen userModel) {
        this.userModel = userModel;
    }

    public Citizen getUserModel() {
        return userModel;
    }
}