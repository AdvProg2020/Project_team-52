package Bank;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class BankApiSocket {
    public static final int PORT = 2222;
    public static final String IP = "127.0.0.1";

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
}
