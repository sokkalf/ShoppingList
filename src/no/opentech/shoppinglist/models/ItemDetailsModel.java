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

import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.utils.Utils;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by: Christian Lønaas
 * Date: 21.12.11
 * Time: 18:54
 */
public class ItemDetailsModel {
    private Item item;

    public ItemDetailsModel(long id) {
        this.item = Utils.getItemRepository().getItemById(id);
    }
    
    public String getItemName() {
        return item.getName();
    }
    
    public String getItemDescription() {
        return item.getDescription();
    }
    
    public int getItemUsageCounter() {
        return item.getUsageCounter();
    }
    
    public int getItemAvgNumberInLine() {
        return item.getAvgNumberInLine();
    }
    
    public String getCreatedAsString() {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatter.format(item.getFirstSeen());
    }
    
    public String getUpdatedAsString() {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatter.format(item.getLastSeen());
    }
}
