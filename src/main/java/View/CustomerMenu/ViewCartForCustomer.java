package View.CustomerMenu;

import Controller.CustomerController;
import View.Interface.Shows;
import View.Menu;
import Exception.*;

import java.util.List;
import java.util.Optional;

public class ViewCartForCustomer extends Menu {
    private static ViewCartForCustomer menu;

    private static CustomerController customerController = CustomerController.getInstance();

    public ViewCartForCustomer(String name) {
        super(name);
    }

    public static ViewCartForCustomer getInstance(String name) {
        if (menu == null) {
            menu = new ViewCartForCustomer(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in ViewCartByBuyerMenu."));
    }

    public void showProducts() {
        try {
            customerController.showProducts().forEach(product -> {
                System.out.println(Shows.getShowProduct().apply(product));
            });
        } catch (ProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewProduct(List<String> inputs) {
        String id = inputs.get(0);
        try {
            System.out.println(
                    Shows.getShowProduct().apply(customerController.viewProductInCart(id))
            );
        } catch (ProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void increase(List<String> inputs) {
        String idProduct = inputs.get(0);
        System.out.println("Please enter seller id of this product: ");
        String idSeller = scanner.nextLine();

        try {
            customerController.increase(idProduct, idSeller);
        } catch (ProductDoesNotExistException | SellerDoesNotSellOfThisProduct | ProductIsOutOfStockException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Ok.");
    }

    public void decrease(List<String> inputs) {
        String idProduct = inputs.get(0);
        System.out.println("Please enter seller id of this product: ");
        String idSeller = scanner.nextLine();
        try {
            customerController.decrease(idProduct, idSeller);
        } catch (ProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Ok.");
    }

    public void showTotalPrice() {
        try {
            System.out.println("TotalPrice: " +customerController.showTotalPrice());
        } catch (ProductDoesNotExistException | SellerDoesNotSellOfThisProduct e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void show() {
        System.out.println("You're in View Cart For Customer Menu.");
    }
    @Override
    public void help() {
        super.help();
        System.out.println(
                "show products : To show product" + System.lineSeparator() +
                        "viewProduct [productId] : To view cart" + System.lineSeparator() +
                        "increase [productId] : To increase number of that product" + System.lineSeparator() +
                        "decrease [productId] : To decrease number of that product" + System.lineSeparator() +
                        "show total price : To show total price" + System.lineSeparator() +
                        "purchase : To purchase" + System.lineSeparator() +
                        "----------------------------------------------"
        );
    }
}
