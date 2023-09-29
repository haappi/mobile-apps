package io.github.haappi.POJOS;

import static io.github.haappi.DBHandler.WORKOUTS_TABLE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import io.github.haappi.DBHandler;

public class UserWorkout {
    private long id;
    private long userLinkedTo;
    private long time;
    private long duration;
    private String customName;
    private int workoutsPerformed;

    @SuppressLint("Range")
    public static UserWorkout fromCursor(Cursor cursor) {
        UserWorkout userWorkout = new UserWorkout();
        userWorkout.setId(cursor.getLong(cursor.getColumnIndex("id")));
        userWorkout.setUserLinkedTo(cursor.getLong(cursor.getColumnIndex("user_id")));
        userWorkout.setTime(cursor.getLong(cursor.getColumnIndex("time")));
        userWorkout.setDuration(cursor.getLong(cursor.getColumnIndex("duration")));
        userWorkout.setCustomName(cursor.getString(cursor.getColumnIndex("custom_name")));
        userWorkout.setWorkoutsPerformed(
                cursor.getInt(cursor.getColumnIndex("workouts_performed")));
        return userWorkout;
    }

    public static UserWorkout createUserWorkout(UserWorkout userWorkout) {
        SQLiteDatabase database = DBHandler.getInstance().getWritableDatabase();
        userWorkout.setId(database.insert(WORKOUTS_TABLE, null, userWorkout.toContentValues()));
        return userWorkout;
    }

    public static UserWorkout getUserWorkout(int userWorkoutId) {
        Cursor cursor =
                DBHandler.getInstance()
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT * FROM " + WORKOUTS_TABLE + " WHERE id = " + userWorkoutId,
                                null);

        if (cursor != null && cursor.moveToFirst()) {
            return UserWorkout.fromCursor(cursor);
        }

        return null;
    }

    public static UserWorkout updateUserWorkout(UserWorkout userWorkout) {
        SQLiteDatabase database = DBHandler.getInstance().getWritableDatabase();
        database.update(
                WORKOUTS_TABLE,
                userWorkout.toContentValues(),
                "id = ?",
                new String[]{String.valueOf(userWorkout.getId())});
        return userWorkout;
    }

    public static void deleteUserWorkout(int userWorkoutId) {
        SQLiteDatabase database = DBHandler.getInstance().getWritableDatabase();
        database.delete(WORKOUTS_TABLE, "id = ?", new String[]{String.valueOf(userWorkoutId)});
    }

    public static List<UserWorkout> getWorkoutsBetweenTimestamps(long startTime, long endTime) {
        SQLiteDatabase database = DBHandler.getInstance().getReadableDatabase();
        String[] columns = {
                "id", "user_id", "time", "duration", "custom_name", "workouts_performed"
        };

        String selection = "time >= ? AND time <= ?";
        String[] selectionArgs = {String.valueOf(startTime), String.valueOf(endTime)};

        Cursor cursor =
                database.query(WORKOUTS_TABLE, columns, selection, selectionArgs, null, null, null);
        List<UserWorkout> workouts = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                workouts.add(UserWorkout.fromCursor(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return workouts;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("user_id", userLinkedTo);
        values.put("time", time);
        values.put("duration", duration);
        values.put("custom_name", customName);
        values.put("workouts_performed", workoutsPerformed);
        return values;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserLinkedTo() {
        return userLinkedTo;
    }

    public void setUserLinkedTo(long userLinkedTo) {
        this.userLinkedTo = userLinkedTo;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public int getWorkoutsPerformed() {
        return workoutsPerformed;
    }

    public void setWorkoutsPerformed(int workoutsPerformed) {
        this.workoutsPerformed = workoutsPerformed;
    }

    public void linkUserToWorkout(User user) {
        this.setUserLinkedTo(user.getId());
        UserWorkout.createUserWorkout(this);
    }
}
