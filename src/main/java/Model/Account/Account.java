package Model.Account;
import Exception.*;

import Model.Data.Data;
import Model.DataBase.DataBase;
import Model.Field.Field;
import Model.Info;
import Model.Interface.AddingNew;
import Model.Interface.Packable;
import Model.Request;
import Model.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account implements Packable<Account> {


    protected static List<Account> list = new ArrayList<>();
    protected static List<Account> inRegistering = new ArrayList<>();
    private long id;

    private String userName;

    private String password;

    private String token;

    private int bankAccountId;

    private String email;

    private Role role;

    private long credit;

    protected Info personalInfo;



    private Map<String, String> details = new HashMap<>();


    public Account(String userName, String password, String email, Role role  , Info personalInfo ) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    protected Account(String userName) {
        this.userName=userName;
    }

    public Account(long id, String userName, String password, Info personalInfo) {
        this.id=id;
        this.userName=userName;
        this.password=password;
        this.personalInfo=personalInfo;
    }


    public static void addAccount(Account account) {
        account.setId(AddingNew.getRegisteringId().apply(getList()));
        list.add(account);
        DataBase.save(account, true);
    }

    public static List<Account> getList() {
        return list;
    }

    public static void removeFromInRegistering(Account account) {
        inRegistering.remove(account);
    }

    public static void addToInRegisteringList(Account account) {
        inRegistering.add(account);
    }

    public static boolean isThereAnyInRegisteringWithThisUsername(String username) {
        return inRegistering.stream().anyMatch(account -> username.equals(account.getUserName()));
    }

    public static boolean isThereAnyAccountWithThisUsername(String username) {
        return list.stream().anyMatch(account -> username.equals(account.getUserName()));
    }

    public String getFullName() {
        return details.get("firstname") + " " + details.get("lastname");
    }

    public void changeFirstName(String name) {
        details.put("firstname", name);
    }

    public void changeLastName(String name) {
        details.put("lastname", name);
    }

    public void changeEmail(String Email) {
        this.email = Email;
    }

    @Override
    public Data<Account> pack() {
        return null;
    }

    @Override
    public Account dpkg(Data<Account> data) {
        return null;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public Role getRole() {
        return role;
    }

    public String getFirstName() {
        return details.get("firstname");
    }

    public String getLastName() {
        return details.get("lastname");
    }

    public Info getPersonalInfo() {
        return personalInfo;
    }

    public String getCompanyName() {
        if (role == Role.SELLER)
            return details.get("Company Name");
        return null;
    }

    public Map<String, String> getDetails() {

        Map<String, String> essentialDetails = new HashMap();
        essentialDetails.put("Username", this.userName);
        essentialDetails.put("FirstName", details.get("firstname"));
        essentialDetails.put("LastName", details.get("lastname"));
        essentialDetails.put("Email", this.email);
        if (role == Role.SELLER) {
            essentialDetails.put("Company Name", details.get("Company Name"));
        }
        return essentialDetails;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void changeUsername(String username) {
        this.userName = username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void changePassword(String oldPassword, String newPassword) throws NoAccessException {
        if (oldPassword.equals(this.password)) {
            this.password = newPassword;
        } else {
            throw new NoAccessException("You are not allowed to do that.");
        }
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public long getCredit() {
        return credit;
    }

    public void pay(long amount) throws NotEnoughCreditException {
        if (amount > credit) {
            throw new NotEnoughCreditException("You don't have enough credit to pay " + amount+ credit);
        }

        credit -= amount;
    }



    public static Account getAccountById(long id) throws AccountDoesNotExistException {
        return list.stream()
                .filter(account -> id == account.getId())
                .findFirst()
                .orElseThrow(() -> new AccountDoesNotExistException(
                        "The Account with the id:" + id + " not exist in all account list."
                ));
    }

    public static Account getAccountByUserName(String name) throws AccountDoesNotExistException {
        return list.stream()
                .filter(account -> name.equals(account.getUserName()))
                        .findFirst()
                        .orElseThrow(() -> new AccountDoesNotExistException(
                                "The username:" + name + " not exist in all account list."
                        ));
    }

    public String getPassword() {
        return password;
    }

    public void editField(String fieldName, String value) throws FieldDoesNotExistException {

        if ("password".equals(fieldName)) {
            setPassword(value);
        } else {
            Field field = personalInfo.getList().getFieldByName(fieldName);
            field.setString(value);
        }

        DataBase.save(this);
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setCredit(double v) {
        this.credit=credit;
    }

    public void setPersonalInfo(Info personalInfo) {
        this.personalInfo = personalInfo;
    }

    public void acceptRequest(Request request) throws AccountDoesNotExistException {
        request.acceptRequest();
    }

    public void declineRequest(Request requestById) throws AccountDoesNotExistException {
        requestById.declineRequest();
    }
    

    public static void deleteAccount(Account account) {
        list.removeIf(acc -> account.getId() == acc.getId());
        DataBase.remove(account);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
