package Model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class Sorter {

    private Comparator<Product> comparator;

    private static Comparator<Product> timeComparator = (o1, o2) -> {
        LocalDate d1 = o1.getProductInfo().getUploadDate();
        LocalDate d2 = o2.getProductInfo().getUploadDate();
        return d1.compareTo(d2);
    };

    private static Comparator<Product> pointComparator = (o1, o2) -> {
        double s1 = o1.getAverageScore();
        double s2 = o2.getAverageScore();
        return Double.compare(s1, s2);
    };



    private static Comparator<Product> defaultComparator = (o1, o2) -> 0;

    public static Comparator<Product> getDefaultComparator() {
        return defaultComparator;
    }

    public static Comparator<Product> getVisitorComparator() {
        return visitorComparator;
    }

    public static Comparator<Product> getPointComparator() {
        return pointComparator;
    }

    public static Comparator<Product> getTimeComparator() {
        return timeComparator;
    }

    public void sorted(List<Product> productList) {
        productList.sort(comparator);
    }

    public Sorter(Comparator<Product> comparator) {
        this.comparator = comparator;
    }
}
