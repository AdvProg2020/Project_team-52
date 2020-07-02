package Controller;

import Controller.Interface.RegisterAndLogin;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Category;
import Model.Field.Field;
import Model.FieldList;
import Exception.*;
import Model.Product;
import Model.Request;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerController extends AccountController {
    private static ManagerController managerController = new ManagerController();

    public static ManagerController getInstance() {
        return managerController;
    }


    public List<Request> showAllRequests() {
        return Request.getList();
    }

    public List<Category> showAllCategories() {
        return Category.getList();
    }

    public Account viewAccount(String username) throws AccountDoesNotExistException {
        return Account.getAccountByUserName(username);
    }



    public void removeProduct(String strProductId) throws ProductDoesNotExistException {
        long productId = Long.parseLong(strProductId);
        Product product = Product.getProductById(productId);
        Product.removeProduct(product);
    }




    public Request detailsOfRequest(String strRequestId) throws RequestDoesNotExistException {
        long requestId = Long.parseLong(strRequestId);
        return Request.getRequestById(requestId);
    }

    public void acceptRequest(String strRequestId) throws RequestDoesNotExistException, AccountDoesNotExistException {
        long requestId = Long.parseLong(strRequestId);
        ((Manager) controllerUnit.getAccount()).acceptRequest(Request.getRequestById(requestId));
    }

    public void denyRequest(String strRequestId) throws RequestDoesNotExistException, AccountDoesNotExistException {
        long requestId = Long.parseLong(strRequestId);
        ((Manager) controllerUnit.getAccount()).declineRequest(Request.getRequestById(requestId));
    }

    public void editCategory(String categoryId, String fieldName, String newField) throws FieldDoesNotExistException, CategoryDoesNotExistException, NumberFormatException {
        long id = Long.parseLong(categoryId);
        Category category = Category.getCategoryById(id);
        category.editField(fieldName, newField);
    }

    public void removeCategory(String categoryId) throws CategoryDoesNotExistException {
        long id = Long.parseLong(categoryId);
        Category category = Category.getCategoryById(id);
        Category.removeCategory(category);
    }

    public Category createEmptyCategory(String categoryName, List<String> fieldNames, List<String> strCategoriesIds) throws CategoryDoesNotExistException, NumberFormatException {

        List<Long> subCategory = strCategoriesIds.stream().map(Long::parseLong).collect(Collectors.toList());

        for (long aLong : subCategory) {
            Category.checkExistOfCategoryById(aLong);
        }

        List<Field> fields = fieldNames.stream().map(Field::new).collect(Collectors.toList());

        FieldList fieldList = new FieldList(fields);

        Category category = new Category(categoryName, fieldList, subCategory);

        Category.addCategory(category);

        return category;
    }

    public Manager createManagerProfileBaseAccount(String username) throws UserNameInvalidException, UserNameTooShortException, ThisUserNameAlreadyExistsException {
        RegisterAndLogin.RegisterValidation registerValidation = RegisterAndLogin.isUsername(username).get();

        switch (registerValidation) {
            case IS_NOT_A_VALID_USERNAME_CHAR:
                throw new UserNameInvalidException("UserNameInvalidException");
            case IS_NOT_A_VALID_USERNAME_TOO_SHORT:
                throw new UserNameTooShortException("UserNameTooShortException");
        }

        if (Account.isThereAnyAccountWithThisUsername(username) || Account.isThereAnyInRegisteringWithThisUsername(username)) {
            throw new ThisUserNameAlreadyExistsException("The username: " + username + " already exist.");
        }

        Manager manager = new Manager(username);
        Account.addToInRegisteringList(manager);
        return manager;
    }
}
