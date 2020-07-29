package Model;

import Model.Data.Data;
import Model.DataBase.DataBase;
import Model.Interface.AddingNew;
import Model.Interface.ForPend;
import Model.Interface.Packable;
import Exception.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class Off implements Packable<Off>, ForPend,Cloneable {
    private static List<Off> list= new ArrayList<>();

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private long auctionId;
    private String auctionName;
    private String stateForPend;
    private LocalDate start;
    private LocalDate end;
    private Discount discount;
    private List<Long> productList=new ArrayList<>();

    public static List<Off> getList() {
        return Collections.unmodifiableList(list);
    }




    public List<Long> getProductList() {
        return Collections.unmodifiableList(productList);
    }

    public long getId() {
        return auctionId;
    }

    public String getStateForPend() {
        return stateForPend;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public Discount getDiscount() {
        return discount;
    }

    public String getName() {
        return auctionName;
    }



    public void setProductList(List<Long> productList) {
        this.productList = productList;
    }

    public void setStateForPend(String stateForPend) {
        this.stateForPend = stateForPend;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public static void setList(List<Off> list) {
        Off.list = list;
    }



    public static void addAuction(Off auction, boolean New) {
        if (New) auction.setAuctionId(AddingNew.getRegisteringId().apply(getList()));
        list.add(auction);
        DataBase.save(auction, true);
    }

    public static void removeOff(Off auction) {
        list.removeIf(auc -> auction.getId() == auc.getId());
        DataBase.remove(auction);
    }

    public void addProductToOff(long productId) {
        productList.add(productId);
        DataBase.save(this);
    }


    public void removeProductFromOff(long productId) {
        productList.remove(productId);
        DataBase.save(this);
    }



    public static Off getAuctionById(long id) throws OffDoesNotExistException {
        return list.stream()
                .filter(product -> id == product.getId())
                .findFirst()
                .orElseThrow(() -> new  OffDoesNotExistException (
                        "Auction does not exist with this id:" + id + " in list of all Auctions."
                ));
    }

    public static void checkExistOfAuctionById(long id ,  List<Long> longList, Packable<?> packable) throws  OffDoesNotExistException {
        if (longList.stream().noneMatch(Id -> id == Id)) {
            throw new OffDoesNotExistException(
                    "In the " + packable.getClass().getSimpleName() + " with id:" + packable.getId() + " the Auction with id:"+  id + " does not exist."
            );
        }
    }

    public double getAuctionDiscount(double price) {
        return Math.min(discount.getPercent() * price / 100, discount.getAmount());
    }

    public void editField( String fieldName, String value) throws FieldDoesNotExistException, NumberFormatException {

        switch (fieldName) {
            case "auctionName":
                setAuctionName(value);
                break;
            case "start":
                setStart(LocalDate.parse(value, formatter));
                break;
            case "end":
                setEnd((LocalDate.parse(value, formatter)));
                break;
            case "stateForPend":
                setStateForPend(value);
                break;
            case "discountMaxAmount":
                discount.setAmount(Double.parseDouble(value));
                break;
            case "discountPercent":
                discount.setPercent(Double.parseDouble(value));
                break;
            default:
                throw new FieldDoesNotExistException("Field with the name:" + fieldName + " doesn't exist.");
        }
    }



    public Off(String auctionName, LocalDate start, LocalDate end, Discount discount) {
        this.auctionName = auctionName;
        this.start = start;
        this.end = end;
        this.discount = discount;
    }




    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public Data<Off> pack() {
        return null;
    }

    @Override
    public Off dpkg(Data<Off> data) {
        this.auctionId = (long) data.getFields().get(0);
        this.productList = (List<Long>) data.getFields().get(1);
        this.stateForPend = (String) data.getFields().get(2);
        this.start = (LocalDate) data.getFields().get(3);
        this.end = (LocalDate) data.getFields().get(4);
        this.discount = (Discount) data.getFields().get(5);
        return this;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "auctionId=" + auctionId +
                ", productList=" + productList +
                ", status=" + stateForPend +
                ", start=" + start +
                ", end=" + end +
                ", discount=" + discount +
                '}';
    }


    public double getOffDiscount(double price) {
        return Math.min(discount.getPercent() * price / 100, discount.getAmount());
    }
}
