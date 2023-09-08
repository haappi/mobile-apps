package io.github.haappi;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SubmitClass {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "content_saved")
    public String contentToStore;

    @ColumnInfo
    public long timestamp;

    public String toString() {
        return String.format("(%s) %s %s %s", uid, firstName, contentToStore, timestamp);
    }
}