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
import no.opentech.shoppinglist.utils.Logger;
import no.opentech.shoppinglist.utils.Utils;

/**
 * Created by: Christian Lønaas
 * Date: 25.12.11
 * Time: 23:59
 */
public class ShoppingListDetailsModel {
    private static Logger log = Logger.getLogger(ShoppingListDetailsModel.class);
    private ShoppingList shoppingList;
    
    public ShoppingListDetailsModel(long id) {
        shoppingList = Utils.getShoppingListRepository().getShoppingListById(id);
        log.debug("fetched shopping list " + shoppingList.getName() + " with id " + id);
    }
    
    public String getName() {
        return shoppingList.getName();
    }
    
    public int getNumItems() {
        return shoppingList.getItems().size();
    }
    
    public String getCreatedDate() {
        return Utils.formatDate(shoppingList.getCreated());
    }
}
