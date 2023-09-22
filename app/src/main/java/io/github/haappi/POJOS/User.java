package io.github.haappi.POJOS;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int height;
    private int weight;
    private boolean metric;
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
