package io.github.haappi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "sample_table";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_CONTENT = "content";

    public static DBHandler instance;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context);
        }
        Log.d("DBHandler", "getInstance: " + instance);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = String.format(
                "CREATE TABLE %s (%s TEXT, %s INTEGER, %s TEXT)",
                TABLE_NAME, COLUMN_NAME, COLUMN_AGE, COLUMN_CONTENT);
        Log.d("ExecSQL", createTableQuery);
        db.execSQL(createTableQuery);
        Log.d("DBHandler", "onCreate: " + createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public ArrayList<String> getAll() {
        String select = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<String> returnThingy = new ArrayList<>();
        while (cursor.moveToNext()) {
            String firstName = cursor.getString(0); // same indices as the csv / create table query
            int age = cursor.getInt(1);
            String content = cursor.getString(2);
            returnThingy.add("name: " + firstName + " age: " + age + " content: " + content);
        }
        cursor.close();
        database.close();
        return returnThingy;
    }

    public void deleteAll() {
        String delete = String.format("DELETE FROM %s", TABLE_NAME);
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(delete);
        database.close();
    }
}
