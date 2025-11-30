package controller;



public class HomeControllerSingleton {

    private static HomeController homeController;

    public static void setHomeController(HomeController controller) {
        homeController = controller;
    }

    public static HomeController getInstance() {
        return homeController;
    }
}
