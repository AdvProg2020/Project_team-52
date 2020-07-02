package View.LoginAndSigup;

import Controller.ControllerSection;
import Model.Account.*;
import View.AccountMenu.CustomerMenu;
import View.AccountMenu.ManagerMenu;
import View.AccountMenu.SellerMenu;
import View.MainMenu;
import View.Menu;
import View.MenuManage;

import java.util.List;
import java.util.Optional;

public class UserMenu extends Menu {
    private static UserMenu menu;

    private UserMenu(String name) {
        super(name);
    }

    public static UserMenu getInstance(String name) {
        if (menu == null) {
            menu = new UserMenu(name);
        }
        return menu;
    }

    public static UserMenu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in UserAreaMenu."));
    }

    public void openLoginMenu() {
        MenuManage.setLatestMenu(LogInMenu.getMenu());
    }

    public void openSignUpMenu() {
        MenuManage.setLatestMenu(SignUpMenu.getMenu());
    }

    public void EnterAsGuest() {
        ControllerSection.getInstance().setAccount(Guest.autoCreateGuest());
        MenuManage.setLatestMenu(MainMenu.getMenu());
    }

    @Override
    public void show() {
        System.out.println(
                "You're in UserAreaMenu" + System.lineSeparator() +
                        "-------------------SubMenus-------------------" + System.lineSeparator() +
                        "1.LoginMenu" + System.lineSeparator() +
                        "2.SignUpMenu" + System.lineSeparator() +
                        "----------------------------------------------"
        );
    }
}
