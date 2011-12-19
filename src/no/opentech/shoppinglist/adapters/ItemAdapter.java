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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.R;

import java.util.ArrayList;

/**
 * Created by: Christian Lønaas
 * Date: 09.04.11
 * Time: 20:06
 */
public class ItemAdapter extends ArrayAdapter<Item> implements Filterable {
    private ArrayList<Item> filteredItems;
    private ArrayList<Item> items;
    private Filter filter;

    public ItemAdapter(Context context, int textViewResourceId, ArrayList<Item> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        filteredItems = new ArrayList<Item>();
        cloneItems();
    }

    public void cloneItems() {
        filteredItems.clear();
        for(Item i : items) filteredItems.add(i);
    }
    
    @Override
    public void add(Item item) {
        filteredItems.add(item);
        super.add(item);
    }
    
    @Override 
    public void remove(Item item) {
        filteredItems.remove(item);
        super.remove(item);
    }
    
    @Override
    public void insert(Item item, int pos) {
        filteredItems.add(pos, item);
        super.insert(item, pos);
    }



    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public Item getItem(int pos) {
        return filteredItems.get(pos);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) filter = new ItemFilter();

        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row, null);
        }
        Item item = filteredItems.get(position);
        if(null != item) {
            TextView text = (TextView) v.findViewById(R.id.itemtext);
            text.setText((item.getAmount() > 1) ? item.getAmount() + " " + item.getName() : item.getName());
            if(item.isChecked())
                text.setTextColor(Color.GREEN);
            else text.setTextColor(Color.LTGRAY);
        }
        return v;
    }

    private class ItemFilter extends Filter {
        FilterResults results = new FilterResults();
        
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint == null || constraint.length() == 0) {
                results.values = items;
                results.count = items.size();
            } else {
                ArrayList<Item> newValues = new ArrayList<Item>();
                for(Item i : items) {
                    if(i.getName().toLowerCase().startsWith(constraint.toString().toLowerCase()))
                        newValues.add(i);
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItems = (ArrayList<Item>) results.values;
            notifyDataSetChanged();
        }
    }
}
