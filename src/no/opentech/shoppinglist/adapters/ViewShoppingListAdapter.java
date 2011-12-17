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
import no.opentech.shoppinglist.entities.ShoppingList;

import java.util.ArrayList;

/**
 * User: sokkalf
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
            v = vi.inflate(R.layout.row, null);
        }
        ShoppingList list = lists.get(position);
        if(null != list) {
            TextView text = (TextView) v.findViewById(R.id.itemtext);
            text.setText(list.getName());
        }
        return v;
    }
}
