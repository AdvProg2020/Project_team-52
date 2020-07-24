package View.LoginAndSigup;

import Controller.ControllerSection;
import Controller.LoginController;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import View.AccountMenu.CustomerMenu;
import View.AccountMenu.ManagerMenu;
import View.AccountMenu.SellerMenu;
import View.Interface.Shows;
import View.MainMenu;
import View.Menu;
import View.MenuManage;
import Exception.*;

import java.util.List;
import java.util.Optional;

public class LogInMenu extends Menu {
    private static LogInMenu menu;

    public static LogInMenu getInstance(String name) {
        if (menu == null) {
            menu = new LogInMenu(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in LogInMenu."));
    }

    private LogInMenu(String name) {
        super(name);
    }

    public void login(List<String> inputs) {
        Account account;

        try {
            account = LoginController.getInstance().login(inputs.get(0), inputs.get(1));
        } catch (AccountDoesNotExistException | UserNameTooShortException | UserNameInvalidException | PasswordInvalidException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (account instanceof Manager) {
            MenuManage.setLatestMenu(ManagerMenu.getMenu());
        } else if (account instanceof Customer) {
            MenuManage.setLatestMenu(CustomerMenu.getMenu());
        } else if (account instanceof Seller) {
            MenuManage.setLatestMenu(SellerMenu.getMenu());
        }

        ControllerSection.getInstance().setAccount(account);
        MenuManage.getLatestMenu().setParentMenu(MainMenu.getMenu());
        MainMenu.getMenu().setParentMenu(MenuManage.getLatestMenu());
        System.out.println(Shows.getShowAccount().apply(account));
    }

    @Override
    public void show() {
        System.out.println("You're in LogInMenu");
    }

    @Override
    public void help() {
        super.help();
        System.out.println(
                "login [username] [password]: to login to your account" + System.lineSeparator() +
                        "----------------------------------------------"
        );
    }
}
