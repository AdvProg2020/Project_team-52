package Model;

import Model.Data.Data;
import Model.DataBase.DataBase;
import Model.Interface.AddingNew;
import Model.Interface.Packable;
import Exception.*;

import java.util.Collections;
import java.util.List;

public class LogHistory implements Packable<LogHistory>{
    private static List<LogHistory> list= new ArrayList<>();

    private long logHistoryId;
    private double finalAmount;
    private double discountAmount;
    private double auctionDiscount;
    private FieldList fieldList;
    private List<ProductLog> productLogList= new ArrayList<>();

    private LogHistory() {
    }


    public static List<LogHistory> getList() {
        return Collections.unmodifiableList(list);
    }

    public long getId() {
        return logHistoryId;
    }

    public FieldList getFieldList() {
        return fieldList;
    }

    public double getAuctionDiscount() {
        return auctionDiscount;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public List<ProductLog> getProductLogList() {
        return Collections.unmodifiableList(productLogList);
    }
    public void setLogHistoryId(long logHistoryId) {
        this.logHistoryId = logHistoryId;
    }

    public static void setList(List<LogHistory> list) {
        LogHistory.list = list;
    }


    public static void addLog( LogHistory logHistory) {
        logHistory.setLogHistoryId(AddingNew.getRegisteringId().apply(LogHistory.getList()));
        list.add(logHistory);
        DataBase.save(logHistory,true);
    }
    @Override
    public Data<LogHistory> pack() {
        return new Data<LogHistory>()
                .addField(logHistoryId)
                .addField(finalAmount)
                .addField(discountAmount)
                .addField(auctionDiscount)
                .addField(fieldList)
                .addField(productLogList)
                .setInstance(new LogHistory());
    }


    public static LogHistory getLogHistoryById(long id) throws LogHistoryDoesNotExistException {
        return list.stream()
                .filter(logHistory -> id == logHistory.getId())
                .findFirst()
                .orElseThrow(() -> new LogHistoryDoesNotExistException(
                        "LogHistory with the id:" + id + " does not exist in list of all logHistory."
                ));
    }

    public static void checkExistOfLogHistoryById(long id ,  List<Long> longList, Packable<?> packable) throws LogHistoryDoesNotExistException {
        if (longList.stream().noneMatch(Id -> id == Id)) {
            throw new LogHistoryDoesNotExistException(
                    "In the " + packable.getClass().getSimpleName() + " with id:" + packable.getId() + " the LogHistory with id:"+  id + " does not exist."
            );
        }
    }

    public LogHistory(double finalAmount, double discountAmount, double auctionDiscount, FieldList fieldList, List<ProductLog> productLogList) {
        this.finalAmount = finalAmount;
        this.discountAmount = discountAmount;
        this.auctionDiscount = auctionDiscount;
        this.fieldList = fieldList;
        this.productLogList = productLogList;
    }

    @Override
    public LogHistory dpkg(Data<LogHistory> data) {
        this.logHistoryId = (long) data.getFields().get(0);
        this.finalAmount = (double) data.getFields().get(1);
        this.discountAmount = (double) data.getFields().get(2);
        this.auctionDiscount = (double) data.getFields().get(3);
        this.fieldList = (FieldList) data.getFields().get(4);
        this.productLogList = (List<ProductLog>) data.getFields().get(5);
        return this;
    }
    @Override
    public String toString() {
        return "LogHistory{" +
                "logId=" + logHistoryId +
                ", amount=" + finalAmount +
                ", discountAmount=" + discountAmount +
                ", auctionDiscount=" + auctionDiscount +
                ", fieldList=" + fieldList +
                ", productList=" + productLogList +
                '}';
    }


}
