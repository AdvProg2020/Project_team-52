package View;

import View.AccountMenu.CustomerMenu;
import View.AccountMenu.ManagerMenu;
import View.AccountMenu.SellerMenu;
import View.CustomerMenu.ViewCartForCustomer;
import View.CustomerMenu.ViewOrderForCustomer;
import View.LoginAndSigup.LogInMenu;
import View.LoginAndSigup.SignUpMenu;
import View.LoginAndSigup.UserMenu;
import View.ManagerMenu.ManageCategoryForManager;
import View.ManagerMenu.ManageProductForManager;
import View.ManagerMenu.ManageRequestForManager;
import View.SellerMenu.ManageProductForSeller;
import View.SellerMenu.ViewOffSeller;

import java.lang.reflect.Method;

public class Output {
    private static Output outPut = new Output();

    private MainMenu mainMenu = MainMenu.getInstance("mainMenu");
    private UserMenu userMenu = UserMenu.getInstance("userAreaMenu");
    private OffMenu offMenu = OffMenu.getInstance("auctionsMenu");
    private SignUpMenu signUpMenu = SignUpMenu.getInstance("signUpMenu");
    private CustomerMenu customerMenu =CustomerMenu.getInstance("buyerMenu");
    private CommentMenu commentMenu = CommentMenu.getInstance("commentProductMenu");
    private ProductActivityMenu productActivityMenu = ProductActivityMenu.getInstance("digestProductMenu");
    private LogInMenu logInMenu = LogInMenu.getInstance("logInMenu");
    private ManageCategoryForManager manageCategoryForManager=ManageCategoryForManager.getInstance("manageCategoryForManager");
    private ManageInfoMenu manageInfoMenu = ManageInfoMenu.getInstance("manageInfoMenu");
    private ManageProductForManager manageProductForManager = ManageProductForManager.getInstance("manageProductForManagerMenu");
    private ManageProductForSeller manageProductForSeller = ManageProductForSeller.getInstance("manageProductForSellerMenu");
    private ManageRequestForManager manageRequestForManager = ManageRequestForManager.getInstance("manageRequestsByManagerMenu");
    private ManagerMenu managerMenu = ManagerMenu.getInstance("ManagerMenu");
    private ProductMenu productMenu = ProductMenu.getInstance("productMenu");
    private ProductsMenu productsMenu = ProductsMenu.getInstance("productsMenu");
    private SellerMenu sellerMenu = SellerMenu.getInstance("sellerMenu");
    private ViewCartForCustomer viewCartForCustomer = ViewCartForCustomer.getInstance("viewCartByBuyerMenu");
    private ViewOffSeller viewOffsBySellerMenu = ViewOffSeller.getInstance("viewOffsBySellerMenu");
    private ViewOrderForCustomer viewOrderForCustomer = ViewOrderForCustomer.getInstance("viewOrdersByBuyerMenu");


    public static Output getInstance() {
        return outPut;
    }

    private Output() {
        this.setParents();
        this.setPatterns();
        this.setSetMethods();
    }

