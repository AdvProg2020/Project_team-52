package View;

import java.util.Optional;

public class MainMenu extends Menu {
    private static MainMenu menu;

    private MainMenu(String name) {
        super(name);
    }

    public static MainMenu getInstance(String name) {
        if (menu == null) {
            menu = new MainMenu(name);
        }
        return menu;
    }

    public static Menu getMenu() {
        return Optional.ofNullable(menu).orElseThrow(() -> new NullPointerException("getting null in MainMenu."));
    }

    public void accountAccess() {
        MenuManage.setLatestMenu(parentMenu);
    }

    public void openProductsArea() {
        MenuManage.setLatestMenu(ProductsMenu.getMenu());
    }

    public void openAuctionsArea() {
        MenuManage.setLatestMenu(OffMenu.getMenu());
    }

    @Override
    public void show() {
        System.out.println(
                "You're in MainMenu" + System.lineSeparator() +
                        "-------------------SubMenus-------------------" + System.lineSeparator() +
                        "1.AccountAccess" + System.lineSeparator() +
                        "2.ProductsArea" + System.lineSeparator() +
                        "3.AuctionsArea" + System.lineSeparator() +
                        "----------------------------------------------"
        );
    }


}
