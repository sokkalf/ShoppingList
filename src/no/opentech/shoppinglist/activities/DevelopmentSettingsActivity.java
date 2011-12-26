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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;
import no.opentech.shoppinglist.R;
import no.opentech.shoppinglist.ShoppingListApp;
import no.opentech.shoppinglist.models.DevelopmentSettingsModel;

/**
 * Created by: Christian Lønaas
 * Date: 25.12.11
 * Time: 18:05
 */
public class DevelopmentSettingsActivity extends PreferenceActivity {
    DevelopmentSettingsModel model;
    Context context = ShoppingListApp.getContext();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new DevelopmentSettingsModel();
        addPreferencesFromResource(R.xml.developmentsettings);
        Preference resetCounters = findPreference("resetCounters");
        resetCounters.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DevelopmentSettingsActivity.this);
                alertDialog.setTitle("Reset counters?");
                alertDialog.setMessage("This will reset all item counters, are you sure?");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        model.resetCounters();
                        Toast.makeText(context, "Item counters reset", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialog.show();
                return true;
            }
        });

        Preference dateFormat = findPreference("dateFormat");
        dateFormat.setDefaultValue(model.getDateFormat());
        dateFormat.setOnPreferenceChangeListener(new ListPreference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object value) {
                model.setDateFormat(value.toString());
                return true;
            }
        });

        Preference shakeSensitivity = findPreference("shakeSensitivity");
        shakeSensitivity.setDefaultValue(model.getShakeSensitivity());
        shakeSensitivity.setOnPreferenceChangeListener(new ListPreference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object value) {
                model.setShakeSensitivity(value.toString());
                return true;
            }
        });
    }
}