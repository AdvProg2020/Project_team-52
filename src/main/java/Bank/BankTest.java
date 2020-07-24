package Bank;

import java.util.Random;

public class BankTest {
    public static void main(String[] args) {
        System.out.println("Test started...");
        BankService bankService = new BankServiceImpl();
        System.out.println("Connected to socket...");
        Random random = new Random();
        int r = random.nextInt(1000);
        
        int accountId = bankService.createAccount("BobFirst", "BobLast", "BobUser" + r, "BobPass", "BobPass");
        System.out.println("AccountId: " + accountId);

        String token = bankService.getToken("BobUser" + r, "BobPass");
        System.out.println("Token: " + token);

        int receiptId = bankService.createReceipt(token, "deposit", 10000, -1, accountId, "description");
        System.out.println("ReceiptId: " + receiptId);

        bankService.pay(receiptId);
        System.out.println("Paid.");

        int balance = bankService.getBalance(token);
        System.out.println("Balance: " + balance);

        String transactions = bankService.getTransactions(token, "*");
        System.out.println("Transactions: " + transactions);

        bankService.exit();
        System.out.println("Finished.");
    }
}
