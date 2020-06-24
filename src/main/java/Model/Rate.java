package Model;

public class Rate {

    private int id;
    private Customer customer;
    private double score;
    private Product product;

    public Rate(int id) {
        this.id = id;
    }

    public Rate(Customer customer, double score, Product product) {
        this.customer = customer;
        this.score = score;
        this.product = product;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return this.customer.getId();
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getId() {
        return this.id;
    }

    public double getScore() {
        return this.score;
    }
}