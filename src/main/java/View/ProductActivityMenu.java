package View;

import Controller.ProductController;
import View.LoginAndSigup.LogInMenu;
import Exception.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductActivityMenu extends Menu {

    private static ProductActivityMenu menu;
    private static ProductController productController = ProductController.getInstance();

    private ProductActivityMenu(String name) {
        super(name);
    }

    public static ProductActivityMenu getInstance(String name) {
        if (menu == null) {
            menu = new ProductActivityMenu(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in DigestProductMenu."));
    }

    public void addToCart() {
        System.out.println(
                "Enter seller id in this pattern: " + System.lineSeparator() +
                        "select seller :[sellerId]");

        Matcher matcher = Pattern.compile("select seller :(\\w+)").matcher(scanner.nextLine().trim());
        if (!matcher.find()) {
            System.out.println("Incorrect format");
            return;
        }
        try {
            productController.addToCart(matcher.group(1));
            System.out.println("Successful");

        } catch (AccountHasNotLogin e) {
            System.out.println(e.getMessage());
            MenuManage.setLatestMenu(LogInMenu.getMenu());
        } catch (ProductIsOutOfStockException | ProductDoesNotExistException | SellerDoesNotSellOfThisProduct e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println("You're in DigestProductMenu");
    }
}
