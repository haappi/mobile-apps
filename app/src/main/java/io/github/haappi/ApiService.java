package io.github.haappi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface ApiService {
    @GET("question")
    Call<List<Question>> getQuestions(
            @Query("limit") String param1,
            @Query("difficulty") int param2,
            @Query("category") String param3);
}
