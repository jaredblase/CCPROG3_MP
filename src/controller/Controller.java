package controller;

public abstract class Controller {
    protected MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        update();
    }

    public abstract void update();
}
