package View.ManagerMenu;

import Controller.ManagerController;
import View.Menu;
import View.SellerMenu.ManageProductForSeller;
import Exception.*;

import java.util.List;
import java.util.Optional;

public class ManageProductForManager extends Menu {
    private static ManageProductForManager menu;

    private static ManagerController managerController = ManagerController.getInstance();

    public ManageProductForManager(String name) {
        super(name);
    }

    public static ManageProductForManager getInstance(String name) {
        if (menu == null) {
            menu = new ManageProductForManager(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in ManageProductsByManagerMenu."));
    }

    public void remove(List<String> inputs) {
        String id=inputs.get(0);
        try {
            managerController.removeProduct(id);
            System.out.println("product removed.");
        } catch (ProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println("You are in manage products by manager menu.");
    }
}
