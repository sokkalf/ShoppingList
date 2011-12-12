package no.opentech.shoppinglist;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * User: sokkalf
 * Date: 12/11/11
 * Time: 1:05 AM
 */
@SuppressWarnings("unchecked")
public class ShoppingListActivity extends ListActivity {
    
    private ArrayList<Item> shoppingList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shoppingList = getIntent().getParcelableArrayListExtra("shoppinglist");
        setListAdapter(new ShoppingListAdapter(this.getApplicationContext(), R.layout.list_item, shoppingList));
        ListView lv = getListView();
        registerForContextMenu(lv);
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // toggle checked
                Item selectedItem = shoppingList.get(position);
                Log.i("ShoppingList", "selected item : " + selectedItem.getName() + " , position : " + position + " , firstSeen : " + selectedItem.getFirstSeen());
                selectedItem.setChecked(!selectedItem.isChecked());
                ((ShoppingListAdapter)getListAdapter()).notifyDataSetChanged();
            }
        });        
    }
}