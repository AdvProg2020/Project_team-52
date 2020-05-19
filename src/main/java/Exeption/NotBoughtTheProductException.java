package Exeption;


    public class NotBoughtTheProductException extends Exception {

        public NotBoughtTheProductException(String message) {
            super(message);
        }
    }
}
