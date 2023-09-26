package io.github.haappi.POJOS;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

public class UserWorkout {
    private long id;
    private long userLinkedTo;
    private long time;
    private long duration;
    private String customName;
    private int workoutsPerformed;

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

    @SuppressLint("Range")
    public static UserWorkout fromCursor(Cursor cursor) {
        UserWorkout userWorkout = new UserWorkout();
        userWorkout.setId(cursor.getLong(cursor.getColumnIndex("id")));
        userWorkout.setUserLinkedTo(cursor.getLong(cursor.getColumnIndex("user_id")));
        userWorkout.setTime(cursor.getLong(cursor.getColumnIndex("time")));
        userWorkout.setDuration(cursor.getLong(cursor.getColumnIndex("duration")));
        userWorkout.setCustomName(cursor.getString(cursor.getColumnIndex("custom_name")));
        userWorkout.setWorkoutsPerformed(cursor.getInt(cursor.getColumnIndex("workouts_performed")));
        return userWorkout;
    }
}
