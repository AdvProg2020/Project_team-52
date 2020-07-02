package Controller;

import Model.Account.Account;
import Model.Category;
import Model.Off;
import Model.Product;

public class ControllerSection {

    private static ControllerSection ControllerSection = new ControllerSection();


    private Account currentAccount;
    private Product currentProduct;
    private Off currentOff;
    private Category currentCategory;


    public static ControllerSection getInstance() {
        return ControllerSection;
    }

    public void setAccount(Account account) {
        this.currentAccount = account;
    }

    public void setProduct(Product product) {
        this.currentProduct = product;
    }

    public void setAuction(Off auction) {
        this.currentOff = currentOff;
    }

    public void setCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public Off getAuction() {
        return currentOff;
    }

    public Account getAccount() {
        return currentAccount;
    }

    public Product getProduct() {
        return currentProduct;
    }

    public Category getCategory() {
        return currentCategory;
    }


}
