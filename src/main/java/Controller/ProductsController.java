package Controller;

import Model.Category;
import Model.Product;
import Model.Sorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Filter;

public class ProductsController {  public enum SortElement {

    TIME(new Sorter(Sorter.getTimeComparator()),"Sort by upload time"),
    POINT(new Sorter(Sorter.getPointComparator()),"Sort by point"),
    NUMBER_OF_VISITS(new Sorter(Sorter.getVisitorComparator()),"Sort by number of visitors"),
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

    public List<Product> sort( String sortElement) throws NotAvailableSortException {

        switch (sortElement) {
            case "Time":
                this.sortElement = SortElement.TIME;
                break;
            case "Point":
                this.sortElement = SortElement.POINT;
                break;
            case "NumberOfVisits":
                this.sortElement = SortElement.NUMBER_OF_VISITS;
                break;
            case "Default":
                this.sortElement = SortElement.DEFAULT;
                break;
            default:
                throw new NotAvailableSortException("this sort isn't an available Sort.");
        }
        this.sortElement.getSorter().sorted(productList);
        return productList;
    }

    public List<Product> disableSort() {
        sortElement = SortElement.NUMBER_OF_VISITS;
        return productList;
    }

    public Product showProduct(String productIdString) throws ProductDoesNotExistException , NumberFormatException {
        long id = Long.parseLong(productIdString);
        Product product = Product.getProductById(id);
        controllerSection.setProduct(product);
        return product;
    }


}
