package Model;



public class Discount{


private double percent;
private double amount;

public double getPercent() {
        return percent;
        }

public double getAmount() {
        return amount;
        }

public void setAmount(double amount) {
        this.amount = amount;
        }

public void setPercent(double percent) {
        this.percent = percent;
        }

public double getDiscountAmount(double price) {
        return (price = percent * price / 100) < amount ? price : amount;
        }

public Discount(double percent, double amount) {
        this.percent = percent;
        this.amount = amount;
        }
        }