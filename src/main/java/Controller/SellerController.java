package Controller;

import Model.*;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Seller;
import Model.Field.Field;
import Model.Interface.ForPend;
import Exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SellerController extends AccountController {
    private static SellerController sellerController = new SellerController();

    public static SellerController getInstance() {
        return sellerController;
    }

    private FieldList createFieldList( List<String> fieldName, List<String> values) {
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < fieldName.size(); i++) {
            fields.add(new Field(fieldName.get(i), values.get(i)));
        }
        return new FieldList(fields);
    }

    public Info viewCompanyInformation() {
        return ((Seller) controllerUnit.getAccount()).getCompanyInfo();
    }

    public double viewBalance() {
        return ((Seller) controllerUnit.getAccount()).getBalance();
    }

    public List<Category> showCategories() {
        return Category.getList();
    }

    public List<Off> viewAllOffs() {
        return Off.getList();
    }

    public List<LogHistory> viewSalesHistory() throws LogHistoryDoesNotExistException {
        List<LogHistory> list = new ArrayList<>();
        for (Long aLong : ((Seller) controllerUnit.getAccount()).getLogHistoryList()) {
            LogHistory logHistoryById = LogHistory.getLogHistoryById(aLong);
            list.add(logHistoryById);
        }
        return list;
    }

    public List<Product> showProducts() throws ProductDoesNotExistException {
        List<Product> list = new ArrayList<>();
        for (Long aLong : ((Seller) controllerUnit.getAccount()).getProductList()) {
            Product productById = Product.getProductById(aLong);
            list.add(productById);
        }
        return list;
    }

    public Info viewProduct(String productIdString) throws ProductDoesNotExistException, NumberFormatException {
        long productId = Long.parseLong(productIdString);
        Product product = Product.getProductById(productId);
        return product.getProductInfo();
    }

    public List<Customer> viewBuyers(String productIdString) throws ProductDoesNotExistException, NumberFormatException, AccountDoesNotExistException {
        long productId = Long.parseLong(productIdString);
        Product product = Product.getProductById(productId);
        List<Customer> list = new ArrayList<>();
        for (Long aLong : product.getBuyerList()) {
            Account accountById = Account.getAccountById(aLong);
            list.add((Customer) accountById);
        }
        return list;
    }

    public Product createTheBaseOfProduct(String productName, String strCategoryId, String strAuctionId, String strNumberOfThis, String priceString) throws OffDoesNotExistException, CategoryDoesNotExistException {
        long numberOfThis = Long.parseLong(strNumberOfThis);
        long categoryId = Long.parseLong(strCategoryId);
        long auctionId = Long.parseLong(strAuctionId);
        double price = Double.parseDouble(priceString);
        Category category = categoryId == 0 ? null : Category.getCategoryById(categoryId);
        Off auction = auctionId == 0 ? null : Off.getAuctionById(auctionId);
        ProductSeller productOfSeller = new ProductSeller(controllerUnit.getAccount().getId(), numberOfThis, price);
        return new Product(productName, category, auction, productOfSeller);
    }

    public void saveProductInfo( Product product, List<String> fieldName, List<String> values) {
        FieldList fieldList = createFieldList(fieldName, values);
        product.setProductInfo(new Info("ProductInfo", fieldList, LocalDate.now()));
    }

    public void saveCategoryInfo( Product product, List<String> fieldName, List<String> values) {
        FieldList fieldList = createFieldList(fieldName, values);
        product.setCategoryInfo(new Info("CategoryInfo", fieldList, LocalDate.now()));
    }


    public void sendRequest(ForPend forPend, String information, String type) {
        ((Seller) controllerUnit.getAccount()).addToPendList(forPend);
        Request request = new Request(controllerUnit.getAccount().getId(), information, type, forPend);
        Request.addRequest(request);
    }

    public Off addOff(String auctionName, String strStart, String strEnd, String strPercent, String strMaxAmount) throws NumberFormatException, DateTimeParseException, InvalidInputByUserException {
        LocalDate start = LocalDate.parse(strStart, formatter);
        LocalDate end = LocalDate.parse(strEnd, formatter);
        if (!end.isAfter(start)) {
            throw new InvalidInputByUserException("End must be after start time. ok?");
        }
        double percent = Double.parseDouble(strPercent);
        double maxAmount = Double.parseDouble(strMaxAmount);
        Discount discount = new Discount(percent, maxAmount);
        return new Off(auctionName, start, end, discount);
    }

    public void addProductsToAuction( Off off,  List<String> productIdsString) throws ProductDoesNotExistException, ProductCantBeInMoreThanOneAuction, NumberFormatException {
        List<Long> productIds = productIdsString.stream().map(Long::parseLong).collect(Collectors.toList());
        for (long aLong : productIdsString.stream().map(Long::parseLong).collect(Collectors.toList())) {
            Product product = Product.getProductById(aLong);
            if (product.getOff() != null) {
                throw new ProductCantBeInMoreThanOneAuction("Product with the id:" + aLong + " have auction. You can't add it");
            }
        }
        off.setProductList(productIds);
    }

    public void removeProduct(String productIdString, String information) throws ProductDoesNotExistException, NumberFormatException {
        long productId = Long.parseLong(productIdString);
        Product product = Product.getProductById(productId);
        this.sendRequest(product, information, "remove");
    }

    public Off viewOff(String offIdString) throws OffDoesNotExistException, NumberFormatException {
        long offId = Long.parseLong(offIdString);
        return Off.getAuctionById(offId);
    }

    public void editAuction(String strId, String fieldName, String newInfo, String information) throws OffDoesNotExistException, FieldDoesNotExistException, NumberFormatException, InvalidInputByUserException {
        long id = Long.parseLong(strId);
        Off.checkExistOfAuctionById(id, ((Seller) controllerUnit.getAccount()).getOffList(), controllerUnit.getAccount());
        try {
            Off off = (Off) Off.getAuctionById(id).clone();
            off.editField(fieldName, newInfo);
            if (!off.getEnd().isAfter(off.getStart())) {
                throw new InvalidInputByUserException("End must be after start time. ok?");
            }
            this.sendRequest(off, information, "edit");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void editProduct(String strId, String fieldName, String newInfo, String information) throws OffDoesNotExistException, FieldDoesNotExistException, CategoryDoesNotExistException, ProductDoesNotExistException, NumberFormatException {
        long id = Long.parseLong(strId);
        Product.checkExistOfProductById(id, ((Seller) controllerUnit.getAccount()).getProductList(), controllerUnit.getAccount());
        Product product = (Product) Product.getProductById(id).clone();
        product.editField(fieldName, newInfo);
        this.sendRequest(product, information, "edit");
    }

    public void addProductsToOff(Off auction,List<String> productIdsString) throws ProductDoesNotExistException, ProductCantBeInMoreThanOneAuction, NumberFormatException {
        List<Long> productIds = productIdsString.stream().map(Long::parseLong).collect(Collectors.toList());
        for (long aLong : productIdsString.stream().map(Long::parseLong).collect(Collectors.toList())) {
            Product product = Product.getProductById(aLong);
            if (product.getOff() != null) {
                throw new ProductCantBeInMoreThanOneAuction("Product with the id:" + aLong + " have auction. You can't add it");
            }
        }
        auction.setProductList(productIds);
    }
}
