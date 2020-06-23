package controller.account;

import model.*;

public class Account {
    private String password;
    private Role role;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String token;
    private String id;

    public Account() {
        this.username = new String();
        password = new String();
        email = new String();
        firstName = new String();
        lastName = new String();
        token = new String();
    }

    public Account(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Account(String username, String password, Role role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User makeUser() {
        if (role == Role.CUSTOMER) {
            Customer customer = new Customer(username, password, email, role);
            customer.changeFirstName(firstName);
            customer.changeLastName(lastName);
            return customer;
        }
        else if (role == Role.SELLER) {
            Seller seller = new Seller(username, password, email, role);
            seller.changeFirstName(firstName);
            seller.changeLastName(lastName);
            return seller;
        } else {
            Admin admin = new Admin(username,password,email,role);
            admin.changeFirstName(firstName);
            admin.changeLastName(lastName);
            return admin;
        }
    }

}