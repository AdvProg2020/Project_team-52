package Controller;

import Model.Category;
import Model.Product;
import Model.Sorter;
import Exception.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Filter;

public class ProductsController {


    public enum SortElement {

    TIME(new Sorter(Sorter.getTimeComparator()),"Sort by upload time"),
    POINT(new Sorter(Sorter.getPointComparator()),"Sort by point"),
    DEFAULT(new Sorter(Sorter.getDefaultComparator()),"default sort");

    Sorter sorter;
    String info;

    SortElement(Sorter sorter, String info) {
        this.sorter = sorter;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public Sorter getSorter() {
        return sorter;
    }
}

    private SortElement sortElement = SortElement.DEFAULT;

    private static List<Product> productList = new ArrayList<>(Product.getList());

    private static ProductsController productsController = new ProductsController();

    private static ControllerSection controllerSection = ControllerSection.getInstance();

    public List<Category> viewCategories() {
        return Category.getList();
    }

    public String showAvailableSorts() {
        return "The available sort elements are : \"Time\" or \"Point\" or \"NumberOfVisits\" or \"Default\"";
    }

    public SortElement currentSort() {
        return sortElement;
    }

    public List<Product> showProducts() {

        List<Product> productList = new ArrayList<>(Product.getList());
        currentSort().getSorter().sorted(productList);

        return productList;
    }


    public List<Product> disableSort() {
        return productList;
    }

    public static ProductsController getInstance() {
        return productsController;
    }

    public Product showProduct(String productIdString) throws ProductDoesNotExistException , NumberFormatException {
        long id = Long.parseLong(productIdString);
        Product product = Product.getProductById(id);
        controllerSection.setProduct(product);
        return product;
    }


}
