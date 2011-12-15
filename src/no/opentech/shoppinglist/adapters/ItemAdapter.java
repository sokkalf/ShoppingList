package no.opentech.shoppinglist.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.R;

import java.util.ArrayList;

/**
 * User: sokkalf
 * Date: 09.04.11
 * Time: 20:06
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> items;

    public ItemAdapter(Context context, int textViewResourceId, ArrayList<Item> items) {
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
            if(item.isChecked())
                text.setTextColor(Color.GREEN);
            else text.setTextColor(Color.LTGRAY);
        }
        return v;
    }
}
