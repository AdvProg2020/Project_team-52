package View.ManagerMenu;

import Controller.ManagerController;
import Model.Request;
import View.Interface.Shows;
import View.Menu;
import Exception.*;

import java.util.List;
import java.util.Optional;

public class ManageRequestForManager extends Menu {
    private static ManageRequestForManager menu;

    private static ManagerController managerController = ManagerController.getInstance();

    public ManageRequestForManager(String name) {
        super(name);
    }

    public static ManageRequestForManager getInstance(String name) {
        if (menu == null) {
            menu = new ManageRequestForManager(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in ManageRequestsByManagerMenu."));
    }

    public void showDetails(List<String> inputs) {
        String id = inputs.get(0);
        try {
            Request request = managerController.detailsOfRequest(id);
            System.out.println(
                    Shows.getShowRequest().apply(request)
            );

        } catch (RequestDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void acceptRequest(List<String> inputs) {
        String id = inputs.get(0);
        try {
            managerController.acceptRequest(id);
            System.out.println("Request accepted.");
        } catch (RequestDoesNotExistException | AccountDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void declineRequest(List<String> inputs) {
        String id = inputs.get(0);
        try {
            managerController.denyRequest(id);
            System.out.println("Request declined.");
        } catch (RequestDoesNotExistException | AccountDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println("You're in ManageRequestsByManagerMenu.");
    }

    @Override
    public void help() {
        super.help();
        System.out.println(
                "showDetails [requestId]: To show details" + System.lineSeparator() +
                        "acceptRequest [requestId]: To accept request" + System.lineSeparator() +
                        "declineRequest [requestId]: To decline request" + System.lineSeparator() +
                        "----------------------------------------------"
        );
    }
}
