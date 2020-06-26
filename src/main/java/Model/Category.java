package Model;

import Model.DataBase.DataBase;
import Model.Field.SingleString;
import Model.Interface.AddingNew;
import Model.Interface.Packable;

import java.util.ArrayList;
import java.util.List;

public class Category implements Packable<Category>, Cloneable{
    private static List<Category> list;

    private long categoryId;
    private String name;
    private FieldList categoryFields;
    private List<Long> subCategories;
    private List<Long> productList = new ArrayList<>();


    public static void setList(List<Category> list) {
        Category.list = list;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }



    public long getId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public FieldList getCategoryFields() {
        return categoryFields;
    }

    public List<Long> getProductList() {
        return productList;
    }

    public List<Long> getSubCategories() {
        return subCategories;
    }


    public static List<Category> getList() {
        return list;
    }

    public void addToProductList(long productId) {
        productList.add(productId);
        DataBase.save(this);
    }

    public void removeFromProductList(long productId) {
        productList.removeIf(pro -> productId == pro );
        DataBase.save(this);
    }

    public void addToSubCategoryList(long categoryId) {
        subCategories.add(categoryId);
        DataBase.save(this);
    }

    public static void addCategory(Category category) {
        category.setCategoryId(AddingNew.getRegisteringId().apply(Category.getList()));
        list.add(category);
        DataBase.save(category, true);
    }

    public static void removeCategory(Category category) {
        list.removeIf(cat -> category.getId() == cat.getId());
        DataBase.remove(category);
    }

    public static Category getCategoryById(long id) throws CategoryDoesNotExistException {
        return list.stream()
                .filter(category -> id == category.getId())
                .findFirst()
                .orElseThrow(() -> new CategoryDoesNotExistException(
                        "Category with the id:" + id + " does not exist in list of all categories."
                ));
    }

    public static void checkExistOfCategoryById(long id) throws CategoryDoesNotExistException {
        if (list.stream().noneMatch(category -> id == category.categoryId)) {
            throw new CategoryDoesNotExistException(
                    "Category with the id:" + id + " does not exist in list of all categories."
            );
        }
    }

    public void editField( String fieldName, String value) throws FieldDoesNotExistException {

        if (fieldName.equals("name")) {
            setName(value);
        } else {
            SingleString field = (SingleString) categoryFields.getFieldByName(fieldName);
            field.setString(value);
        }
    }


    public Category(String name, FieldList categoryFields, List<Long> subCategories) {
        this.name = name;
        this.categoryFields = categoryFields;
        this.subCategories = subCategories;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", categoryField=" + categoryFields +
                ", subCategoryList=" + subCategories +
                '}';
    }


}
