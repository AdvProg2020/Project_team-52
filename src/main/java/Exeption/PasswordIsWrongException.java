package Exeption;

public class PasswordIsWrongException extends Exception {

    public PasswordIsWrongException(String message) {
        super(message);
    }
}
