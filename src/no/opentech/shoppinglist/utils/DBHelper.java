package no.opentech.shoppinglist.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import no.opentech.shoppinglist.entities.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

/**
 * User: sokkalf
 * Date: 09.04.11
 * Time: 00:19
 */

/* helper functions for SQLite */

// TODO: Add database for items

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_STRING = "" +
            "CREATE TABLE item (" +
            "   id          INTEGER PRIMARY KEY AUTOINCREMENT," +
            "   name        TEXT," +
            "   description TEXT," +
            "   usages      NUMBER," +
            "   avgNumberInLine NUMBER," +
            "   firstseen   INTEGER," +
            "   lastseen    INTEGER);";

    private final Context myContext;


    public DBHelper(Context context) {
        super(context, "shoppingitems.db", null, DATABASE_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ShoppingList", "creating database");
        //db.execSQL(CREATE_STRING);
        try {
            InputStream is = myContext.getAssets().open("create_database.sql");
            String[] statements = parseSqlFile(is);
            for(String statement : statements)
                db.execSQL(statement);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS item;");
        db.execSQL(CREATE_STRING);
    }


    public static String[] parseSqlFile(InputStream sqlFile) throws IOException {
        return parseSqlFile(new BufferedReader(new InputStreamReader(sqlFile)));
    }

    public static String[] parseSqlFile(BufferedReader sqlFile) throws IOException {
        String line;
        StringBuilder sql = new StringBuilder();
        String multiLineComment = null;

        while ((line = sqlFile.readLine()) != null) {
            line = line.trim();

            // Check for start of multi-line comment
            if (multiLineComment == null) {
                // Check for first multi-line comment type
                if (line.startsWith("/*")) {
                    if (!line.endsWith("}")) {
                        multiLineComment = "/*";
                    }
                    // Check for second multi-line comment type
                } else if (line.startsWith("{")) {
                    if (!line.endsWith("}")) {
                        multiLineComment = "{";
                    }
                    // Append line if line is not empty or a single line comment
                } else if (!line.startsWith("--") && !line.equals("")) {
                    sql.append(line);
                } // Check for matching end comment
            } else if (multiLineComment.equals("/*")) {
                if (line.endsWith("*/")) {
                    multiLineComment = null;
                }
                // Check for matching end comment
            } else if (multiLineComment.equals("{")) {
                if (line.endsWith("}")) {
                    multiLineComment = null;
                }
            }

        }

        sqlFile.close();

        return sql.toString().split(";");
    }
}