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

package no.opentech.shoppinglist.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import no.opentech.shoppinglist.R;
import no.opentech.shoppinglist.models.ShoppingListDetailsModel;

/**
 * Created by: Christian Lønaas
 * Date: 25.12.11
 * Time: 23:57
 */
public class ShoppingListDetailsActivity extends Activity {
    private ShoppingListDetailsModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppinglist_details);
        TextView shoppingListNameText = (TextView) this.findViewById(R.id.shoppinglistname);
        TextView shoppingListCreatedDateText = (TextView) this.findViewById(R.id.shoppinglistcreateddate);
        TextView shoppingListNumItemsText = (TextView) this.findViewById(R.id.shoppinglistnumitems);
        long shoppingListId = getIntent().getLongExtra("shoppingListId", 0);
        model = new ShoppingListDetailsModel(shoppingListId);
        shoppingListNameText.setText(model.getName());
        shoppingListCreatedDateText.setText(model.getCreatedDate());
        shoppingListNumItemsText.setText(Integer.toString(model.getNumItems()));
    }
}
