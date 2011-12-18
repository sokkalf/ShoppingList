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

package no.opentech.shoppinglist.file;

import android.util.Log;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.utils.Logger;
import no.opentech.shoppinglist.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by: Christian Lønaas
 * Date: 18.12.11
 * Time: 01:20
 */
public class JSONHandler {
    private static Logger log = Logger.getLogger(JSONHandler.class);

    public JSONHandler() {

    }

    public String createJSONFromItemList(ArrayList<Item> itemList) {
        JSONArray jsonItems = new JSONArray();
        try {
            int i=0;
            for(Item item : itemList) {
                Log.d("ShoppingList/JSONHandler", "JSONifying " + item.getName());
                jsonItems.put(i, item.getName());
                jsonItems.put(1+i, item.getDescription());
                jsonItems.put(2+i, item.getUsageCounter());
                jsonItems.put(3+i, item.getNumberInLine());
                jsonItems.put(4+i, Utils.getTimeStamp(item.getFirstSeen()));
                jsonItems.put(5+i, Utils.getTimeStamp(item.getLastSeen()));
                i += 6;
            }           
        } catch(JSONException e) {
            return null;
        }
        return jsonItems.toString();
    }
    
    public ArrayList<Item> createItemListFromJSON(String json) {
        ArrayList<Item> itemList = new ArrayList<Item>();
        JSONArray jsonItems;
        try {
            jsonItems = new JSONArray(json);
            if((jsonItems.length() % 6) != 0) return null;
            for(int i=0; i<jsonItems.length(); i+=6) {
                Item item = new Item();
                item.setName(jsonItems.getString(i));
                item.setDescription(jsonItems.getString(i + 1));
                item.setUsageCounter(jsonItems.getInt(i + 2));
                item.setAvgNumberInLine(jsonItems.getInt(i + 3));
                Date first = new Date();
                first.setTime(jsonItems.getLong(i + 4));
                Date last = new Date();
                last.setTime(jsonItems.getLong(i + 5));
                item.setFirstSeen(first);
                item.setLastSeen(last);
                itemList.add(item);
            }
        } catch (JSONException e) {
            return null;
        }
        return itemList;
    }
}
