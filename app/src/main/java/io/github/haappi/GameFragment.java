package io.github.haappi;

import static io.github.haappi.SettingsFragment.INFINITE_MODE_KEY;
import static io.github.haappi.SettingsFragment.OPTION1_KEY;
import static io.github.haappi.SettingsFragment.OPTION2_KEY;
import static io.github.haappi.SettingsFragment.PREFERENCES_NAME;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment {

    private ImageView backgroundImage;
    private TextView questionTextView;
    private TextView[] answers;
    private ArrayList<Object> questionMapping;
    private GestureDetector gestureDetector;

    private String type;
    private int difficutly;
    private boolean infiniteMode;

    public GameFragment() {}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFERENCES_NAME, 0);
        int selectedOption1 = preferences.getInt(OPTION1_KEY, 0); // gets option 1, or zero if null
        int selectedOption2 = preferences.getInt(OPTION2_KEY, 0);
        infiniteMode = preferences.getBoolean(INFINITE_MODE_KEY, false);

        String[] option1Values = getResources().getStringArray(R.array.option1_array);
        String[] option2Values = getResources().getStringArray(R.array.option2_array);

        type = option1Values[selectedOption1];
        difficutly = selectedOption2;

        fetchQuestions();

        View transparentOverlay = view.findViewById(R.id.transparentOverlay);
        GestureDetector gestureDetector =
                new GestureDetector(requireContext(), new GestureHandler());
        transparentOverlay.setOnTouchListener(
                (v, event) -> {
                    //            Log.d("touch", "touch event: " + event.toString());
                    return gestureDetector.onTouchEvent(event);
                });
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    private class GestureHandler extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 10; // Adjust this value as needed
        private static final int SWIPE_VELOCITY_THRESHOLD = 10; // Adjust this value as needed

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                // Horizontal swipe (left or right)
                if (Math.abs(diffX) > SWIPE_THRESHOLD
                        && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        // Right swipe
                        handleRightSwipe();
                    } else {
                        // Left swipe
                        handleLeftSwipe();
                    }
                }
            } else {
                // Vertical swipe (up or down)
                if (Math.abs(diffY) > SWIPE_THRESHOLD
                        && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        // Down swipe
                        handleDownSwipe();
                    } else {
                        // Up swipe
                        handleUpSwipe();
                    }
                }
            }
            Log.d(
                    "touch",
                    "onFling: "
                            + e1.toString()
                            + " "
                            + e2.toString()
                            + " "
                            + velocityX
                            + " "
                            + velocityY);
            return true;
        }
    }

    // Handle swipe gestures
    private void handleUpSwipe() {
        // Handle up swipe action
        showToast("Up Swipe Detected");
    }

    private void handleDownSwipe() {
        // Handle down swipe action
        showToast("Down Swipe Detected");
    }

    private void handleRightSwipe() {
        // Handle right swipe action
        showToast("Right Swipe Detected");
    }

    private void handleLeftSwipe() {
        // Handle left swipe action
        showToast("Left Swipe Detected");
    }

    private void showToast(String message) {
        Log.d("toast", "showToast: " + message);
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void displayQuestion(int questionIndex) {
        if (questionIndex >= 0 && questionIndex < questionMapping.size()) {
            Question question = (Question) questionMapping.get(questionIndex);
            questionTextView.setText(question.getQuestion());
        }
    }

    private void fetchQuestions() {
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://chicago.quack.boo/school/oldie/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Make the API request
        Call<List<Question>> call =
                apiService.getQuestions(infiniteMode ? "100" : "10", difficutly, type);
        call.enqueue(
                new Callback<>() {
                    @Override
                    public void onResponse(
                            Call<List<Question>> call, Response<List<Question>> response) {
                        if (response.isSuccessful()) {
                            List<Question> questions = response.body();

                            if (questions != null && !questions.isEmpty()) {
                                questionMapping.addAll(questions);
                                Log.d("questions", "onResponse: " + questions.toString());
                                displayQuestion(0);
                            }
                        } else {
                            try {
                                Log.e(
                                        "questions",
                                        "would you look at that it broke: "
                                                + response.errorBody().string());
                                showSnack("API request failed: " + response.errorBody().string());
                                Log.e("url", call.request().url().toString());

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Question>> call, Throwable t) {
                        // Handle API request failure
                    }
                });
    }

    private void showSnack(String message) {
        Snackbar snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
