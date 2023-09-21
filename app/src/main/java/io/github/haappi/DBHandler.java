package io.github.haappi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.github.haappi.POJOS.User;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "data";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "users";
    private static final String WORKOUTS_TABLE = "user_workouts";
    private static final String TRENDS_TABLE = "user_trends";

    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final boolean METRIC_OR_CUSTOMARY = true;
    private static final String CURRENT_GOAL = "current_goal";
    private static final String DB_NAME = "data";
    
    private static DBHandler instance;
    
    public static DBHandler getInstance() {
        if (instance == null) {
            throw new RuntimeException("DBHandler not initialized. Call DBHandler.init() first.");
        }
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new DBHandler(context);
        }
    }
    
    private DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + FIRST_NAME + " TEXT, "
                + LAST_NAME + " TEXT, "
                + HEIGHT + " INTEGER, "
                + WEIGHT + " INTEGER, "
                + METRIC_OR_CUSTOMARY + " BOOLEAN, "
                + CURRENT_GOAL + " INTEGER);";
        database.execSQL(createTable);

        createTable = "CREATE TABLE " + WORKOUTS_TABLE + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "date TEXT, "
                + "duration INTEGER, "
                + "distance INTEGER, "
                + "calories INTEGER, "
                + "notes TEXT);";
        database.execSQL(createTable);

        createTable = "CREATE TABLE " + TRENDS_TABLE + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "date TEXT, "
                + "weight INTEGER, "
                + "calories INTEGER, "
                + "steps INTEGER);";
        database.execSQL(createTable);
    }

    public void add(User user) {

    }

    public void modify(int userID, User user) {

    }

}
