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


package no.opentech.shoppinglist.crud;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.utils.DBHelper;
import no.opentech.shoppinglist.utils.Logger;
import no.opentech.shoppinglist.utils.Utils;

import java.util.ArrayList;

/**
 * Created by: Christian Lønaas
 * Date: 15.12.11
 * Time: 21:40
 */
public class ItemRepository {
    private static Logger log = Logger.getLogger(ItemRepository.class);
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
        log.debug("Inserted item " + item.getName() + " with ID " + id);
        dBHelper.close();
        return id;
    }

    public void update(Item item) {
        Long id = item.getId();
        if(null == id) throw new IllegalArgumentException("ID can't be null");

        long firstSeen = Utils.getTimeStamp(item.getFirstSeen());
        long lastSeen = Utils.getTimeStampNow(); // updating, so refresh timestamp

        String sql = "UPDATE item SET name = '" + item.getName() + "', description = '" + item.getDescription() + "'," +
                " usages = " + item.getUsageCounter() + ", avgNumberInLine = " + item.getAvgNumberInLine() + "," +
                " firstseen = " + firstSeen + ", lastseen = " + lastSeen + " WHERE id = " + id + ";";
        dBHelper.getWritableDatabase().execSQL(sql);
        log.debug("Updated item " + item.getName() + " with ID " + id);
        dBHelper.close();
    }

    public void delete(Item item) {
        Long id = item.getId();
        if(null == id) throw new IllegalArgumentException("ID can't be null");

        String sql = "DELETE FROM item WHERE id = " + id + ";";
        dBHelper.getWritableDatabase().execSQL(sql);
        log.debug("Deleted item " + item.getName() + " with ID " + id);
        dBHelper.close();
    }

    public Item getItemById(long id) {
        Item i;
        Cursor c = dBHelper.getReadableDatabase().query("item", new String[] {"id", "name", "description", "usages", "avgNumberInLine",
                "firstseen", "lastseen"}, "id = '" + id + "'", null, null, null, null);

        c.moveToFirst();
        i = Utils.createItemFromValues(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5),
                c.getLong(6));

        c.close();
        dBHelper.close();
        return i;
    }

    public Item getItemByName(String name) {
        Item i;
        Cursor c = dBHelper.getReadableDatabase().query("item", new String[] {"id", "name", "description", "usages", "avgNumberInLine",
                "firstseen", "lastseen"}, "name = '" + name + "'", null, null, null, null);

        c.moveToFirst();
        i = Utils.createItemFromValues(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5),
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
            itemList.add(Utils.createItemFromValues(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5),
                    c.getLong(6)));
            c.moveToNext();
        }
        c.close();
        log.debug("Returned " + itemList.size() + " items");
        dBHelper.close();
        return itemList;
    }
}
