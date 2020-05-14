package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Off {

    private int id;
    private boolean isLoaded;
    private Date startDate;
    private Date endDate;
    private List<OffItem> items;

    public Off() {
        this.isLoaded = false;
        items = new ArrayList<OffItem>();
    }

    public int getId() {
        return id;
    }


    public boolean isLoaded() {
        return isLoaded;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<OffItem> getItems() {
        return items;
    }

    public OffItem getItemByProductId(int id) {
        for (OffItem item : items) {
            if (item.getProduct().getId() == id)
                return item;
        }
        return null;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof Off))
            return false;
        Off off = (Off) obj;
        if (off.getId() == this.getId())
            return true;
        return false;

    }
}
