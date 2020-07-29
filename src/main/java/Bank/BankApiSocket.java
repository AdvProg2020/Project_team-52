package Bank;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;
public class BankApiSocket {
    public static final int PORT = 2222;
    public static final String IP = "127.0.0.1";
    private static final int MAXIMUM_BRUTE_FORCE_ATTACKS = 5;
    private static final int SECONDS_TO_REMAIN_ON_BLACKLIST = 120;
    private static final int MAXIMUM_REQUESTS_PER_SECOND = 5;

    private HashMap<String, LocalDateTime> connectionTime = new HashMap<>();
    private HashMap<String, AtomicInteger> numberOfRequests = new HashMap<>();
    private HashMap<String, AtomicInteger> failedTries = new HashMap<>();
    private HashSet<String> blackList = new HashSet<>();
    private Socket socket;
    private ResponseListenerThread thread;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public synchronized void connect() throws IOException {
        socket = new Socket(IP, PORT);
        thread = new ResponseListenerThread();
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());

        thread.start();
        String response = thread.readResponse();
        if (!response.startsWith("hello")) {
            throw new BankException("Error in connecting to Bank");
        }
    }

    public synchronized String sendMessage(String message) {
        try {
            thread.resetResponse();
            outputStream.writeUTF(message);
            return thread.readResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public class ResponseListenerThread extends Thread {
        private String response;

        @Override
        public void run() {
            while (true) {
                try {
                    response = inputStream.readUTF();
                } catch (EOFException ex) {
                    response = "EOF";
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return;
                }
            }
        }

        public void resetResponse() {
            response = null;
        }

        public String readResponse() {
            while (response == null) {
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                }
            }
            return response;
        }
    }


    private boolean checkBruteForce(String client) {
        AtomicInteger numberOfAttempt = failedTries.get(client);
        if (numberOfAttempt == null || numberOfAttempt.get() <= MAXIMUM_BRUTE_FORCE_ATTACKS)
            return true;
        else {
            blackList.add(client);
            new Thread(() -> {
                try {
                    sleep(SECONDS_TO_REMAIN_ON_BLACKLIST * 1000);
                    blackList.remove(client);
                    failedTries.replace(client, new AtomicInteger(0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            return false;
        }
    }



    private boolean checkDOS(String client) {
        LocalDateTime firstConnection = connectionTime.get(client);
        if (firstConnection == null) return false;
        if (numberOfRequests.get(client) == null) return true;
        int requests = numberOfRequests.get(client).get();
        long seconds = Duration.between(firstConnection, LocalDateTime.now()).toSeconds();
        if ((requests / (seconds + 1)) <= MAXIMUM_REQUESTS_PER_SECOND)
            return true;
        else {
            blackList.add(client);
            new Thread(() -> {
                try {
                    sleep(SECONDS_TO_REMAIN_ON_BLACKLIST * 1000);
                    blackList.remove(client);
                    numberOfRequests.replace(client, new AtomicInteger(0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            return false;
        }
    }
}
