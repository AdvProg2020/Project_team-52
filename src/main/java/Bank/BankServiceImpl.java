package Bank;

import java.io.IOException;

public class BankServiceImpl implements BankService {
    private BankApiSocket socket;

    public BankServiceImpl() {
        socket = new BankApiSocket();
        try {
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createAccount(String firstName, String lastName, String userName, String password, String repeatPassword) throws BankException {
        String message = "create_account " + firstName + " " + lastName + " " + userName + " " + password + " " + repeatPassword;
        String response = socket.sendMessage(message);
        try {
            int accountId = Integer.parseInt(response);
            return accountId;
        } catch (NumberFormatException ex) {
            throw new BankException(response);
        }
    }

    @Override
    public String getToken(String userName, String password) throws BankException {
        String message = "get_token " + userName + " " + password;
        String response = socket.sendMessage(message);
        if (response.length() != 32) {
            throw new BankException(response);
        }
        return response;
    }

    @Override
    public int createReceipt(String token, String receiptType, int money, int sourceId, int destId, String description) throws BankException {
        String message = "create_receipt " + token + " " + receiptType + " " + money + " " + sourceId + " " + destId + " " + description;
        String response = socket.sendMessage(message);
        try {
            int receiptId = Integer.parseInt(response);
            return receiptId;
        } catch (NumberFormatException ex) {
            throw new BankException(response);
        }
    }

    @Override
    public String getTransactions(String token, String type) throws BankException {
        String message = "get_transactions " + token + " " + type;
        String response = socket.sendMessage(message);

        if (!response.trim().isEmpty() && !response.trim().startsWith("{")) {
            throw new BankException(response);
        }

        return response;
    }

    @Override
    public void pay(int receiptId) throws BankException {
        String message = "pay " + receiptId;
        String response = socket.sendMessage(message);

        if (!response.equals("done successfully")) {
            throw new BankException(response);
        }
    }

    @Override
    public int getBalance(String token) throws BankException {
        String message = "get_balance " + token;
        String response = socket.sendMessage(message);

        try {
            int balance = Integer.parseInt(response);
            return balance;
        } catch (NumberFormatException ex) {
            throw new BankException(response);
        }
    }

    @Override
    public void exit() throws BankException {
        socket.sendMessage("exit");
    }
}
