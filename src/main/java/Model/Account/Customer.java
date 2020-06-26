package Model.Account;

import Model.*;

import java.util.List;

public class Customer extends Account {
    private List<Request> requestList;

    private List<Long> logHistoryList = new ArrayList<>();
    private Cart cart;
    private List<Long> discountCodeList = new ArrayList<>();

    private List<Off> availableOff;

    public Customer() {
        requestList = new ArrayList<>();
        availableOff = new ArrayList<>();
    }

    public Customer(String username, String password, String email, Role role, Info personalInfo) {
        super(username, password, email, role,personalInfo);
        requestList = new ArrayList<>();
        availableOff = new ArrayList<>();
    }


    public List<Request> getRequestList() {
        return requestList;
    }

    public List<Off> getAvailableOff() {
        return availableOff;
    }

    public void addOff(Off off) {
        availableOff.add(off);
    }

    public void addRequest(Request request) {
        requestList.add(request);
    }

    public Cart getCart() {
        return cart;
    }

    public void addToCart(long productId, long sellerId) {
        cart.addProductToCart(sellerId, productId);
    }

    public void removeFromCart(long productId, long sellerId) {
        cart.addProductToCart(sellerId, productId);
    }

    public void addToLogHistoryList(long id) {
        logHistoryList.add(logHistoryId);
    }

    public void setCart(Cart autoCreateCart) {
            this.cart = cart;
    }

    public void removeFromDiscountCodeList(long id) {

            discountCodeList.remove(discountCodeId);

    }

    public List<Long> getLogHistoryList() {
        return logHistoryList;
    }
}
