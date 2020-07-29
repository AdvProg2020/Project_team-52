package Model;

import Model.Data.Data;
import Model.DataBase.DataBase;
import Model.Interface.AddingNew;
import Model.Interface.Packable;
import Exception.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart implements Packable<Cart> {
    private static List<Cart> list=new ArrayList<>();

    private long id;
    private List<Long> sellersId =new ArrayList<>();
    private List<Long> productsId=new ArrayList<>();

    private Cart() {

    }


    public long getId() {
        return id;
    }

    public List<Long> getProductList() {
        return Collections.unmodifiableList(productsId);
    }

    public List<Long> getProductSellers() {
        return Collections.unmodifiableList(sellersId);
    }

    public static List<Cart> getList() {
        return Collections.unmodifiableList(list);
    }


    public static void setList(List<Cart> list) {
        Cart.list = list;
    }

    private void setId(long id) {
        this.id = id;
    }


    public void addProductToCart(long sellerId, long productId) {
        sellersId.add(sellerId);
        productsId.add(productId);
        DataBase.save(this);
    }

    public void removeProductFromCart(long sellerId, long productId) {
        for (int i = 0; i < sellersId.size(); i++) {
            if (sellersId.get(i) == sellerId && productsId.get(i) == productId) {
                productsId.remove(i);
                sellersId.remove(i);
                return;
            }
        }
        DataBase.save(this);
    }

    public static void addCart( Cart cart) {
        cart.setId(AddingNew.getRegisteringId().apply(getList()));
        list.add(cart);
        DataBase.save(cart, true);
    }

    public static void removeCart(Cart cart) {
        list.removeIf(car -> cart.getId() == car.getId());
        DataBase.remove(cart);
    }

    public boolean isThereThisProductInCart(long productId) {
        return productsId.stream().anyMatch(id -> productId == id);
    }

    public double getTotalPrice() throws ProductDoesNotExistException, SellerDoesNotSellOfThisProduct {
        double price = 0;
        for (int i = 0; i < productsId.size(); i++) {
            Product product = Product.getProductById(productsId.get(i));
            price += product.getProductOfSellerById(sellersId.get(i)).getPrice();
        }
        return price;
    }

    public double getTotalAuctionDiscount() throws ProductDoesNotExistException, SellerDoesNotSellOfThisProduct {
        double price = 0;
        for (int i = 0; i < productsId.size(); i++) {
            Product product = Product.getProductById(productsId.get(i));
            Off off = product.getOff();
            if (off != null) {
                price += off.getOffDiscount(product.getProductOfSellerById(sellersId.get(i)).getPrice());
            }
        }
        return price;
    }

    public static Cart getCartById(long id) throws CartDoesNotExistException {
        return list.stream()
                .filter(cart -> id == cart.getId())
                .findFirst()
                .orElseThrow(() -> new CartDoesNotExistException(
                        "Cart with the id:" + id + " does not exist in list of all carts."
                ));
    }


    public static Cart autoCreateCart() {
        Cart cart = new Cart(AddingNew.getRegisteringId().apply(getList()), new ArrayList<>(), new ArrayList<>());
        Cart.addCart(cart);
        return cart;
    }


    @Override
    public Data<Cart> pack() {
        return new Data<Cart>()
                .addField(id)
                .addField(productsId)
                .addField(sellersId)
                .setInstance(new Cart());
    }

    @Override
    public Cart dpkg(Data<Cart> data) {
        this.id = (long) data.getFields().get(0);
        this.productsId = (List<Long>) data.getFields().get(1);
        this.sellersId  = (List<Long>) data.getFields().get(2);
        return this;
    }


    public Cart(long id, List<Long> sellersId, List<Long> productsId) {
        this.id = id;
        this.sellersId = sellersId;
        this.productsId = productsId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", sellersId=" + sellersId +
                ", productsId=" + productsId +
                '}';
    }

}
