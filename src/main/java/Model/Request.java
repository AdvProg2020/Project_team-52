package Model;

import Model.Account.Account;
import Model.Account.Seller;
import Model.Data.Data;
import Model.DataBase.DataBase;
import Model.Interface.AddingNew;
import Model.Interface.ForPend;
import Model.Interface.Packable;

import java.util.Collections;
import java.util.List;

public class Request implements Packable<Request>, Packable<Request> {



    private static List<Request> list;

    private long requestId;
    private long accountId;
    private String information;
    private String typeOfRequest;
    private ForPend forPend;
    private RequestCondition requestCondition;



    public long getId() {
        return requestId;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getTypeOfRequest() {
        return typeOfRequest;
    }

    public ForPend getForPend() {
        return forPend;
    }

    public String getInformation() {
        return information;
    }

    public static List<Request> getList() {
        return Collections.unmodifiableList(list);
    }



    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public static void setList(List<Request> list) {
        Request.list = list;
    }


    public void acceptRequest() throws AccountDoesNotExistException {

        Seller seller = (Seller) Account.getAccountById(accountId);

        switch (typeOfRequest) {
            case "new":
                accept_new();
                break;
            case "remove":
                accept_remove();
                break;
            case "edit":
                accept_edit();
        }

        seller.removeFromPendList(forPend);

        list.remove(this);
        DataBase.remove(this);

        forPend.setStateForPend("Accepted");
    }

    private void accept_new() throws AccountDoesNotExistException {

        Seller seller = (Seller) Account.getAccountById(accountId);

        if (forPend instanceof Product) {
            Off off = ((Product) forPend).getAuction();
            Category category = ((Product) forPend).getCategory();
            if (off != null) {
                off.addProductToAuction(((Product) forPend).getId());
            }
            if (category != null) {
                category.addToProductList(((Product) forPend).getId());
            }
            Product.addProduct((Product) forPend);
            seller.addToProductList(((Product) forPend).getId());
        } else if (forPend instanceof Off) {
            Off.addAuction((Off) forPend);
            seller.addToAuctionList(((Off) forPend).getId());
        }
    }

    private void accept_edit() throws AccountDoesNotExistException {
        accept_remove();
        accept_new();
    }

    private void accept_remove() throws AccountDoesNotExistException {

        Seller seller = (Seller) Account.getAccountById(accountId);

        if (forPend instanceof Product) {
            Product.removeProduct((Product) forPend);
            Off off = ((Product) forPend).getAuction();
            Category category = ((Product) forPend).getCategory();
            if (off != null) {
                off.removeProductFromAuction(((Product) forPend).getId());
            }
            if (category != null) {
                category.removeFromProductList(((Product) forPend).getId());
            }
            Product.removeProduct((Product) forPend);
            seller.removeFromProductList(((Product) forPend).getId());
        } else if (forPend instanceof Off) {
            Off.removeAuction((Off) forPend);
            seller.removeFromAuctionList(((Off) forPend).getId());
        }
    }

    public void declineRequest() throws AccountDoesNotExistException {

        Seller seller = (Seller) Account.getAccountById(accountId);

        seller.removeFromPendList(forPend);

        list.remove(this);
        DataBase.remove(this);

        forPend.setStateForPend("Declined");

    }


    public static void addRequest( Request request) {
        request.setRequestId(AddingNew.getRegisteringId().apply(Request.getList()));
        list.add(request);
        DataBase.save(request, true);
    }

    public static void removeRequest(Request request) {
        list.removeIf(req -> request.getId() == req.getId());
        DataBase.remove(request);
    }



    @Override
    public Data<Request> pack() {
        return new Data<Request>()
                .addField(requestId)
                .addField(accountId)
                .addField(information)
                .addField(typeOfRequest)
                .addField(forPend)
                .setInstance(new Request());
    }

    @Override
    public Request dpkg( Data<Request> data) {
        this.requestId = (long) data.getFields().get(0);
        this.accountId = (long) data.getFields().get(1);
        this.information = (String) data.getFields().get(2);
        this.typeOfRequest = (String) data.getFields().get(3);
        this.forPend = (ForPend) data.getFields().get(4);
        return this;
    }


    public static Request getRequestById(long id) throws RequestDoesNotExistException {
        return list.stream()
                .filter(request -> id == request.getId())
                .findFirst()
                .orElseThrow(() -> new RequestDoesNotExistException(
                        "Request with the id:" + id + " does not exist in list of all request."
                ));
    }



    public Request(long accountId, String information, String typeOfRequest, ForPend forPend ,RequestCondition requestCondition) {
        this.accountId = accountId;
        this.information = information;
        this.typeOfRequest = typeOfRequest;
        this.forPend = forPend;
        this.requestCondition=requestCondition;
    }


    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", accountId=" + accountId +
                ", information='" + information + '\'' +
                ", typeOfRequest='" + typeOfRequest + '\'' +
                ", forPend=" + forPend +
                '}';
    }
}