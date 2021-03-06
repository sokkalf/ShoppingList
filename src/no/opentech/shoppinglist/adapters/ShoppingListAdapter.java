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
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import no.opentech.shoppinglist.R;
import no.opentech.shoppinglist.entities.Item;

import java.util.ArrayList;

/**
 * Created by: Christian Lønaas
 * Date: 12.12.11
 * Time: 20:14
 */
public class ShoppingListAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> items;

    public ShoppingListAdapter(Context context, int textViewResourceId, ArrayList<Item> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row, null);
        }
        Item item = items.get(position);
        if(null != item) {
            TextView text = (TextView) v.findViewById(R.id.itemtext);
            text.setText((item.getAmount() > 1) ? item.getAmount() + " " + item.getName() : item.getName());
            if(item.isChecked()) {
                text.setTextColor(Color.DKGRAY);
                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                text.setTextColor(Color.BLACK);
                text.setPaintFlags(text.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
        return v;
    }
}
