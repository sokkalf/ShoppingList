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


package no.opentech.shoppinglist;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import no.opentech.shoppinglist.utils.Logger;
import no.opentech.shoppinglist.utils.Statistics;

/**
 * Created by: Christian Lønaas
 * Date: 15.12.11
 * Time: 23:12
 */
public class ShoppingListApp extends Application {
    public static final String APP_NAME = "ShoppingList";
    public static final String PREFS_NAME = "ShoppingListSettings";
    private static final boolean DEVELOPMENT_VERSION = false;
    private static ShoppingListApp instance;
    private static Logger log = Logger.getLogger(ShoppingListApp.class);
    public static String dateFormat;
    public static int shakeSensitivity;

    @Override
    public void onCreate() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        setDateFormat(settings.getString("dateFormat", "dd.MM.yyyy HH:mm"));
        setShakeSensitivity(Integer.parseInt(settings.getString("shakeSensitivity", "5")));
        Statistics.setNumTimesStarted(settings.getInt("numTimesStarted", 0));
        Statistics.incrementNumTimesStarted();
        Statistics.setShoppingListsCreated(settings.getInt("shoppingListsCreated", 0));
        Statistics.setItemsCheckedOff(settings.getInt("itemsCheckedOff", 0));
        log.debug("Loading shared preferences");
        super.onCreate();
    }

    @Override
    public void onTerminate() {

    }
    
    public ShoppingListApp() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    public static boolean isRelease() {
        return !DEVELOPMENT_VERSION;
    }
    
    public static void setDateFormat(String df) {
        dateFormat = df;
    }

    public static void setShakeSensitivity(int sensitivity) {
        shakeSensitivity = sensitivity;
    }
}
