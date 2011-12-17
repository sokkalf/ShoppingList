package no.opentech.shoppinglist.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import no.opentech.shoppinglist.crud.ItemRepository;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.R;
import no.opentech.shoppinglist.utils.Utils;

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
        TextView itemUsageCounter = (TextView) this.findViewById(R.id.itemusagecounter);
        TextView itemUpdatedDateText = (TextView) this.findViewById(R.id.itemlastseen);
        TextView itemAvgNumInLineText = (TextView) this.findViewById(R.id.itemavgnuminline);

        long itemId = getIntent().getLongExtra("itemId", 0);
        Item item = Utils.getItemRepository().getItemById(itemId);
        Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        itemNameText.setText(item.getName());
        itemCreatedDateText.setText(formatter.format(item.getFirstSeen()));
        itemUsageCounter.setText(Integer.toString(item.getUsageCounter()));
        itemUpdatedDateText.setText(formatter.format(item.getLastSeen()));
        itemAvgNumInLineText.setText(Integer.toString(item.getAvgNumberInLine()));
    }
}