    public void handleCommand(String command) {

        try {

            Method patternToCommand = MenuManage.getLatestMenu().getClass().getMethod("patternToCommand", String.class);
            patternToCommand.invoke(MenuManage.getLatestMenu(), command);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPatterns() {
        setManagerMenuPattern();
        setLogInMenuPattern();
        setProductsMenuPattern();
        setSignUpPatterns();
        setSellerMenuPattern();
        setBuyerMenuPattern();
        setManageProductsByManageMenuPattern();
        setManageRequestsByManagerMenuPattern();
        setManageCategoriesByManagerMenuPattern();
        setManageInfoMenuPattern();
        setManageProductsBySellerMenuPattern();
        setViewOffsBySellerMenuPattern();
        setViewCartForCustomerMenuPattern();
        setViewOrderForCustomerMenuPattern();
        setDiscountsMenuPattern();
        setProductMenuPattern();
        setCommentProductMenuPattern();
        setMainMenuPattern();
        setUserAreaMenuPattern();
    }

    public void setSetMethods() {
        setMainMenuMethods();
        setUserAreaMenuMethods();
        setSignUpMethods();
        setLogInMenuMethods();
        setManagerMenuMethods();
        setCustomerMenuMethods();
        setManageInfoMenuMethods();
        setManageProductsByManagerMenuMethod();
        setViewOffsBySellerMenuMethods();
        setDiscountsMenuMethod();
        setProductsMenuMethod();
        setProductMenuMethod();
        setManageProductsBySellerMenuMethod();
        setCommentProductMenuMethod();
        setManageCategoriesByManagerMenuMethods();
        setViewCartForCustomerMenuMethods();
        setSellerMenuMethods();
        setManagerRequestsByManagerMenuMethods();
    }

    private void setUserAreaMenuPattern() {
        userMenu.addRegex("openLoginMenu")
                .addRegex("openSignUpMenu")
                .addRegex("EnterAsGuest")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }


    private void setUserAreaMenuMethods() {
        userMenu.addMethod("openLoginMenu")
                .addMethod("openSignUpMenu")
                .addMethod("EnterAsGuest")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setMainMenuPattern() {
        mainMenu.addRegex("openProductsArea")
                .addRegex("openAuctionsArea")
                .addRegex("accountAccess")
                .addRegex("exit")
                .addRegex("back")
                .addRegex("help")
                .setPatterns();
    }

    private void setMainMenuMethods() {
        mainMenu.addMethod("openProductsArea")
                .addMethod("openAuctionsArea")
                .addMethod("accountAccess")
                .addMethod("exit")
                .addMethod("back")
                .addMethod("help");
    }

    public void setSignUpPatterns() {
        signUpMenu.addRegex("createAccount (\\w+) (\\w+)")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    public void setSignUpMethods() {
        signUpMenu.addMethod("createAccount")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setLogInMenuPattern() {
        logInMenu.addRegex("login (\\w+) (\\w+)")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setLogInMenuMethods() {
        logInMenu.addMethod("login")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setManagerMenuPattern() {
        managerMenu.addRegex("openMainMenu")
                .addRegex("viewPersonalInfo")
                .addRegex("openManageUsersMenu")
                .addRegex("openManageProductsMenu")
                .addRegex("createDiscountCode")
                .addRegex("viewDiscountCode")
                .addRegex("openManageRequestsMenu")
                .addRegex("openManageCategoriesMenu")
                .addRegex("getDiscountCodeToRandomBuyer (\\d+)")
                .addRegex("getDiscountCodeToSpecialBuyers (\\d+)")
                .addRegex("logout")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setManagerMenuMethods() {
        managerMenu.addMethod("openMainMenu")
                .addMethod("viewPersonalInfo")
                .addMethod("openManageUsersMenu")
                .addMethod("openManageProductsMenu")
                .addMethod("createDiscountCode")
                .addMethod("viewDiscountCode")
                .addMethod("openManageRequestsMenu")
                .addMethod("openManageCategoriesMenu")
                .addMethod("getDiscountCodeToRandomBuyer")
                .addMethod("getDiscountCodeToSpecialBuyers")
                .addMethod("logout")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }




    private void setManageProductsByManageMenuPattern() {
        manageProductForManager.addRegex("remove (\\d+)")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setManageProductsByManagerMenuMethod() {
        manageProductForManager.addMethod("remove")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }



    private void setManageRequestsByManagerMenuPattern() {
        manageRequestForManager.addRegex("showDetails (\\d+)")
                .addRegex("acceptRequest (\\d+)")
                .addRegex("declineRequest (\\d+)")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setManagerRequestsByManagerMenuMethods() {
        manageRequestForManager.addMethod("showDetails")
                .addMethod("acceptRequest")
                .addMethod("declineRequest")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setManageCategoriesByManagerMenuPattern() {
        manageCategoryForManager.addRegex("edit (\\w+)")
                .addRegex("add (\\w+)")
                .addRegex("remove (\\w+)")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setManageCategoriesByManagerMenuMethods() {
        manageCategoryForManager.addMethod("editCategory")
                .addMethod("addCategory")
                .addMethod("removeCategory")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    public void setSellerMenuPattern() {
        sellerMenu.addRegex("openMainMenu")
                .addRegex("viewPersonalInfo")
                .addRegex("viewCompanyInformation")
                .addRegex("viewSalesHistory")
                .addRegex("manageProducts")
                .addRegex("addProduct")
                .addRegex("removeProduct (\\d+)")
                .addRegex("showCategories")
                .addRegex("viewOffs")
                .addRegex("viewBalance")
                .addRegex("logout")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    public void setSellerMenuMethods() {
        sellerMenu.addMethod("openMainMenu")
                .addMethod("viewPersonalInfo")
                .addMethod("viewCompanyInformation")
                .addMethod("viewSalesHistory")
                .addMethod("manageProducts")
                .addMethod("addProduct")
                .addMethod("removeProduct (\\d+)")
                .addMethod("showCategories")
                .addMethod("viewOffs")
                .addMethod("viewBalance")
                .addMethod("logout")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setManageInfoMenuPattern() {
        manageInfoMenu.addRegex("edit (\\w+)")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setManageInfoMenuMethods() {
        manageInfoMenu.addMethod("edit")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setManageProductsBySellerMenuPattern() {
        manageProductForSeller.addRegex("view (\\d+)")
                .addRegex("viewBuyers (\\d+)")
                .addRegex("edit (\\d+)")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();

    }

    private void setManageProductsBySellerMenuMethod() {
        manageProductForSeller.addMethod("view (\\d+)")
                .addMethod("viewBuyers (\\d+)")
                .addMethod("edit (\\d+)")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }


    private void setViewOffsBySellerMenuPattern() {
        viewOffsBySellerMenu.addRegex("viewOff (\\d+)")
                .addRegex("editOff (\\d+)")
                .addRegex("addOff")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setViewOffsBySellerMenuMethods() {
        viewOffsBySellerMenu.addMethod("viewOff")
                .addMethod("editOff")
                .addMethod("addOff")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setBuyerMenuPattern() {
        customerMenu.addRegex("openMainMenu")
                .addRegex("viewPersonalInfo")
                .addRegex("viewCart")
                .addRegex("viewBalance")
                .addRegex("viewDiscountCodes")
                .addRegex("viewOrders")
                .addRegex("logout")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .addRegex("charge Account (\\d+)")
                .setPatterns();
    }

    private void setCustomerMenuMethods() {
        customerMenu.addMethod("openMainMenu")
                .addMethod("viewPersonalInfo")
                .addMethod("viewCart")
                .addMethod("viewBalance")
                .addMethod("viewDiscountCodes")
                .addMethod("viewOrders")
                .addMethod("logout")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back")
                .addMethod("chargeAccount");
    }

    private void setViewCartForCustomerMenuPattern() {
        viewCartForCustomer.addRegex("show products")
                .addRegex("viewProduct (\\d+)")
                .addRegex("increase (\\d+)")
                .addRegex("decrease (\\d+)")
                .addRegex("show total price")
                .addRegex("purchase")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setViewCartForCustomerMenuMethods() {
        viewCartForCustomer.addMethod("showProducts")
                .addMethod("viewProduct")
                .addMethod("increase")
                .addMethod("decrease")
                .addMethod("showTotalPrice")
                .addMethod("purchase")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setViewOrderForCustomerMenuPattern() {
        viewOrderForCustomer.addRegex("showOrder (\\d+)")
                .addRegex("rate (\\d+) [1-5]")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setViewOrderForCustomerMethod() {
        viewOrderForCustomer.addMethod("showOrder (\\d+)")
                .addMethod("rate (\\d+) [1-5]")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }



    private void setProductsMenuPattern() {
        productsMenu.addRegex("viewCategories")
                .addRegex("showProducts")
                .addRegex("showProduct (\\d+)")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setProductsMenuMethod() {
        productsMenu.addMethod("viewCategories")
                .addMethod("showProducts")
                .addMethod("showProduct")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }


    private void setProductMenuPattern() {
        productMenu.addRegex("digest")
                .addRegex("attributes")
                .addRegex("compare (\\w+)")
                .addRegex("Comments")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setProductMenuMethod() {
        productMenu.addMethod("digest")
                .addMethod("attributes")
                .addMethod("compare (\\w+)")
                .addMethod("Comments")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setProductActivityMenuPattern() {
        productActivityMenu.addRegex("addToCart")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setProductActivityMenuMethods() {
        productActivityMenu.addMethod("addToCart")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setCommentProductMenuPattern() {
        commentMenu.addRegex("Add comment")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setCommentProductMenuMethod() {
        commentMenu.addMethod("Add comment")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    private void setDiscountsMenuPattern() {
        offMenu.addRegex("offs")
                .addRegex("showProduct (\\d+)")
                .addRegex("exit")
                .addRegex("help")
                .addRegex("back")
                .setPatterns();
    }

    private void setDiscountsMenuMethod() {
        offMenu.addMethod("offs")
                .addMethod("showProduct")
                .addMethod("exit")
                .addMethod("help")
                .addMethod("back");
    }

    public void setParents() {
        userMenu.setParentMenu(userMenu);
        mainMenu.setParentMenu(userMenu);
        signUpMenu.setParentMenu(userMenu);
        logInMenu.setParentMenu(userMenu);
        customerMenu.setParentMenu(logInMenu);
        manageInfoMenu.setParentMenu(mainMenu);
        viewCartForCustomer.setParentMenu(customerMenu);
        viewOrderForCustomer.setParentMenu(customerMenu);
        sellerMenu.setParentMenu(logInMenu);
        manageProductForSeller.setParentMenu(sellerMenu);
        viewOffsBySellerMenu.setParentMenu(sellerMenu);
        managerMenu.setParentMenu(logInMenu);
        manageCategoryForManager.setParentMenu(managerMenu);
        manageProductForManager.setParentMenu(managerMenu);
        manageRequestForManager.setParentMenu(managerMenu);
        productsMenu.setParentMenu(mainMenu);
        productMenu.setParentMenu(productsMenu);
        commentMenu.setParentMenu(productMenu);
        productActivityMenu.setParentMenu(productMenu);
        offMenu.setParentMenu(mainMenu);
    }
}
