package View;

import Controller.OffController;
import Model.Off;
import Model.Product;
import Exception.*;
import View.Interface.Shows;

import java.util.List;
import java.util.Optional;

public class OffMenu extends Menu {
    private static OffController offController=OffController.getInstance();
    private static OffMenu menu;

    public OffMenu(String name) {
        super(name);
    }

    public static OffMenu getInstance(String name) {
        if (menu == null) {
            menu = new OffMenu(name);
        }
        return menu;
    }


    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in auctionMenu."));
    }


    public void offs() {
        List<Off> auctionList = offController.offs();
        auctionList.forEach(auction -> {
            System.out.println(Shows.getShowOff().apply(auction));
            try {
                List<Product> productList = offController.getProductOfAuction(auction.getId());
                productList.forEach(product -> Shows.getShowProduct().apply(product));
            } catch (OffDoesNotExistException | ProductDoesNotExistException e) {
                System.out.println(e.getMessage());
            }

        });
    }
    public void showProduct( List<String> inputs) {
        String id =inputs.get(0);
        try {
            Product product = offController.showProduct(id);
            System.out.println(Shows.getShowProduct().apply(product));
        } catch (ProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println("you are in auction menu");
    }

    @Override
    public void help() {
        super.help();
        System.out.println("offs : To show all the Auctions." + System.lineSeparator() +
                "showProduct [productId] : To product by the id." + System.lineSeparator() +
                "----------------------------------------------"
        );
    }

}
