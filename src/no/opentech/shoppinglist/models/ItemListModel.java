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

import no.opentech.shoppinglist.adapters.ItemAdapter;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.entities.ShoppingList;
import no.opentech.shoppinglist.file.JSONHandler;
import no.opentech.shoppinglist.utils.Logger;
import no.opentech.shoppinglist.utils.Utils;

import java.util.ArrayList;

/**
 * Created by: Christian Lønaas
 * Date: 20.12.11
 * Time: 17:47
 */
public class ItemListModel {
    private ArrayList<Item> itemList;
    private ItemAdapter adapter;
    private static Logger log = Logger.getLogger(ItemListModel.class);
    private boolean multiDelete = false;
    private ArrayList<Item> initiallyCheckedItems;
    
    public ItemListModel(long shoppingListId) {
        initiallyCheckedItems = new ArrayList<Item>();
        if(shoppingListId == 0)
            itemList = Utils.getItemRepository().getItemsOrderedByName();
        else {
            ArrayList<Item> selectedItems = new ArrayList<Item>();
            ArrayList<Item> shoppingListItems = Utils.getShoppingListRepository().getShoppingListById(shoppingListId).getItems();
            itemList = Utils.getItemRepository().getItemsOrderedByUsages();
            for(Item i : shoppingListItems) {
                if(itemList.contains(i)) {
                    selectedItems.add(i);
                }
            }
            
            for(Item i : selectedItems) {
                Item updatedItem = itemList.get(itemList.indexOf(i));
                updatedItem.setChecked(true);
                updatedItem.setAmount(i.getAmount());
            }
            initiallyCheckedItems.addAll(selectedItems);
        }
    }

    public void setAdapter(ItemAdapter adapter) {
        this.adapter = adapter;
    }
    
    public ArrayList<Item> getItemList() {
        return itemList;
    }
    
    public void addItem(Item item) {
        log.debug("adding item " + item.getName());
        long id = Utils.getItemRepository().insert(item);
        item.setId(id);
        itemList.add(item);
        if((null != adapter) && (!adapter.contains(item))) {
            adapter.add(item);
        }
        update();
    }
    
    public void removeItems(ArrayList<Item> items) {
        multiDelete = true; // prevent update() from being called for each item
        for(Item i : items) {
            removeItem(i);
        }
        multiDelete = false;
        update();
    }

    public Item getItem(int pos) {
        return itemList.get(pos);
    }

    public void removeItem(Item item) {
        log.debug("removing item " + item.getName());
        itemList.remove(item);
        if((null != adapter) && (adapter.contains(item))) {
            adapter.remove(item);
        }
        Utils.getItemRepository().delete(item);
        if (!multiDelete) update();
    }
    
    public ArrayList<Item> getCheckedItems() {
        ArrayList<Item> checkedItems = new ArrayList<Item>();
        for(Item item : itemList)
            if(item.isChecked()) checkedItems.add(item);

        return checkedItems;
    }
    
    public void clearCheckedItems() {
        for(Item item : getCheckedItems()) item.setChecked(false);
        update();
    }
    
    public void save(long shoppingListId) {
        ShoppingList sl = Utils.getShoppingListRepository().getShoppingListById(shoppingListId);

        for(Item item : getCheckedItems()) {
            if(!sl.getItems().contains(item)) {
                item.incrementUsageCounter();
                Utils.getItemRepository().update(item);
                Utils.getShoppingListRepository().addItemToShoppingList(item, sl);
            }
        }

        for(Item i : initiallyCheckedItems) {
            if((sl.getItems().contains(i)) && (!getCheckedItems().contains(i))) {
                i.decrementUsageCounter();
                Utils.getItemRepository().update(i);
                Utils.getShoppingListRepository().removeItemFromShoppingList(i, sl);
            }
        }
    }

    public void update() {
        if(null != adapter) adapter.notifyDataSetChanged();
    }

}
