package Model;

import Model.Account.Seller;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Bid {
    private Seller seller;
    private Product product;
    private DoubleProperty latestPrice=new SimpleDoubleProperty();

    public Bid(Seller seller, Product product) {
        this.seller = seller;
        this.product = product;
    }

    public Seller getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public double getLatestPrice() {
        return latestPrice.get();
    }

    public DoubleProperty latestPriceProperty() {
        return latestPrice;
    }

    public void setLatestPrice(double latestPrice) {
    if (latestPrice>this.latestPrice.get())
        this.latestPrice.set(latestPrice);
    }
}
