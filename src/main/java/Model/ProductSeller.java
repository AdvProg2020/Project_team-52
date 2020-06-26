package Model;

import Model.Account.Seller;

public class ProductSeller {
    private int sellerId;

    private long number;


    private Product product;


    private Seller seller;

    private long price;

    private Long priceInOff;

    private int remainingItems;


    public ProductSeller() {

    }

    public ProductSeller(int sellerId, Product product, int remainingItems) {
        this.sellerId = sellerId;
        this.product = product;
        this.remainingItems = remainingItems;
    }

    public ProductSeller(int sellerId, Product product, int remainingItems, long price, long priceInOff) {
        this.sellerId = sellerId;
        this.product = product;
        this.remainingItems = remainingItems;
        this.price = price;
        this.priceInOff = priceInOff;
    }

    public int getSellerId() {
        return sellerId;
    }


    public Product getProduct() {
        return product;
    }

    public Seller getSeller() {
        return seller;
    }

    public long getPrice() {
        return price;
    }

    public int getRemainingItems() {
        return remainingItems;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void sell(int amount) throws NotEnoughProductsException {
        if (remainingItems < amount) {
            throw new NotEnoughProductsException("Not enough products", this);
        }
        remainingItems -= amount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getPriceInOff() {
        if (priceInOff == null) {
            return price;
        }
        return priceInOff;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setRemainingItems(int remainingItems) {
        this.remainingItems = remainingItems;
    }



    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public ProductSeller clone() {
        return new ProductSeller(this.sellerId, this.product.clone(), this.remainingItems, this.price, this.priceInOff);

    }
}
