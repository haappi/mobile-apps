package io.github.haappi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("question")
    Call<List<Question>> getQuestions(
            @Query("limit") String param1,
            @Query("difficulty") int param2,
            @Query("category") String param3
    );
}

