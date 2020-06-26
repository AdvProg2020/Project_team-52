package Model.Account;

import Model.DataBase.DataBase;
import Model.Info;
import Model.Interface.ForPend;
import Model.Off;
import Model.Role;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

public class Seller extends Account{

    private List<Off> allOffs;

    private Info companyInfo;
    private List<Long> logHistoryList = new ArrayList<>();
    private List<Long> productList = new ArrayList<>();
    private List<Long> auctionList = new ArrayList<>();
    private List<ForPend> forPendList = new ArrayList<>();

    public Seller() {
        allOffs = new ArrayList<>();
    }

    public Seller(String username, String password, String email, Role role,Info personalIfo) {
        super(username, password, email, role,personalIfo);
        allOffs = new ArrayList<>();
    }

    public void setCompanyName(String companyName) {
        getDetails().put("Company Name", companyName);
    }

    public String getCompanyName() {
        return getDetails().get("Company Name");
    }

    public void setAllOffs(List<Off> allOffs) {
        this.allOffs = allOffs;
    }

    public List<Off> getAllOffs(Pageable pageable) {
        return allOffs;
    }

    public void changeCompanyName(String companyName) {
        getDetails().put("Company Name", companyName);
    }

    public void addToLogHistoryList(long logHistoryId) {
        logHistoryList.add(logHistoryId);
        DataBase.save(this);
    }

    public void removeFromLogHistoryList(long logHistoryId) {
        logHistoryList.remove(logHistoryId);
        DataBase.save(this);
    }

    public void addToAuctionList(long auctionId) {
        auctionList.add(auctionId);
        DataBase.save(this);
    }

    public void removeFromAuctionList(long auctionId) {
        auctionList.remove(auctionId);
        DataBase.save(this);
    }

    public void addToProductList(long productId) {
        productList.add(productId);
        DataBase.save(this);
    }

    public void removeFromProductList(long productId) {
        productList.remove(productId);
        DataBase.save(this);
    }

    public void addToPendList(ForPend forPend) {
        forPendList.add(forPend);
        DataBase.save(this);
    }

    public void removeFromPendList(ForPend forPend) {
        forPendList.remove(forPend);
        DataBase.save(this);
    }

    public List<long> getLogHistoryList() {
        return logHistoryList;
    }

    public void setCompanyInfo(Info info) {
        this.companyInfo = companyInfo;
        DataBase.save(this);
    }
}
