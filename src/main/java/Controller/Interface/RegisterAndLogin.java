package Controller.Interface;

import Model.Account.Account;

import java.util.function.Supplier;

public interface RegisterAndLogin extends Supplier<RegisterAndLogin.RegisterValidation> {

    static RegisterAndLogin isFirstName(String name) {
        return () -> name.matches("^([a-z A-Z]+)$")
                ? RegisterValidation.IS_VALID : RegisterValidation.IS_NOT_A_VALID_FIRST_NAME;
    }

    static RegisterAndLogin isLastName(String name) {
        return () -> name.matches("^([a-z A-Z]+)$")
                ? RegisterValidation.IS_VALID : RegisterValidation.IS_NOT_A_VALID_LAST_NAME;
    }

    static RegisterAndLogin isPhoneNumber(String numb) {
        return () -> numb.matches("^(\\d{11})$")
                ? RegisterValidation.IS_VALID : RegisterValidation.IS_NOT_A_VALID_NUMB;
    }

    static RegisterAndLogin isBrand(String brand) {
        return () -> brand.matches("^(.+)$")
                ? RegisterValidation.IS_VALID : RegisterValidation.IS_NOT_A_VALID_BRAND;
    }

    static RegisterAndLogin isEmail(String email) {
        return () -> email.matches("^(.+)@(gmail|yahoo)(.+)$")
                ? RegisterValidation.IS_VALID : RegisterValidation.IS_NOT_A_VALID_EMAIL;
    }

    static RegisterAndLogin isUsername(String user) {
        return () -> user.matches("^(\\w+)$") ?
                user.matches("^(\\w{6}\\w*)$") ?
                        RegisterValidation.IS_VALID : RegisterValidation.IS_NOT_A_VALID_USERNAME_TOO_SHORT
                : RegisterValidation.IS_NOT_A_VALID_USERNAME_CHAR;
    }

    static RegisterAndLogin isPassword(String pass) {
        return () -> pass.matches("^(\\w+)$")
                ? RegisterValidation.IS_VALID : RegisterValidation.IS_NOT_A_VALID_PASS;
    }

    static RegisterAndLogin isCorrectPassword(String pass, Account account) {
        return () -> pass.equals(account.getPassword())
                ? RegisterValidation.IS_VALID : RegisterValidation.IS_NOT_A_VALID_PASS_INCORRECT;
    }

    default RegisterAndLogin and(RegisterAndLogin after) {
        return () -> {
            RegisterValidation registerValidation = this.get();
            return registerValidation == RegisterValidation.IS_VALID ? after.get() : registerValidation;
        };
    }

    public enum RegisterValidation {
        IS_NOT_A_VALID_EMAIL, IS_NOT_A_VALID_FIRST_NAME,
        IS_NOT_A_VALID_LAST_NAME, IS_NOT_A_VALID_NUMB,
        IS_NOT_A_VALID_BRAND , IS_NOT_A_VALID_USERNAME_CHAR,
        IS_NOT_A_VALID_USERNAME_TOO_SHORT, IS_NOT_A_VALID_PASS,
        IS_NOT_A_VALID_PASS_INCORRECT , IS_VALID
    }
}
