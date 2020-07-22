package Model;

import Model.Account.Seller;
import Exception.*;

public class ProductSeller {
    private long sellerId;

    private long number;


    private Product product;


    private Seller seller;

    private double price;

    private Long priceInOff;

    private int remainingItems;


    public ProductSeller() {

    }



    public ProductSeller(long sellerId, long number, double price) {
        this.sellerId = sellerId;
        this.number = number;
        this.price = price;
    }

    public long getSellerId() {
        return sellerId;
    }


    public Product getProduct() {
        return product;
    }

    public Seller getSeller() {
        return seller;
    }

    public double getPrice() {
        return price;
    }

    public int getRemainingItems() {
        return remainingItems;
    }

    public  long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void sell(int amount) throws NotEnoughProductsException {
        if (remainingItems < amount) {
            throw new NotEnoughProductsException("Not enough products");
        }
        remainingItems -= amount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getPriceInOff() {
        if (priceInOff == null) {
            return (long) price;
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


}
