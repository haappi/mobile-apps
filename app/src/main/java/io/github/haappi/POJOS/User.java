package io.github.haappi.POJOS;

import static io.github.haappi.DBHandler.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.github.haappi.DBHandler;

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private int height;
    private int weight;
    private boolean metric; // true = metric, false = customary 0 = metric, 1 = customary
    private String currentGoal;

    public User(
            long id,
            String firstName,
            String lastName,
            int height,
            int weight,
            boolean metric,
            String currentGoal) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.weight = weight;
        this.metric = metric;
        this.currentGoal = currentGoal;
    }

    public User() {
    }

    @SuppressLint("Range")
    public static User fromCursor(Cursor cursor) {
        User user = new User();
        user.id = cursor.getInt(cursor.getColumnIndex("id"));
        user.firstName = cursor.getString(cursor.getColumnIndex("first_name"));
        user.lastName = cursor.getString(cursor.getColumnIndex("last_name"));
        user.height = cursor.getInt(cursor.getColumnIndex("height"));
        user.weight = cursor.getInt(cursor.getColumnIndex("weight"));
        user.metric = cursor.getInt(cursor.getColumnIndex("metric")) == 0;
        user.currentGoal = cursor.getString(cursor.getColumnIndex("current_goal"));
        return user;
    }

    public static User createUser(User user) {
        SQLiteDatabase database = DBHandler.getInstance().getWritableDatabase();
        user.setId(database.insert(TABLE_NAME, null, user.toContentValues()));
        return user;
    }

    public static User getUser(int userId) {
        Cursor cursor =
                DBHandler.getInstance()
                        .getReadableDatabase()
                        .rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = " + userId, null);

        if (cursor != null && cursor.moveToFirst()) {
            return User.fromCursor(cursor);
        }

        return null;
    }

    public static User updateUser(User user) {
        SQLiteDatabase database = DBHandler.getInstance().getWritableDatabase();
        database.update(
                TABLE_NAME,
                user.toContentValues(),
                "id = ?",
                new String[]{
                        String.valueOf(user.getId())
                }); // doing it this way to prevent SQL injection
        return user;
    }

    public static void deleteUser(int userId) {
        SQLiteDatabase database = DBHandler.getInstance().getWritableDatabase();
        database.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(userId)});
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isMetric() {
        return metric;
    }

    public void setMetric(int metric) {
        this.metric = metric == 0;
    }

    public String getCurrentGoal() {
        return currentGoal;
    }

    public void setCurrentGoal(String currentGoal) {
        this.currentGoal = currentGoal;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", firstName);
        contentValues.put("last_name", lastName);
        contentValues.put("height", height);
        contentValues.put("weight", weight);
        contentValues.put("metric", metric ? 0 : 1);
        contentValues.put("current_goal", currentGoal);
        return contentValues;
    }
}
