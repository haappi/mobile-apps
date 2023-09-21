package io.github.haappi;

import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private final String DB_NAME = "data";
    private final int DB_VERSION = 1;

    private final String TABLE_NAME = "users";
    private final String WORKOUTS_TABLE = "user_workouts";
    private final String TRENDS_TABLE = "user_trends";

    private final String FIRST_NAME = "first_name";
    private final String LAST_NAME = "last_name";
    private final String HEIGHT = "height";
    private final String WEIGHT = "weight";
    private final boolean METRIC_OR_CUSTOMARY = true;
    private final String CURRENT_GOAL = "current_goal";
    private final String DB_NAME = "data";

}
