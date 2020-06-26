package Model;

import Model.Account.Account;
import Model.Data.Data;
import Model.DataBase.DataBase;
import Model.Field.Field;
import Model.Interface.AddingNew;
import Model.Interface.Filterable;
import Model.Interface.Packable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Product  implements Packable<Product> , Filterable, Cloneable {
    private int productId;

    private String name;

    private String productName;

    private  Off off;
    private List<Score> scoreList;


    private Double averageScore;

    private int amountBought;

    private Info productInfo;

    private Info categoryInfo;


    private Category category;

    private List<ProductSeller> sellerList;

    private List<Comment> commentList;





    public Product() {
        sellerList = new ArrayList<>();
    }

    public Product(String name, String productName, Info productInfo, Info categoryInfo) {
        this.name = name;

    }



    public Product(String name, String productName) {
            this.name = name;
            this.productName = productName;
            sellerList = new ArrayList<>();
            commentList = new ArrayList<>();
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

        public List<Comment> getCommentList() {
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



        public boolean hasSeller(Account seller) {
             if sellerList.equals(seller)
            {
                return getSellerList().equals(seller);


            }
        }

        public Request createRequest(RequestCondition requestCondition) {
        //todo
        Request Request = new Request(acountId,Information, , requestCondition);
            if (requestCondition == RequestCondition.ADD) {
                for (ProductSeller seller : sellerList) {
                    Request.addSeller(seller.createProductSellerRequest(requestCondition));
                }
            }
            return Request;
        }

        public void setSellerList(List<ProductSeller> sellerList) {
            this.sellerList = sellerList;
        }



    public void addComment(long commentId) {
        commentList.add(commentId);
        DataBase.save(this);
    }


    public static void addProduct( Product product) {
        product.setProductId(AddingNew.getRegisteringId().apply(getList()));
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

    public void addScore(long id) {


        scoreList.add(scoreId);
            DataBase.save(this);
        }


    public void setProductInfo(Info productInfo) {
        this.productInfo=productInfo;
    }

    public Off getOff() {
        return off;
    }
}
