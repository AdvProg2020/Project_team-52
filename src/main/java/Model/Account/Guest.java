package Model.Account;

import Model.Info;
import Model.Interface.AddingNew;
import Model.Interface.Packable;
import Model.Role;

import java.time.LocalDate;
import java.util.List;

public class Guest extends Account {
    public Guest(String userName, String password, String email, Role role, Info personalInfo) {
        super(userName, password, email, role, personalInfo);
    }

    public Guest(long id, String userName, String password, Info personalInfo) {
        super(id,userName,password,personalInfo);
    }

    public static Guest autoCreateGuest() {
        long id = AddingNew.getRegisteringId().apply(getList());
        return new Guest(id,"guest " + id, "", new Info("guestInfo", null, LocalDate.now()));
    }


}
