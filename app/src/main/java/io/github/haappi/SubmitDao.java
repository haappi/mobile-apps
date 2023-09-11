package io.github.haappi;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SubmitDao {

    @Query("SELECT * FROM SubmitClass")
    SubmitClass[] getAll();

    @Query("SELECT * FROM SubmitClass WHERE first_name LIKE :first AND " +
            "content_saved LIKE :content LIMIT 1")
    SubmitClass findByName(String first, String content);

    @Insert
    void insertAll(SubmitClass... submits);

    @Query("DELETE FROM SubmitClass")
    void deleteAll();
}
