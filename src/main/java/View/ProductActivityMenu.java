package View;

import Bank.BankException;
import Bank.BankService;
import Bank.BankServiceImpl;
import Controller.ControllerSection;
import Controller.ProductController;
import Model.Account.Account;
import Model.Account.Customer;
import View.LoginAndSigup.LogInMenu;
import Exception.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductActivityMenu extends Menu {

    private static ProductActivityMenu menu;
    private static ProductController productController = ProductController.getInstance();

    private static BankService bankService = new BankServiceImpl();
    private static ControllerSection controllerSection = ControllerSection.getInstance();


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

    public void checkout() {
        try {
            Customer customer = (Customer) controllerSection.getAccount();

            int receiptId = bankService.createReceipt(
                    customer.getToken(),
                    "withdraw",
                    (int) customer.getCart().getTotalPrice(),
                    customer.getBankAccountId(),
                    -1,
                    "checkout"
            );
            bankService.pay(receiptId);
            System.out.println("Successful");
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println("You're in ProductActivtyMenu");
    }
    @Override
    public void help() {
        super.help();
        System.out.println(
                "addToCart : To add a good" + System.lineSeparator() +
                        "checkout : To pay cart and checkout" + System.lineSeparator() +
                        "----------------------------------------------"
        );
    }
}
