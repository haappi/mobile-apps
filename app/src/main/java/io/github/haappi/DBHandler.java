package io.github.haappi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import io.github.haappi.POJOS.User;

public class DBHandler extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "users";
    public static final String WORKOUTS_TABLE = "user_workouts";
    //    private static final String TRENDS_TABLE = "user_trends";
    public static final String SAVED_WORKOUTS_TABLE = "saved_workouts";
    private static final String DB_NAME = "data";
    private static final int DB_VERSION = 1;
    // -=-=-=-=-=-= users table -=-=-=-=-=-=
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String METRIC_OR_CUSTOMARY = "metric";
    private static final String CURRENT_GOAL = "current_goal";

    // -=-=-=-=-=-= user_workouts table -=-=-=-=-=-=
    private static final String USER_LINKED_TO = "user_id";
    private static final String TIME = "time";
    private static final String DURATION = "duration";
    private static final String CUSTOM_NAME = "custom_name";
    private static final String workoutsPerformed = "workouts_performed";

    //     -=-=-=-=-=-= user_trends table -=-=-=-=-=-=
    //    private static final String DATE = "date";

    // -=-=-=-=-=-= saved_workouts table -=-=-=-=-=-=
    private static final String SAVED_WORKOUTS_USER_LINKED_TO = "user_id";
    private static final String NAME = "name";
    private static final String SETS = "sets";
    private static final String LAST_TIME_PERFORMED = "last_time_performed";
    private static final String WORKOUT_TYPE = "workout_type";

    private static DBHandler instance = null;

    private DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

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
        Log.d("DBHandler", "init: " + instance);
    }

    public void onCreate(SQLiteDatabase database) {
        String createTable =
                "CREATE TABLE "
                        + TABLE_NAME
                        + " ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FIRST_NAME
                        + " TEXT, "
                        + LAST_NAME
                        + " TEXT, "
                        + HEIGHT
                        + " INTEGER, "
                        + WEIGHT
                        + " INTEGER, "
                        + METRIC_OR_CUSTOMARY
                        + " INTEGER, "
                        + CURRENT_GOAL
                        + " INTEGER"
                        + ")";
        database.execSQL(createTable);

        createTable =
                "CREATE TABLE "
                        + WORKOUTS_TABLE
                        + " ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + USER_LINKED_TO
                        + " INTEGER, "
                        + TIME
                        + " INTEGER, "
                        + DURATION
                        + " INTEGER, "
                        + CUSTOM_NAME
                        + " TEXT, "
                        + workoutsPerformed
                        + " TEXT"
                        + ")";
        database.execSQL(createTable);

        //        createTable = "CREATE TABLE " + TRENDS_TABLE + " ("
        //                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
        //                + USER_LINKED_TO + " INTEGER, "
        //                + DATE + " INTEGER, "
        //                + CALORIES + " INTEGER"
        //                + ")";
        //        database.execSQL(createTable);

        createTable =
                "CREATE TABLE "
                        + SAVED_WORKOUTS_TABLE
                        + " ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + SAVED_WORKOUTS_USER_LINKED_TO
                        + " INTEGER, "
                        + NAME
                        + " TEXT, "
                        + SETS
                        + " INTEGER, "
                        + LAST_TIME_PERFORMED
                        + " INTEGER, "
                        + WORKOUT_TYPE
                        + " TEXT"
                        + ")";
        database.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int previousVersion, int newVersion) {}

    public User add(User user) {
        SQLiteDatabase database = getWritableDatabase();
        user.setId(database.insert(TABLE_NAME, null, user.toContentValues()));
        return user;
    }

    public User getUser(int userID) {
        Cursor cursor =
                getReadableDatabase()
                        .rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = " + userID, null);
        return User.fromCursor(cursor);
    }

    public User modify(User user) {
        getWritableDatabase().insert(TABLE_NAME, null, user.toContentValues());
        return user;
    }

    public User update(User user) {
        int rowsUpdated =
                getWritableDatabase()
                        .update(
                                TABLE_NAME,
                                user.toContentValues(),
                                "id = ?",
                                new String[] {String.valueOf(user.getId())});
        if (rowsUpdated > 0) {
            return user;
        }
        return null;
    }
}
