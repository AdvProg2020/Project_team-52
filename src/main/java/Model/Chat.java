package Model;

import Model.Account.Account;

import java.util.ArrayList;
import java.util.Collections;

public class Chat {

    private static ArrayList<Chat> allChats = new ArrayList<>();
    String id;
    private ArrayList<Text> messages= new ArrayList<>();
    private ArrayList<Account> members = new ArrayList<>();

    public Chat(String id, ArrayList<Text> messages, ArrayList<Account> members) {
        this.id = id;
        this.messages = messages;
        this.members = members;
    }

    public ArrayList<Text> getMessages() {
        return messages;
    }

    public ArrayList<Account> getMembers() {
        return members;
    }

    public String getId() {
        return id;
    }

    private void sendMessage(Text message) {
        messages.add(message);
    }

    public void addMembers(Account accounts) {
        Collections.addAll(this.members, accounts);
    }

    public void removeMember(Account account) {
        this.members.add(account);
    }

    public void message(Account account, String chatId, String content) throws Exception {
        Chat chat = getChat(chatId);
        if (!chat.getMembers().contains(account))
            throw new Exception("user is not allowed to send message to this chat");
    }

    private Chat getChat(String id) throws Exception {
        for (Chat chat : allChats) {
            if (chat.getId().equals(id)) return chat;
        }
        throw new Exception("chat id is invalid");
    }

}
