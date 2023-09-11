package io.github.haappi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "mydb";
    private final static int DATABASE_VERSION = 1;
    private final String TABLE_NAME = "mytable";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_CONTENT = "content";
    private final String COLUMN_TIMESTAMP = "timestamp";

    private static DBHandler instance;

    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context);
        }
        return instance;
    }

    public static DBHandler getInstance() {
        if (instance == null) {
            throw new RuntimeException("DBHandler not initialized. Call this method with Context.");
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTable = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s INTEGER) ", TABLE_NAME, COLUMN_NAME, COLUMN_CONTENT, COLUMN_TIMESTAMP);
        database.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        throw new RuntimeException("Not implemented");
    }

    public void add(SubmitClass submitClass) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, submitClass.getFirstName());
        values.put(COLUMN_CONTENT, submitClass.getContentToStore());
        values.put(COLUMN_TIMESTAMP, submitClass.getTimestamp());

        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public ArrayList<SubmitClass> getAll() {
        String select = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(select, null);
        ArrayList<SubmitClass> submitClasses = new ArrayList<>();
        int i = 0;
        while (cursor.moveToNext()) {
            String firstName = cursor.getString(0);
            String content = cursor.getString(1);
            long timestamp = cursor.getLong(2);
            submitClasses.add(new SubmitClass(firstName, content, timestamp));
        }
        cursor.close();
        return submitClasses;
    }
}
