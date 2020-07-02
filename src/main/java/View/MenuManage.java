package View;

import View.AccountMenu.CustomerMenu;
import View.AccountMenu.ManagerMenu;
import View.AccountMenu.SellerMenu;
import View.CustomerMenu.ViewCartForCustomer;
import View.CustomerMenu.ViewOrderForCustomer;
import View.LoginAndSigup.LogInMenu;
import View.LoginAndSigup.SignUpMenu;
import View.LoginAndSigup.UserMenu;
import View.ManagerMenu.ManageCategoryForManager;
import View.ManagerMenu.ManageProductForManager;
import View.ManagerMenu.ManageRequestForManager;
import View.SellerMenu.ManageProductForSeller;
import View.SellerMenu.ViewOffSeller;

import java.awt.*;
import java.util.Scanner;

public class MenuManage {

    private static Scanner scanner=new Scanner(System.in);
    private static Menu latestMenu=UserAreaMenu.getInstance("userAreaMenu");
    private static ModelUnit modelUnit = ModelUnit.getInstance();


    private static void initMenus() {

        CustomerMenu.getMenu().addSubMenu(ManageInfoMenu.getMenu())
                .addSubMenu(ViewCartForCustomer.getMenu())
                .addSubMenu(ViewOrderForCustomer.getMenu())

        MainMenu.getMenu().addSubMenu(ProductsMenu.getMenu())
                .addSubMenu(OffMenu.getMenu());
        ManagerMenu.getMenu()
                .addSubMenu(ManageCategoryForManager.getMenu())
                .addSubMenu(ManageInfoMenu.getMenu())
                .addSubMenu(ManageProductForManager.getMenu())
                .addSubMenu(ManageRequestForManager.getMenu())
                .addSubMenu(ProductMenu.getMenu());
        SellerMenu.getMenu()
                .addSubMenu(ManageInfoMenu.getMenu())
                .addSubMenu(ManageProductForSeller.getMenu())
                .addSubMenu(ViewOffSeller.getMenu());
        SignUpMenu.getMenu()
                .addSubMenu(LogInMenu.getMenu());
        UserMenu.getMenu()
        .addSubMenu(LogInMenu.getMenu())
                .addSubMenu(SignUpMenu.getMenu());
    }

    public static void main(String[] args) {
        Input input=new Input();
        Output output=Output.getInstance();
        input.start(output);
    }

    public static Menu getLatestMenu() {
        return latestMenu;
    }


    public static void setLatestMenu(Menu latestMenu) {
        MenuManage.latestMenu = latestMenu;
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
