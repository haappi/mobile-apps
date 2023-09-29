package io.github.haappi.POJOS;

import static io.github.haappi.DBHandler.SAVED_WORKOUTS_TABLE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import io.github.haappi.DBHandler;

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
        savedWorkout.setLastTimePerformed(
                cursor.getLong(cursor.getColumnIndex("last_time_performed")));
        return savedWorkout;
    }

    public static SavedWorkout createSavedWorkout(SavedWorkout savedWorkout) {
        SQLiteDatabase database = DBHandler.getInstance().getWritableDatabase();
        savedWorkout.setId(
                database.insert(SAVED_WORKOUTS_TABLE, null, savedWorkout.toContentValues()));
        return savedWorkout;
    }

    public static SavedWorkout getSavedWorkout(int savedWorkoutId) {
        Cursor cursor =
                DBHandler.getInstance()
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT * FROM "
                                        + SAVED_WORKOUTS_TABLE
                                        + " WHERE id = "
                                        + savedWorkoutId,
                                null);

        if (cursor != null && cursor.moveToFirst()) {
            return SavedWorkout.fromCursor(cursor);
        }

        return null;
    }

    public static SavedWorkout updateSavedWorkout(SavedWorkout savedWorkout) {
        SQLiteDatabase database = DBHandler.getInstance().getWritableDatabase();
        database.update(
                SAVED_WORKOUTS_TABLE,
                savedWorkout.toContentValues(),
                "id = ?",
                new String[]{String.valueOf(savedWorkout.getId())});
        return savedWorkout;
    }

    public static void deleteSavedWorkout(int savedWorkoutId) {
        SQLiteDatabase database = DBHandler.getInstance().getWritableDatabase();
        database.delete(
                SAVED_WORKOUTS_TABLE, "id = ?", new String[]{String.valueOf(savedWorkoutId)});
    }

    public static List<SavedWorkout> getWorkoutsForUser(User user) {
        SQLiteDatabase database = DBHandler.getInstance().getReadableDatabase();
        String[] columns = {"id", "user_id", "name", "sets", "last_time_performed"};
        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(user.getId())};

        Cursor cursor =
                database.query(
                        SAVED_WORKOUTS_TABLE, columns, selection, selectionArgs, null, null, null);
        List<SavedWorkout> workouts = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                workouts.add(SavedWorkout.fromCursor(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return workouts;
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

    public void linkWorkoutToUser(User user) {
        this.setSavedWorkoutsUserLinkedTo(user.getId());
        SavedWorkout.createSavedWorkout(this);
    }
}
