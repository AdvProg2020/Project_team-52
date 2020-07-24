package Bank;

public interface BankService {
    int createAccount(String firstName, String lastName, String userName, String password, String repeatPassword) throws BankException;

    String getToken(String userName, String password) throws BankException;

    int createReceipt(String token, String receiptType, int money, int sourceId, int destId, String description) throws BankException;

    String getTransactions(String token, String type) throws BankException;

    void pay(int receiptId) throws BankException;

    int getBalance(String token) throws BankException;

    void exit() throws BankException;
}
