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

import android.content.SharedPreferences;
import no.opentech.shoppinglist.ShoppingListApp;

/**
 * Created by: Christian Lønaas
 * Date: 27.12.11
 * Time: 14:39
 */
public class Statistics {
    private static int numTimesStarted;
    private static int shoppingListsCreated;
    private static int itemsCheckedOff;

    public static void incrementNumTimesStarted() {
        setNumTimesStarted(getNumTimesStarted() + 1);
    }

    public static void incrementShoppingListsCreated() {
        setShoppingListsCreated(getShoppingListsCreated() + 1);
    }

    public static void incrementItemsCheckedOff() {
        setItemsCheckedOff(getItemsCheckedOff() + 1);
    }

    public static int getNumTimesStarted() {
        return numTimesStarted;
    }

    public static void setNumTimesStarted(int numTimesStarted) {
        Statistics.numTimesStarted = numTimesStarted;
    }

    public static int getShoppingListsCreated() {
        return shoppingListsCreated;
    }

    public static void setShoppingListsCreated(int shoppingListsCreated) {
        Statistics.shoppingListsCreated = shoppingListsCreated;
    }

    public static int getItemsCheckedOff() {
        return itemsCheckedOff;
    }

    public static void setItemsCheckedOff(int itemsCheckedOff) {
        Statistics.itemsCheckedOff = itemsCheckedOff;
    }
    
    public static void saveStats() {
        SharedPreferences settings = ShoppingListApp.getContext().getSharedPreferences(ShoppingListApp.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("itemsCheckedOff", getItemsCheckedOff());
        editor.putInt("shoppingListsCreated", getShoppingListsCreated());
        editor.putInt("numTimesStarted", getNumTimesStarted());
        editor.commit();
    }

    public static void reset() {
        setItemsCheckedOff(0);
        setShoppingListsCreated(0);
        setNumTimesStarted(0);
        saveStats();
    }
}
