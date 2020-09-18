package controller;

/**
 * This abstract class is extended by Controllers that need access
 * to the main Controller.
 */
public abstract class Controller {
    /** The current main controller running */
    protected MainController mainController;

    /**
     * Sets the main controller with the one currently running.
     * @param mainController the main controller.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        update();
    }

    /**
     * Updated any component that is dependent on the main controller.
     */
    protected abstract void update();
}
