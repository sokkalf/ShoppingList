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
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import static android.content.pm.PackageManager.NameNotFoundException;

/**
 * Created by: Christian Lønaas
 * Date: 15.12.11
 * Time: 23:12
 */
public class ShoppingListApp extends Application {
    public static String appName = "ShoppingList";
    private static boolean DEVELOPMENT_VERSION = true;
    private static ShoppingListApp instance;

    public ShoppingListApp() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    public static boolean isRelease() {
        return !DEVELOPMENT_VERSION;
    }
}