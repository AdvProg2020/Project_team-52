package Controller;

import Model.Account.Account;
import Model.Info;

import java.time.format.DateTimeFormatter;

public class AccountController {
    protected ControllerSection controllerUnit = ControllerSection.getInstance();

    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");



    public void editField(String fieldName, String newField) throws FieldDoesNotExistException {
        Account account = controllerUnit.getAccount();
        account.editField(fieldName,newField);
    }

    public Info viewPersonalInfo()  {
        return controllerUnit.getAccount().getPersonalInfo();
    }
}
