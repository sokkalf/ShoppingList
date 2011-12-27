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

import android.util.Log;
import no.opentech.shoppinglist.ShoppingListApp;

/**
 * Created by: Christian Lønaas
 * Date: 18.12.11
 * Time: 16:08
 */
public class Logger {
    public static boolean enabled = !ShoppingListApp.isRelease();
    public static String TAG;
    public static Logger logger;
    
    public Logger(String name) {
        TAG = ShoppingListApp.APP_NAME + "/" + name;
    }

    public static Logger getLogger(Class c) {
        logger = new Logger(c.getName());
        return logger;
    }
    
    public void debug(String message) {
        if(enabled) Log.d(TAG, message);
    }
    
    public void info(String message) {
        if(enabled) Log.i(TAG, message);
    }
    
    public void verbose(String message) {
        if(enabled) Log.v(TAG, message);
    }
    
    public void error(String message) {
        if (enabled) Log.e(TAG, message);
    }
    
    public void warning(String message) {
        if (enabled) Log.w(TAG, message);
    }
}
