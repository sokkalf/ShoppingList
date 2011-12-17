package no.opentech.shoppinglist.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import no.opentech.shoppinglist.R;
import no.opentech.shoppinglist.adapters.ViewShoppingListAdapter;
import no.opentech.shoppinglist.entities.ShoppingList;
import no.opentech.shoppinglist.utils.Utils;
import java.util.ArrayList;

/**
 * User: sokkalf
 * Date: 08.04.11
 * Time: 21:34
 */
public class ViewShoppingListsActivity extends ListActivity {
    private Context context = no.opentech.shoppinglist.ShoppingList.getContext();
    private ArrayList<ShoppingList> shoppingLists;
    private static final int ITEMLISTACTIVITY = 98;
    private static final int SHOPPINGLISTACTIVITY = 99;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Shopping lists");
        shoppingLists = Utils.getShoppingListRepository().getShoppingLists();
        shoppingLists.add(new ShoppingList().getDefaultList());
        setListAdapter(new ViewShoppingListAdapter(context, R.layout.list_shoppinglists, shoppingLists));
        ListView lv = getListView();
        registerForContextMenu(lv);
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // toggle checked
                ShoppingList selectedItem = shoppingLists.get(position);
                long shoppingListId = selectedItem.getId();
                if (!selectedItem.isDefaultList()) {
                    openShoppingList(shoppingListId);
                } else {
                    createNewShoppingList();
                }
                ((ViewShoppingListAdapter) getListAdapter()).notifyDataSetChanged();
            }
        });
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ITEMLISTACTIVITY:
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(context, "Canceled..", Toast.LENGTH_SHORT).show();
                    long id = data.getLongExtra("shoppingListId", 0);
                    Utils.getShoppingListRepository().deleteById(id);
                    shoppingLists.clear();
                    shoppingLists.addAll(Utils.getShoppingListRepository().getShoppingLists());
                    shoppingLists.add(new ShoppingList().getDefaultList());
                }
                break;
            case SHOPPINGLISTACTIVITY:
                if(resultCode == Activity.RESULT_OK) {
                    Toast.makeText(context, "Finished, removing list", Toast.LENGTH_SHORT).show();
                    shoppingLists.clear();
                    shoppingLists.addAll(Utils.getShoppingListRepository().getShoppingLists());
                    shoppingLists.add(new ShoppingList().getDefaultList());
                }
                break;
        }
        ((ViewShoppingListAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.viewshoppinglists_context_menu, menu);
        menu.setHeaderTitle(R.string.context_menu_title);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ShoppingList selectedItem = shoppingLists.get(info.position);
        
        switch(item.getItemId()) {
            case R.id.viewshoppinglistdetails:
                Toast.makeText(context, "Not implemented yet..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteshoppinglist:
                deleteShoppingList(selectedItem);
                break;
        }
        
        return true;
    }

    public void openShoppingList(long id) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        intent.putExtra("shoppingListId", id);
        this.startActivityForResult(intent, SHOPPINGLISTACTIVITY);
    }

    public void goToItemSelection(long id) {
        Intent intent = new Intent(this, ItemListActivity.class);
        intent.putExtra("shoppingListId", id);
        this.startActivityForResult(intent, ITEMLISTACTIVITY);
    }

    public void createNewShoppingList() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setWidth(200); // TODO: hard coded width is bad
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String newList = input.getText().toString().trim();
                ShoppingList sl = new ShoppingList();
                if(!newList.equals("")) {
                    sl.setName(newList);
                    long id = Utils.getShoppingListRepository().insert(sl);
                    sl.setId(id);
                    shoppingLists.add(0, sl);
                    ((ViewShoppingListAdapter) getListAdapter()).notifyDataSetChanged();
                    goToItemSelection(id);
                }
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
        alert.show();

    }
    
    public void deleteShoppingList(ShoppingList sl) {
        Utils.getShoppingListRepository().delete(sl);
        shoppingLists.remove(sl);
        ((ViewShoppingListAdapter) getListAdapter()).notifyDataSetChanged();
    }
}
