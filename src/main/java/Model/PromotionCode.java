package Model;

import Model.Account.Account;
import Model.Account.Customer;
import Model.Data.Data;
import Model.DataBase.DataBase;
import Model.Interface.AddingNew;
import Model.Interface.Packable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PromotionCode implements Packable<PromotionCode>, Cloneable {
    private static List<PromotionCode> list;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private long id;
    private String discountCode;
    private LocalDate start;
    private LocalDate end;
    private Discount discount;
    private long frequentUse;
    private List<Long> accountList = new ArrayList<>();



    public long getId() {
        return id;
    }

    public String getDiscountCode() {
        return discountCode;
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

    public long getFrequentUse() {
        return frequentUse;
    }

    public List<Long> getAccountList() {
        return Collections.unmodifiableList(accountList);
    }



    public static List<PromotionCode> getList() {
        return Collections.unmodifiableList(list);
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setFrequentUse(long frequentUse) {
        this.frequentUse = frequentUse;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static void setList(List<PromotionCode> list) {
        PromotionCode.list = list;
    }

    public void addAccount(long accountId) {
        accountList.add(accountId);
        DataBase.save(this);
    }

    public void removeAccount(long accountId) {
        accountList.remove(accountId);
        DataBase.save(this);
    }

    public static void addDiscountCode( PromotionCode promotionCode) {
        promotionCode.setId(AddingNew.getRegisteringId().apply(PromotionCode.getList()));
        list.add(promotionCode);
        DataBase.save(promotionCode, true);
    }

    public static void removeFromDiscountCode(PromotionCode promotionCode) {
        list.removeIf(dis -> promotionCode.getId() == dis.getId());
        DataBase.remove(promotionCode);

    }



    @Override
    public Data<PromotionCode> pack() {
        return new Data<PromotionCode>()
                .addField(id)
                .addField(discountCode)
                .addField(start)
                .addField(end)
                .addField(discount)
                .addField(frequentUse)
                .addField(accountList)
                .setInstance(new PromotionCode());
    }


    public static PromotionCode getDiscountCodeById(long id) throws DiscountCodeExpiredException {
        return list.stream()
                .filter(code -> id == code.getId())
                .findFirst()
                .orElseThrow(() -> new DiscountCodeExpiredException(
                        "DiscountCode with the id:" + id + " does not exist in list of all discountCodes."
                ));
    }

    public static PromotionCode getDiscountCodeByCode(String code) throws DiscountCodeExpiredException {
        return list.stream()
                .filter(promotionCode -> code.equals(promotionCode.getDiscountCode()))
                .findFirst()
                .orElseThrow(() -> new DiscountCodeExpiredException(
                        "DiscountCode with the code:" + code + " does not exist in list of all discountCodes."
                ));
    }

    public static String createDiscountCode() {
        return UUID.randomUUID().toString();
    }

    public double getDiscountCodeDiscount(double price) {
        return Math.min(discount.getAmount(), discount.getPercent() * price / 100);
    }

    public void editField(@NotNull String fieldName, String value) throws FieldDoesNotExistException, NumberFormatException, DateTimeParseException {

        switch (fieldName) {
            case "start":
                setStart(LocalDate.parse(value, formatter));
                break;
            case "end":
                setEnd(LocalDate.parse(value, formatter));
                break;
            case "frequentUse":
                setFrequentUse(Long.parseLong(value));
                break;
            case "maxDiscountAmount":
                discount.setAmount(Double.parseDouble(value));
                break;
            case "discountPercent":
                discount.setPercent(Double.parseDouble(value));
                break;
            default:
                throw new FieldDoesNotExistException("Not such a field in DiscountCode");
        }
    }

    public void checkExpiredDiscountCode(boolean exception) throws DiscountCodeExpiredException, AccountDoesNotExistException {
        if (LocalDate.now().isAfter(end)) {
            PromotionCode.removeFromDiscountCode(this);
            List<Account> accounts = new ArrayList<>();
            for (long aLong : accountList) {
                ((Customer) Account.getAccountById(aLong)).removeFromDiscountCodeList(id);
            }
            if (exception) {
                throw new DiscountCodeExpiredException("DiscountCode with id:" + id + " expired.");
            }
        }
    }

    public PromotionCode(String discountCode, LocalDate start, LocalDate end, Discount discount, long frequentUse) {
        this.discountCode = discountCode;
        this.start = start;
        this.end = end;
        this.discount = discount;
        this.frequentUse = frequentUse;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "DiscountCode{" +
                "id=" + id +
                ", discountCode='" + discountCode + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", discount=" + discount +
                ", frequentUse=" + frequentUse +
                '}';
    }


}
