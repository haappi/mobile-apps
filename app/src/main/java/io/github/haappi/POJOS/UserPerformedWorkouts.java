package io.github.haappi.POJOS;

public class UserPerformedWorkouts {
    private final int userLinkedTo;
    private final long time;
    private final long duration;
    private final String name;
    private final String sets;

    public UserPerformedWorkouts(
            int userLinkedTo, long time, long duration, String name, String sets) {
        this.userLinkedTo = userLinkedTo;
        this.time = time;
        this.duration = duration;
        this.name = name;
        this.sets = sets;
    }

    public int getUserLinkedTo() {
        return userLinkedTo;
    }

    public long getTime() {
        return time;
    }

    public long getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getSets() {
        return sets;
    }
}
