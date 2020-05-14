package Model;
import view.products.all.AllProductView;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;
    private String name;
    private List<CategoryFeature> features;
    private Category parent;
    private List<Category> subCategory;
    private List<Product> products;

    public Category(String name) {
        this.name = name;
        features = new ArrayList<CategoryFeature>();
        subCategory = new ArrayList<Category>();
        products = new ArrayList<Product>();
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public List<CategoryFeature> getFeatures() {
        return features;
    }

    public List<Category> getSubCategory() {
        return subCategory;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getCategoriesAndSub() {
        StringBuilder branch = new StringBuilder();
        attachParents(branch, this);
        return branch.toString();
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void addSubCategory(Category category) {

    }

    public void removeSubCategory(Category category) {

    }

    private void attachParents(StringBuilder branch, Category category) {
        branch.append(category.getName());
        if (category.parent == null) {
            return;
        }
        attachParents(branch, category.getParent());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if((obj instanceof Category))
            return false;
        Category category = (Category) obj;
        if(category.getId() == this.getId())
            return true;
        return false;
    }

    @Override
    public Category clone() {
        Category category = new Category(this.name);
        category.setId(this.id);
        this.subCategory.forEach(i -> category.subCategory.add(i));
        this.products.forEach(i -> category.products.add(i));
        this.features.forEach(i -> category.features.add(i));
        return category;
    }
}
