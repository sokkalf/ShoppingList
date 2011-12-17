package no.opentech.shoppinglist.crud;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.entities.ShoppingList;
import no.opentech.shoppinglist.utils.DBHelper;
import no.opentech.shoppinglist.utils.Utils;

import java.util.ArrayList;

/**
 * User: sokkalf
 * Date: 15.12.11
 * Time: 21:58
 */
public class ShoppingListRepository {
    DBHelper dBHelper;
    private static final String TAG = "ShoppingList:ShoppingListRepository";
    
    public ShoppingListRepository(DBHelper db) {
        this.dBHelper = db;
    }

    public long insert(ShoppingList sl) {
        String name = sl.getName();
        String desc = sl.getDescription();
        long created = Utils.getTimeStamp(sl.getCreated());

        String sql = "INSERT INTO list (name, description, created) " +
                "VALUES ('" + name + "','" + desc + "'," + created + ");";
        SQLiteDatabase db = dBHelper.getWritableDatabase();
        db.execSQL(sql);
        Cursor c = db.rawQuery("SELECT last_insert_rowid();", null);
        c.moveToFirst();
        long id = c.getLong(0);
        Log.d(TAG, "Inserted shopping list " + sl.getName() + " with ID " + id);
        dBHelper.close();
        return id;
    }

    public void update(ShoppingList sl) {
        Long id = sl.getId();
        if(null == id) throw new IllegalArgumentException("ID can't be null");

        long created = Utils.getTimeStamp(sl.getCreated());

        String sql = "UPDATE list SET name = '" + sl.getName() + "', description = '" + sl.getDescription() + "'," +
                "created = " + created + " WHERE id = " + id + ";";
        dBHelper.getWritableDatabase().execSQL(sql);
        Log.d(TAG, "Updated shopping list with ID " + id);
        dBHelper.close();
    }

    public void delete(ShoppingList sl) {
        Long id = sl.getId();
        if(null == id) throw new IllegalArgumentException("ID can't be null");

        SQLiteDatabase db = dBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM listitem WHERE listid = " + id + ";"); // remove items first
        db.execSQL("DELETE FROM list WHERE id = " + id + ";");
        Log.d(TAG, "Deleted shopping list with id " + id);
        dBHelper.close();
    }

    public void deleteById(long id) {
        SQLiteDatabase db = dBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM listitem WHERE listid = " + id + ";"); // remove items first
        db.execSQL("DELETE FROM list WHERE id = " + id + ";");
        Log.d(TAG, "Deleted shopping list with id " + id);
        dBHelper.close();
    }

    public ShoppingList getShoppingListById(long id) {
        ShoppingList sl;
        Cursor c = dBHelper.getReadableDatabase().query("list", new String[] {"id", "name", "description", "created"}, "id = '" + id + "'",
                null, null, null, null);

        c.moveToFirst();
        sl = Utils.createShoppingListFromValues(c.getLong(0), c.getString(1), c.getString(2), c.getLong(3));

        c.close();
        dBHelper.close();
        sl.setItems(getShoppingListItems(sl));
        return sl;
    }

    public ShoppingList getShoppingListByName(String name) {
        ShoppingList sl;
        Cursor c = dBHelper.getReadableDatabase().query("list", new String[] {"id", "name", "description", "created"}, "name = '" + name + "'",
                null, null, null, null);

        c.moveToFirst();
        sl = Utils.createShoppingListFromValues(c.getLong(0), c.getString(1), c.getString(2), c.getLong(3));

        c.close();
        dBHelper.close();
        sl.setItems(getShoppingListItems(sl));
        return sl;
    }
    
    public ArrayList<ShoppingList> getShoppingLists() {
        return getShoppingLists(null);
    }

    public ArrayList<ShoppingList> getShoppingLists(String orderBy) {
        ArrayList<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
        Cursor c = dBHelper.getReadableDatabase().query("list", new String[] {"id", "name", "description", "created"},
                null, null, null, null, orderBy);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            shoppingLists.add(Utils.createShoppingListFromValues(c.getLong(0), c.getString(1), c.getString(2), c.getLong(3)));
            c.moveToNext();
        }
        c.close();
        dBHelper.close();
        return shoppingLists;
    }
    
    public void addItemToShoppingList(Item i, ShoppingList sl) {
        addItemToShoppingList(i, sl, 1);
    }
    
    public void addItemToShoppingList(Item i, ShoppingList sl, int amount) {
        Long slId = sl.getId(), iId = i.getId();
        if((null == iId) || (null == slId)) throw new IllegalArgumentException("Item ID and ShoppingList ID are required");
        
        String sql = "INSERT INTO listitem (listid, itemid, amount) VALUES (" + slId + "," + iId + "," + amount + ");";
        dBHelper.getWritableDatabase().execSQL(sql);
        dBHelper.close();
    }
    
    public void removeItemFromShoppingList(Item i, ShoppingList sl) {
        Long slId = sl.getId(), iId = i.getId();
        if((null == iId) || (null == slId)) throw new IllegalArgumentException("Item ID and ShoppingList ID are required");
        
        String sql = "DELETE FROM listitem WHERE listid = " + slId + " AND itemid = " + iId + ";";
        dBHelper.getWritableDatabase().execSQL(sql);
        dBHelper.close();
    }
    
    public ArrayList<Item> getShoppingListItems(ShoppingList sl) {
        return getShoppingListItems(sl, "avgNumberInLine");
    }
    
    public ArrayList<Item> getShoppingListItems(ShoppingList sl, String orderBy) {
        Long slId = sl.getId();
        if(null == slId) throw new IllegalArgumentException("ShoppingList ID is required");  
        
        ArrayList<Item> itemList = new ArrayList<Item>();

        String sql = "SELECT id, name, description, usages, avgNumberInLine, firstseen, lastseen " +
                "FROM item, listitem WHERE listid = " + slId + " AND item.id = listitem.itemid" +
                    ((null != orderBy) ? " ORDER BY " + orderBy : "") + ";";

        Cursor c = dBHelper.getReadableDatabase().rawQuery(sql, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            itemList.add(Utils.createItemFromValues(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5),
                    c.getLong(6)));
            c.moveToNext();
        }
        c.close();
        dBHelper.close();
        return itemList;        
    }
}
