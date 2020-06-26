package Model;

public class ProductLog {
    private long productId;
    private String productName;
    private double price;
    private double auctionDiscount;
    private double finalPrice;

    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public double getAuctionDiscount() {
        return auctionDiscount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public ProductLog(long productId, String productName, double price, double auctionDiscount, double finalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.auctionDiscount = auctionDiscount;
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {
        return "ProductLog{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", auctionDiscount=" + auctionDiscount +
                ", finalPrice=" + finalPrice +
                '}';
    }
}
