package Controller;

public class GuestController {

    private static ControllerSection controllerSection = ControllerSection.getInstance();

    private static GuestController guestController = new GuestController();

    public static GuestController getInstance() {
        return guestController;
    }


}
