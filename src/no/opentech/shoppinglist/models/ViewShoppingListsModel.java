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

import no.opentech.shoppinglist.entities.ShoppingList;
import no.opentech.shoppinglist.utils.Statistics;
import no.opentech.shoppinglist.utils.Utils;

import java.util.ArrayList;

/**
 * Created by: Christian Lønaas
 * Date: 21.12.11
 * Time: 18:37
 */
public class ViewShoppingListsModel {
    private ArrayList<ShoppingList> shoppingLists;

    public ViewShoppingListsModel() {
        shoppingLists = Utils.getShoppingListRepository().getShoppingLists();
        shoppingLists.add(new ShoppingList().getDefaultList());
    }

    public void refresh() {
        shoppingLists.clear();
        shoppingLists.addAll(Utils.getShoppingListRepository().getShoppingLists());
        shoppingLists.add(new ShoppingList().getDefaultList());
    }

    public ArrayList<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public ShoppingList getShoppingList(int pos) {
        return shoppingLists.get(pos);
    }

    public void deleteShoppingListById(long id) {
        ShoppingList shoppingListToDelete=null;
        Utils.getShoppingListRepository().deleteById(id);
        for (ShoppingList shoppingList : shoppingLists) {
            if (shoppingList.getId() == id) {
                shoppingListToDelete = shoppingList;
                break;
            }
        }
        if(null != shoppingListToDelete) shoppingLists.remove(shoppingListToDelete);
    }

    public long addNewShoppingList(ShoppingList sl) {
        long id = Utils.getShoppingListRepository().insert(sl);
        sl.setId(id);
        shoppingLists.add(0, sl);
        Statistics.incrementShoppingListsCreated();
        return id;
    }

    public void deleteShoppingList(ShoppingList sl) {
        Utils.getShoppingListRepository().delete(sl);
        shoppingLists.remove(sl);
    }
}
