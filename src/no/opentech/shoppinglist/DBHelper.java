package no.opentech.shoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

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

    private final Context myContext;


    public DBHelper(Context context) {
        super(context, "shoppingitems.db", null, DATABASE_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ShoppingList", "creating database");
        //db.execSQL(CREATE_STRING);
        try {
            InputStream is = myContext.getAssets().open("create_database.sql");
            String[] statements = parseSqlFile(is);
            for(String statement : statements)
                db.execSQL(statement);

        } catch(Exception e) {
            e.printStackTrace();
        }
        
        ArrayList<Item> shoppingItems = new ArrayList<Item>();
        shoppingItems.add(new Item("Bananer"));
        shoppingItems.add(new Item("Lime"));
        shoppingItems.add(new Item("Farris"));
        shoppingItems.add(new Item("Pizzasaus"));
        shoppingItems.add(new Item("Kylling"));
        shoppingItems.add(new Item("Kjeks"));
        shoppingItems.add(new Item("Mel"));
        
        for(Item i : shoppingItems)
            insert(i, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS item;");
        db.execSQL(CREATE_STRING);
    }
    public long getTimeStamp(Date date) {
        if(null != date) return date.getTime();
        else return new Date().getTime();
    }
    

    public long insert(Item item, SQLiteDatabase db) {
        String name = item.getName();
        String desc = item.getDescription();
        int usages = item.getUsageCounter();
        int avgnum = item.getAvgNumberInLine();
        long firstSeen = getTimeStamp(item.getFirstSeen());
        long lastSeen = getTimeStamp(item.getLastSeen());

        String sql = "INSERT INTO item (name, description, usages, avgNumberInLine, firstseen, lastseen) " +
                "VALUES ('" + name + "','" + desc + "'," + usages + "," + avgnum + "," + firstSeen + "" +
                "," + lastSeen + ");";
        db.execSQL(sql);
        Cursor c = db.rawQuery("SELECT last_insert_rowid();", null);
        c.moveToFirst();
        long id = c.getLong(0);
        return id;
    }

    public long insert(Item item) {
        long id = insert(item, getWritableDatabase());
        this.close();
        return id;
    }
    
    public void update(Item item) {
        Long id = item.getId();
        if(null == id) throw new IllegalArgumentException("ID can't be null");

        long firstSeen = getTimeStamp(item.getFirstSeen());
        long lastSeen = getTimeStamp(item.getLastSeen());
        
        String sql = "UPDATE item SET name = '" + item.getName() + "', description = '" + item.getDescription() + "'," +
                " usages = " + item.getUsageCounter() + ", avgNumberInLine = " + item.getAvgNumberInLine() + "," +
                " firstseen = " + firstSeen + ", lastseen = " + lastSeen + " WHERE id = " + id + ";";
        this.getWritableDatabase().execSQL(sql);
        this.close();
    }
    
    public void delete(Item item) {
        Long id = item.getId();
        if(null == id) throw new IllegalArgumentException("ID can't be null");
        
        String sql = "DELETE FROM item WHERE id = " + id + ";";
        this.getWritableDatabase().execSQL(sql);
        this.close();
    }

    public Item getItemById(long id) {
        Item i;
        Cursor c = this.getReadableDatabase().query("item", new String[] {"id", "name", "description", "usages", "avgNumberInLine",
                "firstseen", "lastseen"}, "id = '" + id + "'", null, null, null, null);

        c.moveToFirst();
        i = createItemFromValues(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5),
                c.getLong(6));

        c.close();
        this.close();
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
        return getItems(null);
    }
    
    public ArrayList<Item> getItemsOrderedByUsages() {
        return getItems("usages DESC");
    }
    
    public ArrayList<Item> getItems(String orderBy) {
        ArrayList<Item> itemList = new ArrayList<Item>();
        Cursor c = this.getReadableDatabase().query("item", new String[] {"id", "name", "description", "usages", "avgNumberInLine",
                "firstseen", "lastseen"}, null, null, null, null, orderBy);

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

    public static String[] parseSqlFile(InputStream sqlFile) throws IOException {
        return parseSqlFile(new BufferedReader(new InputStreamReader(sqlFile)));
    }

    public static String[] parseSqlFile(BufferedReader sqlFile) throws IOException {
        String line;
        StringBuilder sql = new StringBuilder();
        String multiLineComment = null;

        while ((line = sqlFile.readLine()) != null) {
            line = line.trim();

            // Check for start of multi-line comment
            if (multiLineComment == null) {
                // Check for first multi-line comment type
                if (line.startsWith("/*")) {
                    if (!line.endsWith("}")) {
                        multiLineComment = "/*";
                    }
                    // Check for second multi-line comment type
                } else if (line.startsWith("{")) {
                    if (!line.endsWith("}")) {
                        multiLineComment = "{";
                    }
                    // Append line if line is not empty or a single line comment
                } else if (!line.startsWith("--") && !line.equals("")) {
                    sql.append(line);
                } // Check for matching end comment
            } else if (multiLineComment.equals("/*")) {
                if (line.endsWith("*/")) {
                    multiLineComment = null;
                }
                // Check for matching end comment
            } else if (multiLineComment.equals("{")) {
                if (line.endsWith("}")) {
                    multiLineComment = null;
                }
            }

        }

        sqlFile.close();

        return sql.toString().split(";");
    }
}
