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
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.models.ItemDetailsModel;
import no.opentech.shoppinglist.utils.Utils;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by: Christian Lønaas
 * Date: 09.04.11
 * Time: 23:39
 */
public class ItemDetailsActivity extends Activity {
    private ItemDetailsModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        TextView itemNameText = (TextView) this.findViewById(R.id.itemname);
        TextView itemCreatedDateText = (TextView) this.findViewById(R.id.itemcreateddate);
        TextView itemUsageCounter = (TextView) this.findViewById(R.id.itemusagecounter);
        TextView itemUpdatedDateText = (TextView) this.findViewById(R.id.itemlastseen);
        TextView itemAvgNumInLineText = (TextView) this.findViewById(R.id.itemavgnuminline);

        long itemId = getIntent().getLongExtra("itemId", 0);
        model = new ItemDetailsModel(itemId);

        itemNameText.setText(model.getItemName());
        itemCreatedDateText.setText(model.getCreatedAsString());
        itemUsageCounter.setText(Integer.toString(model.getItemUsageCounter()));
        itemUpdatedDateText.setText(model.getUpdatedAsString());
        itemAvgNumInLineText.setText(Integer.toString(model.getItemAvgNumberInLine()));
    }
}

