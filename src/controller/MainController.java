package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Citizen;
import model.UserSystem;

import java.io.IOException;

/**
 * The MainController class holds the information of the user logged in. This class
 * also handles the display and switching of different scenes.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 */
public class MainController {
    /** Value that corresponds to the login scene. */
    public static final int LOGIN_VIEW = 1;
    /** Value that corresponds to the first register scene. */
    public static final int REGISTER_VIEW = 2;
    /** Value that corresponds to the second register scene. */
    public static final int REGISTER_2_VIEW = 3;
    /** Value that corresponds to the profile scene. */
    public static final int PROFILE_VIEW = 4;
    /** Value that corresponds to the case table scene. */
    public static final int CASE_TABLE_VIEW = 5;
    /** Value that corresponds to the modify role scene. */
    public static final int MODIFY_ROLE_VIEW = 6;
    /** Value that corresponds to the trace scene. */
    public static final int TRACE_VIEW = 7;

    /** This is the user logged in. */
    private Citizen userModel;
    /** The Stage where the scenes are shown. */
    private final Stage primaryStage;

    /**
     * Constructs the main controller and sets up the view on the stage received.
     * @param stage the primary stage to be used.
     */
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

    /**
     * Handles the switching of scenes of the main stage.
     * @param view the number representing a specific scene to switch to.
     */
    public void changeScene(int view) {
        FXMLLoader loader = new FXMLLoader();

        // load the scene from the corresponding fxml file
        switch (view) {
            case 1 -> loader.setLocation(getClass().getResource("/view/Login Screen.fxml"));
            case 2 -> loader.setLocation(getClass().getResource("/view/Registration Form Part 1.fxml"));
            case 3 -> loader.setLocation(getClass().getResource("/view/Registration Form Part 2.fxml"));
            case 4 -> loader.setLocation(getClass().getResource("/view/Profile.fxml"));
            case 5 -> loader.setLocation(getClass().getResource("/view/Case Table View.fxml"));
            case 6 -> loader.setLocation(getClass().getResource("/view/Modify Role.fxml"));
            case 7 -> loader.setLocation(getClass().getResource("/view/Trace.fxml"));
        }

        try {
            primaryStage.setScene(new Scene(loader.load(), Color.TRANSPARENT));
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set up the controller of the scene
        Controller controller = loader.getController();
        controller.setMainController(this);
        primaryStage.show();
    }

    /**
     * Sets the user currently logged in.
     * @param userModel the user that is currently logged in.
     */
    public void setUserModel(Citizen userModel) {
        this.userModel = userModel;
    }

    /**
     * Gets the user currently logged in.
     * @return the user that is currently logged in.
     */
    public Citizen getUserModel() {
        return userModel;
    }
}