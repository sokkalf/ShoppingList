package no.opentech.shoppinglist;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * User: sokkalf
 * Date: 12/11/11
 * Time: 1:05 AM
 */
@SuppressWarnings("unchecked")
public class ShoppingListActivity extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList shoppingList = getIntent().getParcelableArrayListExtra("shoppinglist");
        setListAdapter(new ItemAdapter(this.getApplicationContext(), R.layout.list_item, shoppingList));
        ListView lv = getListView();
        registerForContextMenu(lv);
        lv.setTextFilterEnabled(true);
    }
}