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


package no.opentech.shoppinglist.utils;

import no.opentech.shoppinglist.crud.ItemRepository;
import no.opentech.shoppinglist.crud.ShoppingListRepository;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.entities.ShoppingList;

import java.util.Date;

/**
 * Created by: Christian Lønaas
 * Date: 15.12.11
 * Time: 21:52
 */
public class Utils {
    private static final String TAG = "ShoppingList.Utils";
    private static DBHelper dBHelper = getDBHelper();
    private static ItemRepository itemRepository = getItemRepository();
    private static ShoppingListRepository shoppingListRepository = getShoppingListRepository();

    public static long getTimeStamp(Date date) {
        if(null != date) return date.getTime();
        else return new Date().getTime();
    }
    
    public static long getTimeStampNow() {
        return new Date().getTime();
    }
    
    public static Item createItemFromValues(long id, String name, String desc, int usages,
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

    public static Item createItemFromValues(long id, String name, String desc, int usages,
                                            int avgnum, long firstseen, long lastseen, int amount) {
        Item i = createItemFromValues(id, name, desc, usages, avgnum, firstseen, lastseen);
        i.setAmount(amount);
        return i;
    }
    
    public static ShoppingList createShoppingListFromValues(long id, String name, String desc, long created) {
        ShoppingList sl = new ShoppingList();
        sl.setId(id);
        sl.setName(name);
        sl.setDescription(desc);
        Date c = new Date();
        c.setTime(created);
        sl.setCreated(c);

        return sl;
    }
    
    public static DBHelper getDBHelper() {
        if(null != dBHelper) return dBHelper;
        else return new DBHelper(no.opentech.shoppinglist.ShoppingList.getContext());
    }

    public static ItemRepository getItemRepository() {
        if(null != itemRepository) return itemRepository;
        else return new ItemRepository(getDBHelper());
    }

    public static ShoppingListRepository getShoppingListRepository() {
        if(null != shoppingListRepository) return shoppingListRepository;
        else return new ShoppingListRepository(getDBHelper());
    }
}
