package Controller;

import Model.*;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Seller;
import Model.DataBase.DataBase;
import Model.Field.Field;
import Exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerController extends AccountController {
    private static CustomerController customerController = new CustomerController();

    private PromotionCode promotionCodeEntered = null;

    public static CustomerController getInstance() {
        return customerController;
    }


    private void setDiscountCodeEntered(PromotionCode promotionCodeEntered) {
        this.promotionCodeEntered = promotionCodeEntered;
    }

    private void checkEnoughCredit() throws NotEnoughCreditException, ProductDoesNotExistException, SellerDoesNotSellOfThisProduct {
        double price = viewCart().getTotalPrice() - viewCart().getTotalAuctionDiscount();

        if (promotionCodeEntered != null) {
            price -= promotionCodeEntered.getDiscountCodeDiscount(viewCart().getTotalPrice());
        }

        if (((Customer) controllerUnit.getAccount()).getCredit() < price) {
            throw new NotEnoughCreditException("Not Enough Credit.");
        }
    }

    private void saveFieldToFieldList(String name, String value) throws FieldDoesNotExistException {
        FieldList fieldList = (controllerUnit.getAccount()).getPersonalInfo().getList();
        if (!fieldList.isFieldWithThisName(name)) {
            fieldList.addFiled(new Field(name, value));
        } else {
            Field field = fieldList.getFieldByName(name);
            ( field).setString(value);
        }
        DataBase.save(controllerUnit.getAccount());
    }


    private List<ProductLog> payment() throws AccountDoesNotExistException, ProductDoesNotExistException, SellerDoesNotSellOfThisProduct {

        ((Customer) controllerUnit.getAccount()).setCredit(((Customer) controllerUnit.getAccount()).getCredit() - showTotalPrice());


        List<ProductLog> productLogs = new ArrayList<>();
        List<Product> listOfProduct = this.showProducts();
        List<Long> listOfSellers = viewCart().getProductSellers();
        for (int i = 0; i < showProducts().size(); i++) {
            Seller seller = (Seller) Account.getAccountById(listOfSellers.get(i));
            Product product = listOfProduct.get(i);

            double productPrice = product.getProductSellerById(listOfSellers.get(i)).getPrice();
            double productAuctionAmount = product.getOff() == null ? 0 : product.getOff().getAuctionDiscount(productPrice);
            double productFinalPrice = productPrice - productAuctionAmount;

            productLogs.add(new ProductLog(product.getId(), product.getName(), productPrice, productAuctionAmount, productFinalPrice));

            seller.setBalance(seller.getBalance() + productFinalPrice);

        }
        return productLogs;
    }

    private void checkCartForProductId(long productId) throws ProductDoesNotExistException {
        if (!((Customer) controllerUnit.getAccount()).getCart().isThereThisProductInCart(productId)) {
            throw new ProductDoesNotExistException("product with the id:" + productId + " not exist in this cart.");
        }
    }

    public void checkIfProductBoughtToRate(long productId) throws CannotRateException, ProductDoesNotExistException, NumberFormatException {
        Product product = Product.getProductById(productId);
        if (!product.getCustomer().contains(controllerUnit.getAccount().getId())) {
            throw new CannotRateException("Cannot Rate. You must buy it first. ok?");
        }
    }

    public double showTotalPrice() throws ProductDoesNotExistException, SellerDoesNotSellOfThisProduct {
        double price = viewCart().getTotalPrice() - viewCart().getTotalAuctionDiscount();
        if (promotionCodeEntered != null) {
            price -= promotionCodeEntered.getDiscountCodeDiscount(price);
        }
        return price;
    }

    public Cart viewCart() {
        return ((Customer) controllerUnit.getAccount()).getCart();
    }

    public List<Product> showProducts() throws ProductDoesNotExistException {
        List<Product> list = new ArrayList<>();
        for (Long aLong : ((Customer) controllerUnit.getAccount()).getCart().getProductList()) {
            Product productById = Product.getProductById(aLong);
            list.add(productById);
        }
        return list;
    }

    public double viewBalance() {
        return ((Customer) controllerUnit.getAccount()).getCredit();
    }

    public List<PromotionCode> viewDiscountCodes() throws DiscountCodeExpiredException {
        List<PromotionCode> list = new ArrayList<>();
        for (Long aLong : ((Customer) controllerUnit.getAccount()).getPromotionCodeList()) {
            PromotionCode discountCodeById = PromotionCode.getDiscountCodeById(aLong);
            list.add(discountCodeById);
        }
        return list;
    }

    public List<LogHistory> viewOrders() throws LogHistoryDoesNotExistException {
        List<LogHistory> list = new ArrayList<>();
        for (Long aLong : ((Customer) controllerUnit.getAccount()).getLogHistoryList()) {
            LogHistory logHistoryById = LogHistory.getLogHistoryById(aLong);
            list.add(logHistoryById);
        }
        return list;
    }

    public Product viewProductInCart(String productIdString) throws ProductDoesNotExistException, NumberFormatException {
        long productId = Long.parseLong(productIdString);
        Product.checkExistOfProductById(productId, viewCart().getProductList(), viewCart());
        return Product.getProductById(productId);
    }

    public void increase(String productIdString, String sellerIdString) throws NumberFormatException, ProductDoesNotExistException, ProductIsOutOfStockException, SellerDoesNotSellOfThisProduct {
        long productId = Long.parseLong(productIdString);
        long sellerId = Long.parseLong(sellerIdString);
        this.checkCartForProductId(productId);
        ProductSeller productOfSellerById = Product.getProductById(productId).getProductSellerById(sellerId);
        if (ProductSeller.getNumber() <= 0) {
            throw new ProductIsOutOfStockException("Product is out of stock. You can't increase number of the order whit id:" + productIdString + " .");
        } else {
            productOfSellerById.setNumber(productOfSellerById.getNumber() - 1);
            ((Customer) controllerUnit.getAccount()).addToCart(productId, sellerId);
        }
    }

    public void decrease(String productIdString, String sellerIdString) throws NumberFormatException, ProductDoesNotExistException {
        long productId = Long.parseLong(productIdString);
        long sellerId = Long.parseLong(sellerIdString);
        this.checkCartForProductId(productId);
        ((Customer) controllerUnit.getAccount()).removeFromCart(productId, sellerId);
    }

    public void receiveInformation( String postCode, String address) throws PostCodeInvalidException AddresInvalidException, FieldDoesNotExistException {
        if (!postCode.matches("\\d{10}")) {
            throw new PostCodeInvalidException("PostCode is Invalid.");
        }
        if (!address.matches("[a-z A-Z.0-9]+")) {
            throw new AddresInvalidException("Address is Invalid.");
        }
        this.saveFieldToFieldList("postCode", postCode);
        this.saveFieldToFieldList("address", address);
    }



    public void buyProductsOfCart() throws NotEnoughCreditException, AccountDoesNotExistException, ProductDoesNotExistException, SellerDoesNotSellOfThisProduct {
        this.checkEnoughCredit();
        Customer customer = ((Customer) controllerUnit.getAccount());
        List<ProductLog> productLogs = this.payment();
        LogHistory logHistory = new LogHistory(
                showTotalPrice(),
                promotionCodeEntered == null ? 0 : promotionCodeEntered.getDiscountCodeDiscount(viewCart().getTotalPrice() - viewCart().getTotalAuctionDiscount()),
                viewCart().getTotalAuctionDiscount(),
                new FieldList(Arrays.asList(new Field("customerName", customer.getUserName()), new Field("date", LocalDate.now().toString()))), // I don't know now. (for check)
                productLogs
        );
        LogHistory.addLog(logHistory);
        customer.addToLogHistoryList(logHistory.getId());
        for (Long sellerId : customer.getCart().getProductSellers()) {
            Seller seller = (Seller) Account.getAccountById(sellerId);
            seller.addToLogHistoryList(logHistory.getId());
        }
        Cart.removeCart(customer.getCart());
        customer.setCart(Cart.autoCreateCart());
        if (promotionCodeEntered != null) {
            customer.removeFromDiscountCodeList(promotionCodeEntered.getId());
            this.setDiscountCodeEntered(null);
        }
        DataBase.save(customer);
    }

    public LogHistory showOrder(String orderIdString) throws NumberFormatException, LogHistoryDoesNotExistException {
        long orderId = Long.parseLong(orderIdString);
        LogHistory.checkExistOfLogHistoryById(orderId, ((Customer) controllerUnit.getAccount()).getLogHistoryList(), controllerUnit.getAccount());
        return LogHistory.getLogHistoryById(orderId);
    }

    public void rate(String productIdString, String rateNumberString) throws ProductDoesNotExistException, CannotRateException, NumberFormatException {
        long productId = Long.parseLong(productIdString);
        int rateNumber = Integer.parseInt(rateNumberString);
        this.checkIfProductBoughtToRate(productId);
        Product product = Product.getProductById(productId);
        long numberOfBuyer = product.getScoreList().size();
        long lastScore = (long) product.getAverageScore() * numberOfBuyer;
        int newScore = (int) ((lastScore + rateNumber) / (numberOfBuyer + 1));
        Score score = new Score(controllerUnit.getAccount().getId(), productId, rateNumber);
        Score.addScore(score);
        product.addScore(score.getId());
        product.setAverageScore(newScore);
    }

    public void chargeAccount(String amountString) throws NumberFormatException {
        double amount = Double.parseDouble(amountString);
        Customer account = (Customer) controllerUnit.getAccount();
        account.setCredit(account.getCredit() + amount);
        DataBase.save(account);
    }



}
