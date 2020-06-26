package Model.Account;

import Model.Info;
import Model.Role;

public class Manager extends Account {

    public Manager(String userName, String password, String email, Role role, Info personalInfo) {
        super(userName, password, email, role, personalInfo);
    }

}
