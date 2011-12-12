package no.opentech.shoppinglist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * User: sokkalf
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
            text.setText(item.getName());
            text.setTextColor(Color.LTGRAY);
            if(item.isChecked())
                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            else text.setPaintFlags(text.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
        return v;
    }
}
