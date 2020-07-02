package View.Interface;

import Model.*;
import Model.Account.Account;

import java.util.function.Function;

public class Shows {
    private static Function<Product, String> showProduct = product ->
            "-------------------product--------------------" + System.lineSeparator() +
                    String.format("ProductId:%d \nProductName:%s \n", product.getId(), product.getName()) +
                    (product.getCategory() == null ? "" : String.format("ProductCategoryId:%d \nProductCategoryName:%s \n", product.getCategory().getId(), product.getCategory().getName())) +
                    (product.getOff() == null ? "" : String.format("ProductAuctionId:%d \nProductAuctionName:%s \n", product.getOff().getId(), product.getOff().getName())) +
                    "Prices and Sellers:" + System.lineSeparator() +
                    product.getSellerList().stream().map(productOfSeller ->
                            String.format("SellerId:%d Number:%d %s \n", productOfSeller.getSellerId(), productOfSeller.getNumber(),
                                    (product.getOff() == null ? "Price:" + productOfSeller.getPrice() : String.format("Old price:%f newPrice:%f", productOfSeller.getPrice(),
                                            productOfSeller.getPrice() - product.getOff().getAuctionDiscount(productOfSeller.getPrice())))
                            )).reduce("", (a, b) -> a + b) +
                    (product.getCategoryInfo() == null ? "" : Shows.getShowInfo().apply(product.getCategoryInfo())) + System.lineSeparator() +
                    "----------------------------------------------";

    private static Function<Off, String> showAuction = auction ->
            "--------------------auction-------------------" + System.lineSeparator() +
                    String.format("ProductAuctionId:%d \nProductAuctionName:%s \n", auction.getId(), auction.getName()) +
                    String.format("Start:%s \nEnd:%s \n", auction.getStart(), auction.getEnd()) +
                    String.format("Discount percent:%f \nDiscount limit:%f \n", auction.getDiscount().getPercent(), auction.getDiscount().getAmount()) +
                    "----------------------------------------------";

    private static Function<Account, String> showAccount = account ->
            "--------------------account-------------------" + System.lineSeparator() +
                    String.format("AccountId:%d \nAccountUsername:%s \n", account.getId(), account.getUserName()) +
                    String.format("AccountType:%s \nAccountRegisterDate:%s \n", account.getPersonalInfo().getSubject(), account.getPersonalInfo().getUploadDate()) +
                    account.getPersonalInfo().getList().getFieldList().stream().map(
                            field -> String.format("%s : %s\n", field.getFieldName(), (field).getString())
                    ).reduce("", (a, b) -> a + b) + "----------------------------------------------";

    private static Function<Info, String> showInfo = info ->
            "---------------------info---------------------" + System.lineSeparator() +
                    info.getList().getFieldList().stream().map(
                            field -> String.format("%s : %s\n", field.getFieldName(), (field).getString())
                    ).reduce("", (a, b) -> a + b) + "----------------------------------------------";

    private static Function<Category, String> showCategory = category ->
            "-------------------category-------------------" + System.lineSeparator() +
                    String.format("CategoryId:%d \nCategoryName:%s \n", category.getId(), category.getName()) +
                    "Category fields: \n" +
                    category.getCategoryFields().getFieldList().stream().map(
                            field -> field.getFieldName() + System.lineSeparator()
                    ).reduce("", (a, b) -> a + b) + "----------------------------------------------";

    private static Function<LogHistory, String> showLogHistory = logHistory ->
            "------------------logHistory------------------" + System.lineSeparator() +
                    String.format("LogId:%d \nLogAmount:%f \n", logHistory.getId(), logHistory.getFinalAmount()) +
                    String.format("DiscountAmount:%f \nAuctionDiscount:%f \n", logHistory.getDiscountAmount(), logHistory.getAuctionDiscount()) +
                    "Log fields: " +
                    logHistory.getFieldList().getFieldList().stream().map(
                            field -> String.format("%s : %s\n", field.getFieldName(), (field).getString())
                    ).reduce("", (a, b) -> a + b) +
                    logHistory.getProductLogList().stream().map(
                            productLog -> String.format("ProductId:%d Price:%f FinalPrice:%f", productLog.getProductId(), productLog.getPrice(), productLog.getFinalPrice())
                    ).reduce("", (a, b) -> a + b) + "----------------------------------------------";

    private static Function<Request, String> showRequest = request ->
            "--------------------request-------------------" + System.lineSeparator() +
                    String.format("RequestId:%d \nAccountId:%d \n", request.getId(), request.getAccountId()) +
                    String.format("RequestType:%s \nInformation:%s \n", request.getTypeOfRequest(), request.getInformation()) +
                    "ForPend info:" + System.lineSeparator() + (request.getForPend() instanceof Product ?
                    Shows.getShowProduct().apply((Product)request.getForPend()) :
                    Shows.getShowOff().apply((Off)request.getForPend())) + System.lineSeparator() +
                    "----------------------------------------------";

    private static Function<Comment, String> showComment = comment ->
            "--------------------comment-------------------" + System.lineSeparator() +
                    String.format("CommentId:%d \nAccountId:%d \nGoodId:%d", comment.getId(), comment.getUserId(), comment.getGoodId()) +
                    "Comments : " + System.lineSeparator() +
                    comment.getFieldList().getFieldList().stream().map(
                            field -> String.format("%s : %s\n", field.getFieldName(), (field).getString())
                    ).reduce("", (a, b) -> a + b) + "----------------------------------------------";





    public static Function<Product, String> getShowProduct() {
        return showProduct;
    }

    public static Function<Off, String> getShowOff() {
        return showAuction;
    }

    public static Function<Account, String> getShowAccount() {
        return showAccount;
    }

    public static Function<Category, String> getShowCategory() {
        return showCategory;
    }

    public static Function<LogHistory, String> getShowLogHistory() {
        return showLogHistory;
    }

    public static Function<Info, String> getShowInfo() {
        return showInfo;
    }

    public static Function<Request, String> getShowRequest() {
        return showRequest;
    }

    public static Function<Comment, String> getShowComment() {
        return showComment;
    }


}
