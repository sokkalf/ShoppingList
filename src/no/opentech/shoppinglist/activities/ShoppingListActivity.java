package no.opentech.shoppinglist.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.R;
import no.opentech.shoppinglist.adapters.ShoppingListAdapter;

import java.io.Serializable;
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
        setTitle("My list");

        shoppingList = getIntent().getParcelableArrayListExtra("shoppinglist");
        setListAdapter(new ShoppingListAdapter(this.getApplicationContext(), R.layout.list_item, shoppingList));
        ListView lv = getListView();
        registerForContextMenu(lv);
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // toggle checked
                Item selectedItem = shoppingList.get(position);
                Log.d("ShoppingList", "selected item : " + selectedItem.getName() + " , position : " + position + " , firstSeen : " + selectedItem.getFirstSeen());
                selectedItem.setChecked(!selectedItem.isChecked());
                ((ShoppingListAdapter)getListAdapter()).notifyDataSetChanged();
            }
        });        
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.shoppinglist_context_menu, menu);
        menu.setHeaderTitle(R.string.context_menu_title);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Item selectedItem = shoppingList.get(info.position);

        switch(item.getItemId()) {
            case R.id.showshoppinglistitemdetails:
                Intent intent = new Intent(this, ItemDetailsActivity.class);
                intent.putExtra("item", (Serializable)selectedItem);
                this.startActivity(intent);
                break;
        }
        return true;
    }
}