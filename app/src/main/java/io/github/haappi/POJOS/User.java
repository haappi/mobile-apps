package io.github.haappi.POJOS;

import android.annotation.SuppressLint;
import android.database.Cursor;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int height;
    private int weight;
    private boolean metric; // true = metric, false = customary 0 = metric, 1 = customary
    private String currentGoal;

    public int getId() {
        return id;
    }

    public User(int id, String firstName, String lastName, int height, int weight, boolean metric, String currentGoal) {
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isMetric() {
        return metric;
    }

    public String getCurrentGoal() {
        return currentGoal;
    }
}
