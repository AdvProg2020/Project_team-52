package Model;

import Model.Account.Seller;

public enum Role {
    SELLER ,CUSTOMER,MANAGER,GUEST;

    public static Role getRole(String role) {
        if ("Seller".equals(role)) {
            return SELLER;
        } else if ("Manager".equals(role)) {
            return MANAGER;
        } else if ("Customer".equals(role)) {
            return CUSTOMER;
        } else if ("Guest".equals(role)) {
            return GUEST;
        }
        return null;
    }
    public static String getRoleType(Role role) {
        switch (role) {
            case CUSTOMER:
                return "Customer";
            case SELLER:
                return "Seller";
            case GUEST:
                return "Guest";
            default:
                return "Manager";
        }
    }
}
