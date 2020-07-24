package View.AccountMenu;

import Controller.CustomerController;
import View.CustomerMenu.ViewCartForCustomer;
import View.CustomerMenu.ViewOrderForCustomer;
import View.Interface.Shows;
import View.LoginAndSigup.UserMenu;
import View.MainMenu;
import View.ManageInfoMenu;
import View.Menu;
import View.MenuManage;
import Exception.*;

import java.util.List;
import java.util.Optional;

public class CustomerMenu extends Menu {
    private static CustomerController customerController = CustomerController.getInstance();

    private static CustomerMenu menu;

    private CustomerMenu(String name) {
        super(name);
    }

    public static CustomerMenu getInstance(String name) {
        if (menu == null) {
            menu = new CustomerMenu(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in buyerMenu."));
    }

    public void openMainMenu() {
        MenuManage.setLatestMenu(MainMenu.getMenu());
    }

    public void viewPersonalInfo() {
        System.out.println(
                Shows.getShowInfo().apply(customerController.viewPersonalInfo())
        );
        MenuManage.setLatestMenu(ManageInfoMenu.getMenu());
    }

    public void viewCart() {
        MenuManage.setLatestMenu(ViewCartForCustomer.getMenu());
    }

    public void viewOrders() {
        try {
            System.out.println("Orders:");
            customerController.viewOrders().forEach(logHistory ->
                    System.out.println(Shows.getShowLogHistory().apply(logHistory))
            );

        } catch (LogHistoryDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
        MenuManage.setLatestMenu(ViewOrderForCustomer.getMenu());
    }

    public void viewBalance() {
        System.out.println(customerController.viewBalance());
    }



    public void logout() {
        MainMenu.getMenu().setParentMenu(UserMenu.getMenu());
        MenuManage.setLatestMenu(UserMenu.getMenu());
    }

    public void chargeAccount(List<String> input) {
        String amount = input.get(0);
        try {
            customerController.chargeAccount(amount);
            System.out.println("Successful");

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println(
                "You're in CustomerMenu" + System.lineSeparator() +
                        "-------------------SubMenus-------------------" + System.lineSeparator() +
                        "1.MainMenu" + System.lineSeparator() +
                        "2.ManageInfo" + System.lineSeparator() +
                        "3.CartMenu" + System.lineSeparator() +
                        "4.OrdersMenu" + System.lineSeparator() +
                        "----------------------------------------------"
        );
    }

    @Override
    public void help() {
        super.help();
        System.out.println(
                "openMainMenu:to open main menu" + System.lineSeparator() +
                        "viewPersonalInfo : To open manageInfo menu" + System.lineSeparator() +
                        "viewCart : To open cart menu" + System.lineSeparator() +
                        "viewBalance : To show balance" + System.lineSeparator() +
                        "viewDiscountCodes : To show discounts" + System.lineSeparator() +
                        "viewOrders : To open Orders menu" + System.lineSeparator() +
                        "logout : To logout" + System.lineSeparator() +
                        "charge Account [amount( Just Integer)] : To logout" + System.lineSeparator() +
                        "----------------------------------------------"
        );
    }

}
