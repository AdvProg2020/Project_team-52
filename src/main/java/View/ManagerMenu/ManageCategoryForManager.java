package View.ManagerMenu;

import Controller.ManagerController;
import View.Menu;
import View.SellerMenu.ManageProductForSeller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageCategoryForManager extends Menu {
    private static ManageProductForSeller menu;
    private static ManagerController managerController = ManagerController.getInstance();

    public ManageProductForSeller(String name) {
        super(name);
    }

    public static ManageProductForSeller getInstance(String name) {
        if (menu == null) {
            menu = new ManageProductForSeller(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in ManageCategoriesByManagerMenu."));
    }

    public void editCategory(List<String> inputs) {
        String categoryId = inputs.get(0);

        System.out.print("FieldName: ");
        String field = scanner.nextLine();

        System.out.print("FieldValue: ");
        String newField = scanner.nextLine();

        try {
            managerController.editCategory(categoryId, field, newField);
        } catch (CategoryDoesNotExistException | FieldDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCategory(List<String> inputs) {
        String categoryName = inputs.get(0);
        try {

            List<String> fieldNames = new ArrayList<>();
            while (true) {
                System.out.println("Enter fieldName( or finish.):");
                String fieldName = scanner.nextLine();
                if (fieldName.matches("finish")) break;
                fieldNames.add(fieldName);
            }

            List<String> subCategories = new ArrayList<>();
            while (true) {
                System.out.println("Enter subCategory id( or finish.):");
                String categoryField = scanner.nextLine();
                if (categoryField.matches("finish")) break;
                subCategories.add(categoryField);
            }

            managerController.createEmptyCategory(categoryName, fieldNames, subCategories);

            System.out.println("Category created.");

        } catch (CategoryDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeCategory(List<String> inputs) {
        String categoryName = inputs.get(0);
        try {
            managerController.removeCategory(categoryName);
            System.out.println("Category removed.");
        } catch (CategoryDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println("You're in ManageCategoriesByManagerMenu.");
    }
}
