package Controller;

import Model.Off;
import Model.Product;

import java.util.ArrayList;
import java.util.List;
import Exception.*;

public class OffController {
    private ControllerSection controllerSection = ControllerSection.getInstance();

    private static OffController offController = new OffController();


    public static OffController getInstance() {
        return offController;
    }

    public List<Off> offs() {
        return  Off.getList();
    }

    public Product showProduct(String productIdString) throws ProductDoesNotExistException, NumberFormatException {
        long productId = Long.parseLong(productIdString);
        Product.checkExistOfProductById(productId, controllerSection.getOff().getProductList(), controllerSection.getOff());
        Product product = Product.getProductById(productId);
        controllerSection.setProduct(product);
        return product;
    }

    public List<Product> getProductOfAuction(long auctionId) throws OffDoesNotExistException, ProductDoesNotExistException {
        Off auction = Off.getAuctionById(auctionId);
        List<Product> list = new ArrayList<>();
        for (Long aLong : auction.getProductList()) {
            Product productById = Product.getProductById(aLong);
            list.add(productById);
        }
        return list;
    }


}
