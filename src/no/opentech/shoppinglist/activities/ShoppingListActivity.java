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


package no.opentech.shoppinglist.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import no.opentech.shoppinglist.adapters.ViewShoppingListAdapter;
import no.opentech.shoppinglist.entities.Item;
import no.opentech.shoppinglist.R;
import no.opentech.shoppinglist.adapters.ShoppingListAdapter;
import no.opentech.shoppinglist.entities.ShoppingList;
import no.opentech.shoppinglist.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by: Christian Lønaas
 * Date: 11.12.11
 * Time: 01:05
 */
@SuppressWarnings("unchecked")
public class ShoppingListActivity extends ListActivity {
    private Context context = no.opentech.shoppinglist.ShoppingList.getContext();
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private ShoppingList shoppingList;
    private ArrayList<Item> visibleItems;

    private int numInLine;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        
        long id = getIntent().getLongExtra("shoppingListId", 0);
        shoppingList = Utils.getShoppingListRepository().getShoppingListById(id);
        numInLine = 0;
        setTitle(shoppingList.getName());
        visibleItems = new ArrayList<Item>();
        visibleItems.addAll(shoppingList.getItems());
        setListAdapter(new ShoppingListAdapter(context, R.layout.list_item, visibleItems));
        ListView lv = getListView();
        registerForContextMenu(lv);
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = visibleItems.get(position);
                Log.d("ShoppingList", "selected item : " + selectedItem.getName() + " , position : " + position + " , firstSeen : " + selectedItem.getFirstSeen());
                if(!selectedItem.isChecked()) { // this could be better, but OK for now..
                    numInLine++;
                    selectedItem.setChecked(true);
                    selectedItem.setNumberInLine(numInLine);
                } else {
                    selectedItem.setChecked(false);
                    selectedItem.setNumberInLine(0);
                    numInLine--;
                }
                ((ShoppingListAdapter)getListAdapter()).notifyDataSetChanged();

            }
        });        
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        if(shoppingList.allItemsChecked()) {
            updateNumbersAndDeleteList();
            setResult(Activity.RESULT_OK, resultIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, resultIntent);
        }
        this.finish();
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
        Item selectedItem = visibleItems.get(info.position);

        switch(item.getItemId()) {
            case R.id.showshoppinglistitemdetails:
                Intent intent = new Intent(this, ItemDetailsActivity.class);
                intent.putExtra("itemId", selectedItem.getId());
                this.startActivity(intent);
                break;
            case R.id.removefromshoppinglist:
                shoppingList.getItems().remove(selectedItem);
                visibleItems.remove(selectedItem);
                Utils.getShoppingListRepository().removeItemFromShoppingList(selectedItem, shoppingList);
                ((ShoppingListAdapter)getListAdapter()).notifyDataSetChanged();
                break;
        }
        return true;
    }
    
    public void updateNumbersAndDeleteList() {
        for(Item item : shoppingList.getItems()) {
            if(item.getUsageCounter() < 1) item.setAvgNumberInLine(item.getAvgNumberInLine() + item.getNumberInLine() / 2);
            else item.setAvgNumberInLine(item.getNumberInLine());
            Utils.getItemRepository().update(item);
        }
        Utils.getShoppingListRepository().delete(shoppingList);
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            
            if(mAccel > 5) {
                if(shoppingList.allItemsChecked()) {
                    Intent resultIntent = new Intent();
                    updateNumbersAndDeleteList();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    shoppingList.hideCheckedItems();
                    visibleItems.removeAll(shoppingList.getHiddenItems());
                    ((ShoppingListAdapter)getListAdapter()).notifyDataSetChanged();
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onStop();
    }
}