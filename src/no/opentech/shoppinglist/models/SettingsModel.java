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

package no.opentech.shoppinglist.models;

import android.content.Context;
import android.content.SharedPreferences;
import no.opentech.shoppinglist.ShoppingListApp;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.file.JSONHandler;
import no.opentech.shoppinglist.utils.Logger;
import no.opentech.shoppinglist.utils.Statistics;
import no.opentech.shoppinglist.utils.Utils;

import java.util.ArrayList;

/**
 * Created by: Christian Lønaas
 * Date: 25.12.11
 * Time: 19:14
 */
public class SettingsModel {
    Context context = ShoppingListApp.getContext();
    Logger log = Logger.getLogger(SettingsModel.class);
    private String dateFormat;
    private String shakeSensitivity;
    
    public SettingsModel() {
        dateFormat = ShoppingListApp.dateFormat;
        shakeSensitivity = Integer.toString(ShoppingListApp.shakeSensitivity);
    }
    
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        ShoppingListApp.setDateFormat(dateFormat);
        saveSettings();
    }
    
    public String getDateFormat() {
        return dateFormat;
    }
    
    public void setShakeSensitivity(String sensitivity) {
        this.shakeSensitivity = sensitivity;
        ShoppingListApp.setShakeSensitivity(Integer.parseInt(sensitivity));
        saveSettings();
    }
    
    public String getShakeSensitivity() {
        return shakeSensitivity;
    }

    public void resetCounters() {
        ArrayList<Item> items = Utils.getItemRepository().getItems();
        for(Item item : items) {
            item.setUsageCounter(0);
            item.setAvgNumberInLine(0);
            log.debug("Resetting counters for " + item.getName() + " (" + item.getId() + ")");
            Utils.getItemRepository().update(item);
        }
    }

    public boolean exportItems() {
        JSONHandler j = new JSONHandler();
        String json = j.createJSONFromItemList(Utils.getItemRepository().getItemsOrderedByName());
        return Utils.writeFileToSDCard(Utils.BACKUPFILE, json);
    }

    public boolean importItems() {
        String json = Utils.readFileFromSDCard(Utils.BACKUPFILE);
        if(null != json) {
            ArrayList<Item> existingItems = Utils.getItemRepository().getItems();
            JSONHandler j = new JSONHandler();
            ArrayList<Item> importedItems = j.createItemListFromJSON(json);
            for(Item i : importedItems) {
                log.debug(i.getName() + " read from import");
                /* contains method won't cut it, because id differs */
                boolean itemExists = false;
                for(Item ex : existingItems) {
                    if(ex.getName().equals(i.getName())) {
                        itemExists = true;
                        break;
                    }
                }
                if(!itemExists) Utils.getItemRepository().insert(i);
                else log.debug(i.getName() + " skipped because it already exists");
            }
            return true;
        }
        return false;        
    }
    
    public String getNumTimesStarted() {
        return "" + Statistics.getNumTimesStarted();
    }
    
    public String getShoppingListsCreated() {
        return "" + Statistics.getShoppingListsCreated();
    }
    
    public String getItemsCheckedOff() {
        return "" + Statistics.getItemsCheckedOff();
    }

    public void resetStats() {
        Statistics.reset();
    }
    
    public void saveSettings() {
        SharedPreferences settings = context.getSharedPreferences(ShoppingListApp.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("dateFormat", dateFormat);
        editor.commit();
    }
}
