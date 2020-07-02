package View;
import Controller.ManagerController;
import Exception.*;

import java.util.List;
import java.util.Optional;

public class ManageInfoMenu extends Menu {
    private static ManageInfoMenu menu;
    private static ManagerController managerController = ManagerController.getInstance();

    public ManageInfoMenu(String name) {
        super(name);
    }

    public static ManageInfoMenu getInstance(String name) {
        if (menu == null) {
            menu = new ManageInfoMenu(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in ManageInfoMenu."));
    }

    public void edit(List<String> inputs) {
        String fieldName = inputs.get(0);
        System.out.println("Enter a new value: ");
        String newField = scanner.nextLine();
        try {
            managerController.editField(fieldName, newField);
            System.out.println(fieldName + " changed.");
        } catch (FieldDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println("You're in ManageInfoMenu");
    }

    @Override
    public void help() {
        super.help();
        System.out.println(
                "edit [fieldName] : To edit a field" + System.lineSeparator() +
                        "----------------------------------------------"
        );
    }

}
