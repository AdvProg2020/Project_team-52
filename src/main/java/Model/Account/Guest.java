package Model.Account;

public class Guest extends Account {
    public static Guest autoCreateGuest() {
        long id = AddingNew.getRegisteringId().apply(getList());
        return new Guest(id,"guest " + id, "", new Info("guestInfo", null, LocalDate.now()));
    }

    private Guest(long id, String userName, String password, Info personalInfo) {
        super(id, userName, password, personalInfo);
    }
}
