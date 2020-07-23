package Model;

import Model.Account.Account;
import Model.Data.Data;
import Model.DataBase.DataBase;
import Model.Field.Field;
import Model.Interface.AddingNew;
import Model.Interface.Filterable;
import Model.Interface.ForPend;
import Model.Interface.Packable;
import Exception.*;

import java.util.*;

public class Product implements Packable<Product>, Filterable, ForPend {
    private int productId;

    private String name;

    private String productName;

    private  Off off;
    private List<Long> scoreList;
    private static List<Product> list;
    private List<Long> buyerList = new ArrayList<>();




    private Double averageScore;

    private int amountBought;

    private Info productInfo;

    private Info categoryInfo;

    private String requestCondition;


    private Category category;

    private List<ProductSeller> sellerList;

    private List<Long> commentList;

    private long accountId;
    private String information;
    private List<ProductSeller> sellersOfProduct;
    private String stateForPend;


    public Product() {
        sellerList = new ArrayList<>();
    }

    public Product(String name, String productName, Info productInfo, Info categoryInfo) {
        this.name = name;

    }


    public Product(String name, Category category, Off off, ProductSeller productOfSeller) {
        this.productName = name;
        this.category = category;
        this.off = off;
        this.sellersOfProduct = new ArrayList<>();
        sellersOfProduct.add(productOfSeller);
    }

    public Product(long accountId, String information ,String requestCondition) {
        this.accountId = accountId;
        this.information = information;
        this.requestCondition=requestCondition;
    }

    public Product(String name, String productName) {
            this.name = name;
            this.productName = productName;
            sellerList = new ArrayList<>();
            commentList = new ArrayList<Long>();
        }

    public static List<Product> getList() {
        return list;
    }

    public static void setList(List<Product> list) {
        Product.list = list;
    }

    public int getProductId() {
            return productId;
        }

        public String getName() {
            return name;
        }

        public long getMinimumPrice() {
            long min = Integer.MAX_VALUE;
            for (ProductSeller productSeller : sellerList) {
                if (min > productSeller.getPriceInOff())
                    min = productSeller.getPriceInOff();
            }
            return min;
        }

        public String getProductName() {
            return productName;
        }


    public Info getProductInfo() {
        return productInfo;
    }

    public Info getCategoryInfo() {
        return categoryInfo;
    }

    public List<ProductSeller> getSellerList() {
            return sellerList;
        }

        public Category getCategory() {
            return category;
        }



        public Double getAverageScore() {
            return averageScore;
        }

        public int getAmountBought() {
            return amountBought;
        }

    @Override
    public String getField( String fieldName) throws FieldDoesNotExistException {

        switch (fieldName) {
            case "productName":
                return productName;
            case "categoryName":
                return category.getName();
            case "OffName":
                return off.getName();
            default:
                Field field;
                if (productInfo.getList().isFieldWithThisName(fieldName)) {
                    field = productInfo.getList().getFieldByName(fieldName);
                } else
                    field = categoryInfo.getList().getFieldByName(fieldName);

                return field.getString();
        }
    }

    public static Product getProductById(long id) throws ProductDoesNotExistException {
        return list.stream()
                .filter(product -> id == product.getId())
                .findFirst()
                .orElseThrow(() -> new ProductDoesNotExistException(
                        "Product with the id:" + id + " does not exist in list of all products."
                ));
    }

        public List<Long> getCommentList() {
            return commentList;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setCategory(Category category) {
            this.category = category;
        }


        public void setAverageRate(double averageRate) {
            this.averageScore = averageRate;
        }


        public void addSeller(ProductSeller productSeller) {
            this.sellerList.add(productSeller);
        }


        public void setSellerList(List<ProductSeller> sellerList) {
            this.sellerList = sellerList;
        }



    public void addComment(long commentId) {
        commentList.add(commentId);
        DataBase.save(this);
    }


    public static void addProduct(Product product, boolean aNew) {
        product.setProductId(Math.toIntExact(AddingNew.getRegisteringId().apply(getList())));
        Off Off = product.getOff();
        list.add(product);
        DataBase.save(product, true);
    }

    public static void removeProduct(Product product) {
        list.removeIf(pro -> product.getId() == pro.getId());
        DataBase.remove(product);
    }



        @Override
        public Product clone() {
            Product product = new Product(this.name, this.productName);
            product.setCategory(category);
            product.setProductId(productId);
            product.setSellerList(sellerList);
            return product;
        }

    public static void checkExistOfProductById(long id,  List<Long> longList, Packable<?> packable) throws ProductDoesNotExistException {
        if (longList.stream().noneMatch(Id -> id == Id)) {
            throw new ProductDoesNotExistException(
                    "In the " + packable.getClass().getSimpleName() + " with id:" + packable.getId() + " the Product with id:" + id + " does not exist."
            );
        }
    }


    @Override
    public Data<Product> pack() {
        return null;
    }

    @Override
    public Product dpkg(Data<Product> data) {
        return null;
    }

    @Override
    public long getId() {
        return 0;
    }

    public Collection getScoreList() {
        return sellerList;
    }

    public void setAverageScore(int newScore) {
        this.averageScore=averageScore;
    }

    public void addScore(long scoreId) {
        scoreList.add(scoreId);
            DataBase.save(this);
        }


    public void setProductInfo(Info productInfo) {
        this.productInfo=productInfo;
    }

    public Off getOff() {
        return off;
    }




    public ProductSeller getProductSellerById(long sellerId) throws  SellerDoesNotSellOfThisProduct{
        return sellersOfProduct.stream()
                .filter(productSeller -> sellerId == productSeller.getSellerId())
                .findFirst()
                .orElseThrow(() -> new SellerDoesNotSellOfThisProduct(
                        "Seller with the id:" + sellerId + " does not sell the product with id:" + productId + " ."
                ));
    }

    public void setOff(Off off) {
        this.off=off;
    }

    public ProductSeller getProductOfSellerById(long sellerId)  throws SellerDoesNotSellOfThisProduct{
        return sellersOfProduct.stream()
                .filter(productSeller -> sellerId == productSeller.getSellerId())
                .findFirst()
                .orElseThrow(() -> new SellerDoesNotSellOfThisProduct(
                        "Seller with the id:" + sellerId + " does not sell the product with id:" + productId + " ."
                ));

    }

    public void editField(String fieldName, String value) throws FieldDoesNotExistException, OffDoesNotExistException, NumberFormatException, CategoryDoesNotExistException, OffDoesNotExistException {

        switch (fieldName) {
            case "productName":
                setProductName(value);
                break;
            case "category":
                setCategory(Category.getCategoryById(Long.parseLong(value)));
                break;
            case "Auction":
                setOff(Off.getAuctionById(Long.parseLong(value)));
                break;
            default:
                Field field;
                if (productInfo.getList().isFieldWithThisName(fieldName)) {
                    field = productInfo.getList().getFieldByName(fieldName);
                } else
                    field = categoryInfo.getList().getFieldByName(fieldName);

                field.setString(value);
        }
    }


    public void setCategoryInfo(Info categoryInfo) {
        this.categoryInfo = categoryInfo;
    }


    @Override
    public void setStateForPend(String stateForPend) {
        this.stateForPend=stateForPend;
    }

    @Override
    public String getStateForPend() {
        return null;
    }

    public List<Long> getBuyerList() {
        return Collections.unmodifiableList(buyerList);
    }
}
