package io.github.haappi.POJOS;

public class UserSavedWorkouts {
    private int userLinkedTo;
    private String name;
    private String sets;
    private long lastTimePerformed;

    public UserSavedWorkouts(int userLinkedTo, String name, String sets, long lastTimePerformed) {
        this.userLinkedTo = userLinkedTo;
        this.name = name;
        this.sets = sets;
        this.lastTimePerformed = lastTimePerformed;
    }

    public int getUserLinkedTo() {
        return userLinkedTo;
    }

    public void setUserLinkedTo(int userLinkedTo) {
        this.userLinkedTo = userLinkedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public long getLastTimePerformed() {
        return lastTimePerformed;
    }

    public void setLastTimePerformed(long lastTimePerformed) {
        this.lastTimePerformed = lastTimePerformed;
    }
}
