package no.opentech.shoppinglist.utils;

import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.entities.ShoppingList;

import java.util.Date;

/**
 * User: sokkalf
 * Date: 15.12.11
 * Time: 21:52
 */
public class Utils {
    public static long getTimeStamp(Date date) {
        if(null != date) return date.getTime();
        else return new Date().getTime();
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
}
