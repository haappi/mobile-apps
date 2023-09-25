package io.github.haappi;

import static io.github.haappi.DBHandler.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBManager {
    private DBHandler dbHandler;
    private final Context context;
    private SQLiteDatabase database;

    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() throws SQLException {
        dbHandler = new DBHandler(context);
        database = dbHandler.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHandler.close();
    }

    public ArrayList<String> getAll() {
        String select = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase database = open().database;
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
        SQLiteDatabase database = open().database;
        database.execSQL(delete);
        database.close();
    }

    public SQLiteDatabase getWritableDatabase() {
        return database;
    }
}
