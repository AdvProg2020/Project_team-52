package View.SellerMenu;

import Controller.SellerController;
import Model.Off;
import View.Interface.Shows;
import View.Menu;
import Exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ViewOffSeller extends Menu {
    private static ViewOffSeller menu;

    public ViewOffSeller(String name) {
        super(name);
    }

    private static SellerController sellerController = SellerController.getInstance();

    public static ViewOffSeller getInstance(String name) {
        if (menu == null) {
            menu = new ViewOffSeller(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in ViewOffsBySellerMenu."));
    }

    public void viewOff(List<String> inputs) {
        String id = inputs.get(0);
        try {
            System.out.println(
                    Shows.getShowOff().apply(sellerController.viewOff(id))
            );
        } catch (OffDoesNotExistException e) {
            System.out.println(e.getMessage());
        }

    }

    public void editOff(List<String> inputs) {
        String auctionId = inputs.get(0);

        System.out.println("Enter field name:");
        String fieldName = scanner.nextLine();
        System.out.println("Enter new value:");
        String newInfo = scanner.nextLine();

        System.out.println("Enter information for request: ");
        String information = scanner.nextLine();

        try {
            sellerController.editAuction(auctionId, fieldName, newInfo, information);
            System.out.println("Auction changed.");
        } catch (OffDoesNotExistException | FieldDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addOff() {
        System.out.println("Enter information in this pattern :" + System.lineSeparator() +
                "AuctionInfo :[auctionName] :[Start] :[End] :[percent] :[maxAmount]"
        );
        Matcher matcher = Pattern.compile("^AuctionInfo :(.+) :(.+) :(.+) :(.+) :(.+)$").matcher(scanner.nextLine().trim());
        if (!matcher.find()) {
            System.out.println("Incorrect format.");
            return;
        }
        try {

            Off off = sellerController.addOff(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));

            List<String> stringIds = new ArrayList<>();
            System.out.println("Enter product ids(or finish):");
            while (true) {
                System.out.println("Product id:");
                String strId = scanner.nextLine();
                if (strId.matches("finish")) break;
                stringIds.add(strId);
            }

            sellerController.addProductsToOff(off, stringIds);

            System.out.println("Enter information for request: ");
            String information = scanner.nextLine();

            sellerController.sendRequest(off, information, "new");

            System.out.println("Auction created.");

        } catch (NumberFormatException | DateTimeParseException |  ProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println("You're in ViewOffsBySellerMenu");
    }

}
