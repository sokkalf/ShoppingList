package no.opentech.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * User: sokkalf
 * Date: 09.04.11
 * Time: 23:39
 */
public class ItemDetailsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        TextView itemNameText = (TextView) this.findViewById(R.id.itemname);
        TextView itemCreatedDateText = (TextView) this.findViewById(R.id.itemcreateddate);
        TextView itemUsageCounter = (TextView) this.findViewById(R.id.usagecounter);

        Item item = (Item)getIntent().getSerializableExtra("item");
        Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String firstSeen = formatter.format(item.getFirstSeen());
        itemNameText.setText(item.getName());
        itemCreatedDateText.setText(firstSeen);
        itemUsageCounter.setText(Integer.toString(item.getUsageCounter()));
    }
}

