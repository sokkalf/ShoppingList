package no.opentech.shoppinglist.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel p, int flags) {
        p.writeLong(this.id);
        p.writeString(this.name);
        p.writeString(this.description);
        p.writeValue(this.created);
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
