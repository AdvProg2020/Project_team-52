package View.CustomerMenu;

import Controller.CustomerController;
import View.Interface.Shows;
import View.Menu;
import Exception.*;

import java.util.List;
import java.util.Optional;

public class ViewOrderForCustomer extends Menu {
    private static ViewOrderForCustomer menu;

    private static CustomerController customerController = CustomerController.getInstance();

    public ViewOrderForCustomer(String name) {
        super(name);
    }

    public static ViewOrderForCustomer getInstance(String name) {
        if (menu == null) {
            menu = new ViewOrderForCustomer(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in ViewOrdersByBuyerMenu."));
    }

    public void showOrder(List<String> inputs) {
        String id = inputs.get(0);
        try {
            System.out.println(
                    Shows.getShowLogHistory().apply(customerController.showOrder(id))
            );
        } catch (LogHistoryDoesNotExistException e) {
            System.out.println("log history does not exist");
        }
        System.out.println("Ok.");
    }

    public void rate(List<String> inputs) {
        String id = inputs.get(0);
        String number = inputs.get(1);
        try {
            customerController.rate(id, number);
        } catch (ProductDoesNotExistException | CannotRateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("OK.");
    }

    @Override
    public void show() {
        System.out.println("view orders for customer menu.");
    }

    @Override
    public void help() {
        super.help();
        System.out.println("showOrder [order id] :To show orders from you" + System.lineSeparator() +
                "rate [product id] [1-5] :To rate a product from 1 to 5" + System.lineSeparator() +
                "----------------------------------------------"
        );
    }
}
