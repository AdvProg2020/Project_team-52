package View;

import Controller.ControllerSection;
import Model.Product;
import Controller.ProductController;
import Exception.*;
import View.Interface.Shows;

import java.util.List;
import java.util.Optional;

public class ProductMenu extends Menu {
    private static ProductMenu menu;

    private static ProductController productController = ProductController.getInstance();

    public ProductMenu(String name) {
        super(name);
    }

    public static ProductMenu getInstance(String name) {
        if (menu == null) {
            menu = new ProductMenu(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in ProductMenu."));
    }

    public void digest() {
        Product currentProduct = productController.digest();
        System.out.println(
                Shows.getShowProduct().apply(currentProduct)
        );
        MenuManage.setLatestMenu(ProductActivityMenu.getMenu());
    }


    public void compare(List<String> inputs) {
        String id = inputs.get(0);
        Product currentProduct = productController.digest();
        try {
            Product productToCompare = productController.getProductById(id);

        } catch (ProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void Comments() {
        try {
            System.out.println("Comments:");
            productController.viewComments().forEach(comment ->
                    System.out.println(Shows.getShowComment().apply(comment))
            );
        } catch (CommentDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("AverageScore:" + ControllerSection.getInstance().getProduct().getAverageScore());
        MenuManage.setLatestMenu(CommentMenu.getMenu());
    }

    @Override
    public void show() {
        System.out.println("You're in ProductMenu");
    }
    @Override
    public void help() {
        super.help();
        System.out.println("digest : To show product information" + System.lineSeparator() +
                "attributes : To full information" + System.lineSeparator() +
                "compare [anotherId] : To compare" + System.lineSeparator() +
                "Comments : To comment" + System.lineSeparator() +
                "----------------------------------------------"
        );
    }
}
