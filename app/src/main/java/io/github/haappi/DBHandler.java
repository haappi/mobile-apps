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

    // -=-=-=-=-=-= users table -=-=-=-=-=-=
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String METRIC_OR_CUSTOMARY = "metric";
    private static final String CURRENT_GOAL = "current_goal";

    // -=-=-=-=-=-= user_workouts table -=-=-=-=-=-=



    // -=-=-=-=-=-= user_trends table -=-=-=-=-=-=
    private static final String DATE = "date";
    private static final String CALORIES = "calories";


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

//        createTable = "CREATE TABLE " + WORKOUTS_TABLE + " ("
//                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "name TEXT, "
//                + "date TEXT, "
//                + "duration INTEGER, "
//                + "distance INTEGER, "
//                + "calories INTEGER, "
//                + "notes TEXT);";
//        database.execSQL(createTable);

        createTable = "CREATE TABLE " + TRENDS_TABLE + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " TEXT, "
                + CALORIES + " INTEGER);";
        database.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int previousVersion, int newVersion) {

    }

    public void add(User user) {

    }

    public void modify(int userID, User user) {

    }

}
