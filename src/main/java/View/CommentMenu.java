package View;

import Controller.ProductController;
import Exception.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentMenu extends Menu {
    private static CommentMenu menu;
    private static ProductController productController = ProductController.getInstance();
    private CommentMenu(String name) {
        super(name);
    }

    public static CommentMenu getInstance(String name) {
        if (menu == null) {
            menu = new CommentMenu(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in CommentProductMenu."));
    }

    public void addComment() {

        System.out.println("Enter information in this pattern :" + System.lineSeparator() +
                "Title:[title of your comment] Content:[content of your comment]" + System.lineSeparator() +
                "exit : to finish."
        );
        String input = scanner.nextLine().trim();

        Matcher matcher = Pattern.compile("Title:(.+) Content:(.+)").matcher(input);
        if (!matcher.find()) {
            System.out.println("Incorrect format");
        }
        try {
            productController.addComment(matcher.group(1), matcher.group(2));
        } catch (ProductDoesNotExistException | CannotRateException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public void show() {
        System.out.println(" comment menu ");
    }
}
