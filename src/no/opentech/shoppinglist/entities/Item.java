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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by: Christian Lønaas
 * Date: 09.04.11
 * Time: 19:47
 */
public class Item implements Serializable, Parcelable {
    private long id;
    private String name;
    private String description;
    private boolean checked;
    private boolean hidden;
    private int usageCounter;
    private int numberInLine;
    private int avgNumberInLine;
    private Date firstSeen;
    private Date lastSeen;
    private int amount;

    public Item() {
        Calendar cal = new GregorianCalendar();
        this.firstSeen = this.lastSeen = cal.getTime();
    }
    
    public Item(String name) {
        this.name = name;
        Calendar cal = new GregorianCalendar();
        this.firstSeen = this.lastSeen = cal.getTime();
    }

    public Item(String name, String description, boolean checked, int usageCounter, int numberInLine, int avgNumberInLine, Date firstSeen, Date lastSeen, int amount) {
        this.name = name;
        this.description = description;
        this.checked = checked;
        this.usageCounter = usageCounter;
        this.numberInLine = numberInLine;
        this.avgNumberInLine = avgNumberInLine;
        this.firstSeen = firstSeen;
        this.lastSeen = lastSeen;
        this.amount = amount;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getUsageCounter() {
        return usageCounter;
    }

    public void setUsageCounter(int usageCounter) {
        this.usageCounter = usageCounter;
    }

    public void incrementUsageCounter() {
        this.usageCounter++;
    }

    public Date getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(Date firstSeen) {
        this.firstSeen = firstSeen;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public int getNumberInLine() {
        return numberInLine;
    }

    public void setNumberInLine(int numberInLine) {
        this.numberInLine = numberInLine;
    }

    public int getAvgNumberInLine() {
        return avgNumberInLine;
    }

    public void setAvgNumberInLine(int avgNumberInLine) {
        this.avgNumberInLine = avgNumberInLine;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public int getAmount() {
        return amount;
    }
            
    public void writeToParcel(Parcel p, int flags) {
        p.writeLong(id);
        p.writeString(Boolean.toString(this.checked));
        p.writeString(this.name);
        p.writeString(this.description);
        p.writeValue(this.firstSeen);
        p.writeValue(this.lastSeen);
        p.writeInt(numberInLine);
        p.writeInt(avgNumberInLine);
        p.writeInt(usageCounter);
        p.writeInt(amount);
    }
    
    public void readFromParcel(Parcel in) {
        this.setId(in.readLong());
        this.setChecked(Boolean.getBoolean(in.readString()));
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setFirstSeen((Date) in.readValue(java.util.Date.class.getClassLoader()));
        this.setLastSeen((Date) in.readValue(java.util.Date.class.getClassLoader()));
        this.setNumberInLine(in.readInt());
        this.setAvgNumberInLine(in.readInt());
        this.setUsageCounter(in.readInt());
        this.setAmount(in.readInt());
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel source) {
            final Item i = new Item();
            i.id = source.readLong();
            i.checked = Boolean.getBoolean(source.readString());
            i.name = source.readString();
            i.description = source.readString();
            i.firstSeen = (Date) source.readValue(java.util.Date.class.getClassLoader());
            i.lastSeen = (Date) source.readValue(java.util.Date.class.getClassLoader());
            i.numberInLine = source.readInt();
            i.avgNumberInLine = source.readInt();
            i.usageCounter = source.readInt();
            i.amount = source.readInt();
            return i;
        }

        public Item[] newArray(int size) {
            throw new UnsupportedOperationException();
        }

    };

}
