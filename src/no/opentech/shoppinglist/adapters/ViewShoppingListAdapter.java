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


package no.opentech.shoppinglist.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import no.opentech.shoppinglist.R;
import no.opentech.shoppinglist.entities.ShoppingList;
import no.opentech.shoppinglist.utils.Utils;

import java.util.ArrayList;

/**
 * Created by: Christian Lønaas
 * Date: 16.12.11
 * Time: 22:14
 */
public class ViewShoppingListAdapter extends ArrayAdapter<ShoppingList> {
    private ArrayList<ShoppingList> lists;

    public ViewShoppingListAdapter(Context context, int textViewResourceId, ArrayList<ShoppingList> items) {
        super(context, textViewResourceId, items);
        this.lists = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.shoppinglists_row, null);
        }
        ShoppingList list = lists.get(position);
        list.setItems(Utils.getShoppingListRepository().getShoppingListItems(list));
        if(null != list) {
            TextView text = (TextView) v.findViewById(R.id.itemtext);
            TextView summary = (TextView) v.findViewById(R.id.summary);
            text.setTextColor(Color.BLACK);
            text.setText(list.getName());
            summary.setTextColor(Color.BLACK);
            summary.setText((list.isDefaultList()) ? "" : list.getItems().size() + " items remaining");
        }
        return v;
    }
}
