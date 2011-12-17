/*
 * ShoppingList for Android
 * (C)2011 by Christian Lønaas
 *    <christian dot lonaas at discombobulator dot org>
 *
 * This file is part of ShoppingList.
 *
 * ShoppingList is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ShoppingList is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ShoppingList.  If not, see <http://www.gnu.org/licenses/>.
 *
 */


package no.opentech.shoppinglist.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by: Christian Lønaas
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
    
    public void hideCheckedItems() {
        for(Item i : items)
            if(i.isChecked()) i.setHidden(true);
    }
    
    public ArrayList<Item> getHiddenItems() {
        ArrayList<Item> hiddenItems = new ArrayList<Item>();
        for(Item i : items) if(i.isHidden()) hiddenItems.add(i);

        return hiddenItems;
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
