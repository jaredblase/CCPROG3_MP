package controller;

public abstract class Controller {
    protected MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public abstract void update();
}
