package io.github.haappi;

public class SubmitClass {
    private final String firstName;
    private final String contentToStore;
    private final long timestamp;

    public SubmitClass(String firstName, String contentToStore) {
        this.firstName = firstName;
        this.contentToStore = contentToStore;
        this.timestamp = System.currentTimeMillis();
    }

    public SubmitClass(String firstName, String content, long timestamp) {
        this.firstName = firstName;
        this.contentToStore = content;
        this.timestamp = timestamp;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getContentToStore() {
        return contentToStore;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return String.format("name=%s content=%s unix=%s", firstName, contentToStore, timestamp);
    }
}
