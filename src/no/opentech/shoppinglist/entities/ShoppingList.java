package no.opentech.shoppinglist.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: sokkalf
 * Date: 15.12.11
 * Time: 21:59
 */
public class ShoppingList implements Serializable, Parcelable {
    private long id;
    private String name;
    private String description;
    private Date created;
    private ArrayList<Item> items;
    private boolean defaultlist;
    private boolean allchecked;
    
    public ShoppingList() {

    }

    public ShoppingList(String name) {
        this.name = name;
    }
    
    public ShoppingList getDefaultList() {
        this.id = 0;
        this.name = "New list...";
        this.description = "Create a new list.";
        this.created = new Date();
        this.items = new ArrayList<Item>();
        this.defaultlist = true;
        return this;
    }
    
    public boolean allItemsChecked() {
        int numChecked = 0;
        for (Item item : items)
            if (item.isChecked()) numChecked++;

        return (numChecked == items.size());
    }

    public void removeCheckedItems() {
        for(int i=0; i<items.size(); i++)
            if(items.get(i).isChecked()) items.remove(i);
    }

    public boolean isDefaultList() {
        return defaultlist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel p, int flags) {
        p.writeLong(this.id);
        p.writeString(this.name);
        p.writeString(this.description);
        p.writeValue(this.created);
        p.writeTypedList(this.items);
    }

    public void readFromParcel(Parcel in) {
        this.setId(in.readLong());
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setCreated((Date) in.readValue(java.util.Date.class.getClassLoader()));
    }

    public static final Parcelable.Creator<ShoppingList> CREATOR = new Parcelable.Creator<ShoppingList>() {
        public ShoppingList createFromParcel(Parcel source) {
            final ShoppingList sl = new ShoppingList();
            sl.id = source.readLong();
            sl.name = source.readString();
            sl.description = source.readString();
            sl.created = (Date) source.readValue(java.util.Date.class.getClassLoader());
            return sl;
        }

        public ShoppingList[] newArray(int size) {
            throw new UnsupportedOperationException();
        }

    };

}
