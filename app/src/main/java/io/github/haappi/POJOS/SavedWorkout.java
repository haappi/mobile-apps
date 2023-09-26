package io.github.haappi.POJOS;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

public class SavedWorkout {
    private long id;
    private long savedWorkoutsUserLinkedTo;
    private String name;
    private int sets;
    private long lastTimePerformed;

    @SuppressLint("Range")
    public static SavedWorkout fromCursor(Cursor cursor) {
        SavedWorkout savedWorkout = new SavedWorkout();
        savedWorkout.setId(cursor.getLong(cursor.getColumnIndex("id")));
        savedWorkout.setSavedWorkoutsUserLinkedTo(cursor.getLong(cursor.getColumnIndex("user_id")));
        savedWorkout.setName(cursor.getString(cursor.getColumnIndex("name")));
        savedWorkout.setSets(cursor.getInt(cursor.getColumnIndex("sets")));
        savedWorkout.setLastTimePerformed(cursor.getLong(cursor.getColumnIndex("last_time_performed")));
        return savedWorkout;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSavedWorkoutsUserLinkedTo() {
        return savedWorkoutsUserLinkedTo;
    }

    public void setSavedWorkoutsUserLinkedTo(long savedWorkoutsUserLinkedTo) {
        this.savedWorkoutsUserLinkedTo = savedWorkoutsUserLinkedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public long getLastTimePerformed() {
        return lastTimePerformed;
    }

    public void setLastTimePerformed(long lastTimePerformed) {
        this.lastTimePerformed = lastTimePerformed;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("user_id", savedWorkoutsUserLinkedTo);
        values.put("name", name);
        values.put("sets", sets);
        values.put("last_time_performed", lastTimePerformed);
        return values;
    }

}
