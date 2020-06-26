package Controller;

import Model.*;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Guest;
import Model.Account.Seller;
import Model.Field.Field;

import java.util.Arrays;
import java.util.List;

public class ProductController {
    private static ControllerSection controllerSection = ControllerSection.getInstance();

    private static ProductController productController = new ProductController();

    public static ProductController getInstance() {
        return productController;
    }

    public Product digest() {
        return controllerSection.getProduct();
    }

    public List<Seller> ListOfSellersOfChosenProduct() throws AccountDoesNotExistException {
        List<Seller> list = new ArrayList<>();
        for (ProductSeller productOfSeller : controllerSection.getProduct().getSellersOfProduct()) {
            long sellerId = productOfSeller.getSellerId();
            Account accountById = Account.getAccountById(sellerId);
            list.add((Seller) accountById);
        }
        return list;
    }

    public List<Comment> viewComments() throws CommentDoesNotExistException {
        List<Comment> list = new ArrayList<>();
        for (Long aLong : controllerSection.getProduct().getCommentList()) {
            Comment commentById = Comment.getCommentById(aLong);
            list.add(commentById);
        }
        return list;
    }

    public Product getProductById(String productIdString) throws NumberFormatException, ProductDoesNotExistException {
        long id = Long.parseLong(productIdString);
        return Product.getProductById(id);
    }


    public void addToCart(String sellerIdString) throws AccountHasNotLogin, ProductIsOutOfStockException, ProductDoesNotExistException, SellerDoesNotSellOfThisProduct {
        long sellerId = Long.parseLong(sellerIdString);

        if (controllerSection.getAccount() instanceof Guest) {
            throw new AccountHasNotLogin("Guest can't add to cart. Go to login menu ...");
        }

        Customer customer = (Customer) controllerSection.getAccount();

        ProductSeller productOfSellerById = Product.getProductById(controllerSection.getProduct().getId()).getProductSellerById(sellerId);
        if (productOfSellerById.getNumber() <= 0) {
            throw new ProductIsOutOfStockException("Product is out of stock.");
        }
        productOfSellerById.setNumber(productOfSellerById.getNumber() - 1);
        customer.getCart().addProductToCart(sellerId, controllerSection.getProduct().getId());
    }

    public void addComment(String title, String content) throws ProductDoesNotExistException, CannotRateException {
        Account account = controllerSection.getAccount();
        Product product = controllerSection.getProduct();
        List<Field> fields = Arrays.asList(new Field("Title", title), new Field("Content", content));
        FieldList fieldList = new FieldList(fields);
        CustomerController.getInstance().checkIfProductBoughtToRate(product.getId());
        Comment comment = new Comment("good", account.getId(), product.getId(), fieldList);
        Comment.addComment(comment);
    }

}
