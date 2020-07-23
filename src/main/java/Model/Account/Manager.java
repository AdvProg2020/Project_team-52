package Model.Account;

import Model.Info;
import Model.Role;

public class Manager extends Account {

    public Manager(String userName, String password, String email, Role role, Info personalInfo) {
        super(userName, password, email, role, personalInfo);
    }


    public Manager(String userName) {
        super(userName);
    }


    public static boolean isThereAnyManager() {
        return list.stream().anyMatch(account -> account instanceof Manager);
    }
}
