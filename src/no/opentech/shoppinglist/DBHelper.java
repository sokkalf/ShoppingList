package no.opentech.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * User: sokkalf
 * Date: 09.04.11
 * Time: 00:19
 */

/* helper functions for SQLite */

public class DBHelper extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, "gs", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
