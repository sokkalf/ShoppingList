package no.opentech.shoppinglist.crud;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.utils.DBHelper;
import no.opentech.shoppinglist.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * User: sokkalf
 * Date: 15.12.11
 * Time: 21:40
 */
public class ItemRepository {
    DBHelper dBHelper;
    public ItemRepository(DBHelper db) {
        this.dBHelper = db;
    }

    public long insert(Item item) {
        String name = item.getName();
        String desc = item.getDescription();
        int usages = item.getUsageCounter();
        int avgnum = item.getAvgNumberInLine();
        long firstSeen = Utils.getTimeStamp(item.getFirstSeen());
        long lastSeen = Utils.getTimeStamp(item.getLastSeen());

        String sql = "INSERT INTO item (name, description, usages, avgNumberInLine, firstseen, lastseen) " +
                "VALUES ('" + name + "','" + desc + "'," + usages + "," + avgnum + "," + firstSeen + "" +
                "," + lastSeen + ");";
        SQLiteDatabase db = dBHelper.getWritableDatabase();
        db.execSQL(sql);
        Cursor c = db.rawQuery("SELECT last_insert_rowid();", null);
        c.moveToFirst();
        long id = c.getLong(0);
        dBHelper.close();
        return id;
    }

    public void update(Item item) {
        Long id = item.getId();
        if(null == id) throw new IllegalArgumentException("ID can't be null");

        long firstSeen = Utils.getTimeStamp(item.getFirstSeen());
        long lastSeen = Utils.getTimeStamp(item.getLastSeen());

        String sql = "UPDATE item SET name = '" + item.getName() + "', description = '" + item.getDescription() + "'," +
                " usages = " + item.getUsageCounter() + ", avgNumberInLine = " + item.getAvgNumberInLine() + "," +
                " firstseen = " + firstSeen + ", lastseen = " + lastSeen + " WHERE id = " + id + ";";
        dBHelper.getWritableDatabase().execSQL(sql);
        dBHelper.close();
    }

    public void delete(Item item) {
        Long id = item.getId();
        if(null == id) throw new IllegalArgumentException("ID can't be null");

        String sql = "DELETE FROM item WHERE id = " + id + ";";
        dBHelper.getWritableDatabase().execSQL(sql);
        dBHelper.close();
    }

    public Item getItemById(long id) {
        Item i;
        Cursor c = dBHelper.getReadableDatabase().query("item", new String[] {"id", "name", "description", "usages", "avgNumberInLine",
                "firstseen", "lastseen"}, "id = '" + id + "'", null, null, null, null);

        c.moveToFirst();
        i = createItemFromValues(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5),
                c.getLong(6));

        c.close();
        dBHelper.close();
        return i;
    }

    public Item createItemFromValues(int id, String name, String desc, int usages,
                                     int avgnum, long firstseen, long lastseen) {
        Item i = new Item();
        i.setId(id);
        i.setName(name);
        i.setDescription(desc);
        i.setUsageCounter(usages);
        i.setAvgNumberInLine(avgnum);
        Date first = new Date();
        first.setTime(firstseen);
        i.setFirstSeen(first);
        Date last = new Date();
        last.setTime(lastseen);
        i.setLastSeen(last);

        return i;
    }


    public Item getItemByName(String name) {
        Item i;
        Cursor c = dBHelper.getReadableDatabase().query("item", new String[] {"id", "name", "description", "usages", "avgNumberInLine",
                "firstseen", "lastseen"}, "name = '" + name + "'", null, null, null, null);

        c.moveToFirst();
        i = createItemFromValues(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5),
                c.getLong(6));

        c.close();
        dBHelper.close();
        return i;
    }

    public ArrayList<Item> getItems() {
        return getItems(null);
    }

    public ArrayList<Item> getItemsOrderedByUsages() {
        return getItems("usages DESC");
    }

    public ArrayList<Item> getItems(String orderBy) {
        ArrayList<Item> itemList = new ArrayList<Item>();
        Cursor c = dBHelper.getReadableDatabase().query("item", new String[] {"id", "name", "description", "usages", "avgNumberInLine",
                "firstseen", "lastseen"}, null, null, null, null, orderBy);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            itemList.add(createItemFromValues(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5),
                    c.getLong(6)));
            c.moveToNext();
        }
        c.close();
        dBHelper.close();
        return itemList;
    }
}
