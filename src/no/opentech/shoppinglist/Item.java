package no.opentech.shoppinglist;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: sokkalf
 * Date: 09.04.11
 * Time: 19:47
 */
public class Item implements Serializable {
    private String name;
    private String description;
    private boolean checked;
    private int usageCounter;
    private int numberInLine;
    private int avgNumberInLine;
    private Date firstSeen;
    private Date lastSeen;


    public Item(String name) {
        this.name = name;
        Calendar cal = new GregorianCalendar();
        this.firstSeen = cal.getTime();
    }

    public Item(String name, String description, boolean checked, int usageCounter, int numberInLine, int avgNumberInLine, Date firstSeen, Date lastSeen) {
        this.name = name;
        this.description = description;
        this.checked = checked;
        this.usageCounter = usageCounter;
        this.numberInLine = numberInLine;
        this.avgNumberInLine = avgNumberInLine;
        this.firstSeen = firstSeen;
        this.lastSeen = lastSeen;
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
}
