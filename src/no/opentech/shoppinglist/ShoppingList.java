package no.opentech.shoppinglist;

import android.app.Application;
import android.content.Context;

/**
 * User: sokkalf
 * Date: 15.12.11
 * Time: 23:12
 */
public class ShoppingList extends Application {
    private static ShoppingList instance;

    public ShoppingList() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
