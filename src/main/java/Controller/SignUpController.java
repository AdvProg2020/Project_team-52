package Controller;

import Controller.Interface.RegisterAndLogin;
import Model.*;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Field.Field;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class SignUpController {
    private static SignUpController registerController = new SignUpController();

    /**************************************************MainMethods******************************************************/

    public void finishRegistering(Account account) {
        Account.removeFromInRegistering(account);
    }

    public Account creatTheBaseOfAccount(String type, String username) throws UserNameInvalidException, UserNameTooShortException, TypeInvalidException, CanNotCreatMoreThanOneMangerBySignUp, ThisUserNameAlreadyExistsException {

        RegisterAndLogin.RegisterValidation registerValidation = RegisterAndLogin.isUsername(username).get();

        switch (registerValidation) {
            case IS_NOT_A_VALID_USERNAME_CHAR:
                throw new UserNameInvalidException("Username is invalid.");
            case IS_NOT_A_VALID_USERNAME_TOO_SHORT:
                throw new UserNameTooShortException("Username is too short.");
        }

        if (Account.isThereAnyAccountWithThisUsername(username) || Account.isThereAnyInRegisteringWithThisUsername(username)) {
            throw new ThisUserNameAlreadyExistsException("The username: " + username + " already exist.");
        }

        Account account;

        switch (type) {
            case "Seller":
                account = new Seller(username);
                break;
            case "Manager":
                if (Manager.isThereAnyManager()) {
                    throw new CanNotCreatMoreThanOneMangerBySignUp("Just one manager in signUp menu can create.");
                }
                account = new Manager(username);
                break;
            case "Customer":
                account = new Customer(username);
                break;
            default:
                throw new TypeInvalidException(type + " isn't a valid type. just 'Manager' , 'Seller' , 'Customer' are valid.");
        }

        Account.addToInRegisteringList(account);

        return account;
    }

    public void creatPasswordForAccount(Account account, String password) throws PasswordInvalidException {

        RegisterAndLogin.RegisterValidation registerValidation = RegisterAndLoginValidator.isPassword(password).get();

        if (registerValidation == RegisterAndLogin.RegisterValidation.IS_NOT_A_VALID_PASS) {
            throw new PasswordInvalidException("Your entered password: " + password + " is Invalid. Just enter (a-z),(A_Z),(_).");
        }

        account.setPassword(password);
    }

    public void savePersonalInfo(Account account, String firstName, String lastName, String phoneNumber, String email) throws FirstNameInvalidException, LastNameInvalidException, EmailInvalidException, PhoneNumberInvalidException {

        RegisterAndLogin.RegisterValidation registerValidation = RegisterAndLogin.isFirstName(firstName)
                .and(RegisterAndLogin.isLastName(lastName))
                .and(RegisterAndLogin.isEmail(email))
                .and(RegisterAndLogin.isPhoneNumber(phoneNumber)).get();

        switch (registerValidation) {
            case IS_NOT_A_VALID_NUMB:
                throw new PhoneNumberInvalidException("Phone number is invalid.");
            case IS_NOT_A_VALID_FIRST_NAME:
                throw new FirstNameInvalidException("First name is invalid.");
            case IS_NOT_A_VALID_LAST_NAME:
                throw new LastNameInvalidException("Last name is invalid.");
            case IS_NOT_A_VALID_EMAIL:
                throw new EmailInvalidException("Email is invalid.");
        }

        FieldList personalInfo = new FieldList(new ArrayList<>(Arrays.asList(
                new Field("FirstName", firstName),
                new Field("LastName", lastName),
                new Field("Email", email),
                new Field("PhoneNumber", phoneNumber))
        ));

        Info info = new Info(account.getClass().getSimpleName(), personalInfo, LocalDate.now());

        account.setPersonalInfo(info);

        if ( account instanceof Manager) {
            Account.addAccount(account);
            finishRegistering(account);
        }

        if ( account instanceof Customer) {
            ((Customer) account).setCart(Cart.autoCreateCart());
            Account.addAccount(account);
            finishRegistering(account);
        }
    }

    public void saveCompanyInfo(Account account, String brand, String phoneNumber, String email) throws CompanyNameInvalidException, PhoneNumberInvalidException, EmailInvalidException {

        RegisterAndLogin.RegisterValidation registerValidation = RegisterAndLogin.isBrand(brand)
                .and(RegisterAndLogin.isEmail(email))
                .and(RegisterAndLogin.isPhoneNumber(phoneNumber)).get();

        switch (registerValidation) {
            case IS_NOT_A_VALID_NUMB:
                throw new PhoneNumberInvalidException("Phone number is invalid.");
            case IS_NOT_A_VALID_BRAND:
                throw new CompanyNameInvalidException("this Company name is invalid.");
            case IS_NOT_A_VALID_EMAIL:
                throw new EmailInvalidException("Email is invalid.");
        }

        FieldList companyInfo = new FieldList(new ArrayList<>(Arrays.asList(
                new Field("CompanyName", brand),
                new Field("CompanyPhoneNumber", phoneNumber),
                new Field("CompanyEmail", email))
        ));

        Info info = new Info(account.getClass().getSimpleName(), companyInfo, LocalDate.now());

        ((Seller)account).setCompanyInfo(info);

        finishRegistering(account);

        Account.addAccount(account);
    }


    public static SignUpController getInstance() {
        return registerController;
    }
}
