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

import no.opentech.shoppinglist.adapters.ShoppingListAdapter;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.entities.ShoppingList;
import no.opentech.shoppinglist.utils.Utils;

import java.util.ArrayList;

/**
 * Created by: Christian Lønaas
 * Date: 21.12.11
 * Time: 17:53
 */
public class ShoppingListModel {
    private ShoppingList shoppingList;
    private ShoppingListAdapter adapter;
    
    public ShoppingListModel(long id) {
        shoppingList = Utils.getShoppingListRepository().getShoppingListById(id);
    }
    
    public void setAdapter(ShoppingListAdapter adapter) {
        this.adapter = adapter;
    }
    
    public ArrayList<Item> getShoppingListItems() {
        return shoppingList.getItems();
    }
    public Item getShoppingListItem(int pos) {
        return getShoppingListItems().get(pos);
    }

    public void update() {
        if(null != adapter) adapter.notifyDataSetChanged();
    }
    
    public String getName() {
        return shoppingList.getName();
    }

    public boolean allItemsChecked() {
        return shoppingList.allItemsChecked();
    }
    
    public void removeItem(Item item) {
        getShoppingListItems().remove(item);
        Utils.getShoppingListRepository().removeItemFromShoppingList(item, shoppingList);
    }

    public void updateNumbersAndDeleteList() {
        for(Item item : getShoppingListItems()) {
            if(item.getUsageCounter() < 1) item.setAvgNumberInLine(item.getAvgNumberInLine() + item.getNumberInLine() / 2);
            else item.setAvgNumberInLine(item.getNumberInLine());
            Utils.getItemRepository().update(item);
        }
        Utils.getShoppingListRepository().delete(shoppingList);
    }

    public void hideCheckedItems() {
        shoppingList.hideCheckedItems();
    }

    public ArrayList<Item> getHiddenShoppingListItems() {
        return shoppingList.getHiddenItems();
    }
}