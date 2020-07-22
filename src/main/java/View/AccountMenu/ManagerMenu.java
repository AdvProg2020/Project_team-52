package View.AccountMenu;

import Controller.ManagerController;
import View.Interface.Shows;
import View.LoginAndSigup.UserMenu;
import View.MainMenu;
import View.ManageInfoMenu;
import View.ManagerMenu.ManageCategoryForManager;
import View.ManagerMenu.ManageProductForManager;
import View.ManagerMenu.ManageRequestForManager;
import View.Menu;
import View.MenuManage;
import Exception.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerMenu extends Menu {
    private static ManagerMenu menu;

    private static ManagerController managerController = ManagerController.getInstance();

    private ManagerMenu(String name) {
        super(name);
    }

    public static ManagerMenu getInstance(String name) {
        if (menu == null) {
            menu = new ManagerMenu(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in ManagerMenu."));
    }

    public void openMainMenu() {
        MenuManage.setLatestMenu(MainMenu.getMenu());
    }

    public void openManageProductsMenu() {
        MenuManage.setLatestMenu(ManageProductForManager.getMenu());
    }

    public void viewPersonalInfo() {
        System.out.println(
                Shows.getShowInfo().apply(managerController.viewPersonalInfo())
        );
        MenuManage.setLatestMenu(ManageInfoMenu.getMenu());
    }


    public void createDiscountCode() {
        System.out.println("Enter discountCode information in this format :" + System.lineSeparator() +
                "DiscountCode :[start date] :[end data] :[percent] :[max amount] :[frequent]"
        );

        Matcher matcher = Pattern.compile("^DiscountCode :(.+) :(.+) :(.+) :(.+) :(.+)$")
                .matcher(scanner.nextLine().trim());

        if (!matcher.find()) {
            System.out.println(" Enter information in correct format.");
            return;
        }
    }

        public void openManageRequestsMenu() {
            System.out.println("All request: ");
            managerController.showAllRequests().forEach(request ->
                    System.out.println(Shows.getShowRequest().apply(request))
            );
        }


    public void openManageCategoriesMenu() {
            System.out.println("All categories: ");
            managerController.showAllCategories().forEach(category ->
                    System.out.println(Shows.getShowCategory().apply(category))
            );
            MenuManage.setLatestMenu(ManageCategoryForManager.getMenu());
        }

        public void logout() {
        MainMenu.getMenu().setParentMenu(UserMenu.getMenu());
        MenuManage.setLatestMenu(UserMenu.getMenu());
    }

        public void show() {
        System.out.println(
                "You are in managerMenu" + System.lineSeparator() +
                        "-------------------SubMenus-------------------" + System.lineSeparator() +
                        "1.MainMenu" + System.lineSeparator() +
                        "2.ManageUsersMenu" + System.lineSeparator() +
                        "3.ManageProductsMenu" + System.lineSeparator() +
                        "4.ManageRequestsMenu" + System.lineSeparator() +
                        "5.ManageCategoriesMenu" + System.lineSeparator() +
                        "6.ManageIfo" + System.lineSeparator() +
                        "7.ViewDiscountCodesByManagerMenu" + System.lineSeparator() +
                        "----------------------------------------------"
        );
    }
}
