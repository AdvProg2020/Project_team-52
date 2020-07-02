package Controller;

import Controller.Interface.RegisterAndLogin;
import Model.Account.Account;
import Exception.*;

public class LoginController {
    private static ControllerSection controllerSection = ControllerSection.getInstance();

    private static LoginController loginController = new LoginController();

    public Account login(String username, String password) throws PasswordInvalidException, UserNameInvalidException, UserNameTooShortException, AccountDoesNotExistException {

        RegisterAndLogin.RegisterValidation checkUsername = RegisterAndLogin
                .isUsername(username).get();

        switch (checkUsername) {
            case IS_NOT_A_VALID_USERNAME_TOO_SHORT:
                throw new UserNameTooShortException("Username is too short.");
            case IS_NOT_A_VALID_USERNAME_CHAR:
                throw new UserNameInvalidException("Username is invalid.");
        }

        Account account = Account.getAccountByUserName(username);

        RegisterAndLogin.RegisterValidation checkPassword = RegisterAndLogin.isCorrectPassword(password, account).get();

        if (checkPassword == RegisterAndLogin.RegisterValidation.IS_NOT_A_VALID_PASS_INCORRECT) {
            throw new PasswordInvalidException("Password is incorrect.");
        }

        controllerSection.setAccount(account);

        return account;
    }

    public static LoginController getInstance() {
        return loginController;
    }

    private LoginController() {
    }
}
