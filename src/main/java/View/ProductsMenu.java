package View;

import Controller.ControllerSection;
import Controller.ProductsController;
import Model.Product;
import Exception.*;
import View.Interface.Shows;

import java.util.List;
import java.util.Optional;

public class ProductsMenu extends Menu {
    private static ProductsMenu menu;
    private static ProductsController productsController = ProductsController.getInstance();

    private ProductsMenu(String name) {
        super(name);
    }

    public static ProductsMenu getInstance(String name) {
        if (menu == null) {
            menu = new ProductsMenu(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in ProductsMenu."));
    }

    public void viewCategories() {
        System.out.println("Categories: ");
        productsController.viewCategories().forEach(category ->
                System.out.println(Shows.getShowCategory().apply(category))
        );
    }


    public void showProducts() {
        System.out.println(productsController.showProducts());
    }

    public void showProduct( List<String> inputs) {
        String id = inputs.get(0);
        try {

            Product product = productsController.showProduct(id);

            System.out.println(
                    Shows.getShowProduct().apply(product)
            );

            ControllerSection.getInstance().setProduct(product);
            MenuManage.setLatestMenu(ProductMenu.getMenu());

        } catch (ProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println("you are in products menu" + System.lineSeparator() +
                "-------------------SubMenus-------------------" + System.lineSeparator() +
                "1.FilteringMenu" + System.lineSeparator() +
                "2.SortingMenu" + System.lineSeparator() +
                "3.productMenu" + System.lineSeparator() +
                "----------------------------------------------");
    }
}
