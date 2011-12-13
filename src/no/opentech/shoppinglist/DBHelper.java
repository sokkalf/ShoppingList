package no.opentech.shoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: sokkalf
 * Date: 09.04.11
 * Time: 00:19
 */

/* helper functions for SQLite */

// TODO: Add database for items

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_STRING = "" +
            "CREATE TABLE item (" +
            "   id          INTEGER PRIMARY KEY AUTOINCREMENT," +
            "   name        TEXT," +
            "   description TEXT," +
            "   usages      NUMBER," +
            "   avgNumberInLine NUMBER," +
            "   firstseen   INTEGER," +
            "   lastseen    INTEGER);";


    public DBHelper(Context context) {
        super(context, "shoppingitems.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ShoppingList", "creating database");
        db.execSQL(CREATE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS item;");
        db.execSQL(CREATE_STRING);
    }
    
    public void insert(Item item) {
        String name = item.getName();
        String desc = item.getDescription();
        int usages = item.getUsageCounter();
        int avgnum = item.getAvgNumberInLine();
        long firstSeen = new Date().getTime();
        if (null != item.getFirstSeen()) firstSeen = item.getFirstSeen().getTime();
        long lastSeen = new Date().getTime();
        if(null != item.getLastSeen()) lastSeen = item.getLastSeen().getTime();
        
        String sql = "INSERT INTO item (name, description, usages, avgNumberInLine, firstseen, lastseen) " +
                "VALUES ('" + name + "','" + desc + "'," + usages + "," + avgnum + "," + firstSeen + "" +
                "," + lastSeen + ");";
        this.getWritableDatabase().execSQL(sql);
        this.close();
    }
    
    public void update(Item item) {

    }

    public Item getItemById(long id) {
        return new Item();
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
        Item i = new Item();
        Cursor c = this.getReadableDatabase().query("item", new String[] {"id", "name", "description", "usages", "avgNumberInLine",
                "firstseen", "lastseen"}, "name = '" + name + "'", null, null, null, null);

        c.moveToFirst();
        i = createItemFromValues(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5),
                c.getLong(6));
        
        c.close();
        this.close();
        return i;
    }
    
    public ArrayList<Item> getItems() {
        ArrayList<Item> itemList = new ArrayList<Item>();
        Cursor c = this.getReadableDatabase().query("item", new String[] {"id", "name", "description", "usages", "avgNumberInLine",
                "firstseen", "lastseen"}, null, null, null, null, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            itemList.add(createItemFromValues(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5),
                    c.getLong(6)));
            c.moveToNext();
        }
        c.close();
        this.close();
        return itemList;
    }
}
