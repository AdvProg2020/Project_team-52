package Model;

import Model.Account.Account;

public class Text {
    String content;
    Account sender;
    String id;

    public Text(String content, Account sender, String id) {
        this.content = content;
        this.sender = sender;
        this.id = id;
    }

    public String getContent() {
        return content;
    }


    public Account getSender() {
        return sender;
    }

    public String getId() {
        return id;
    }
}
