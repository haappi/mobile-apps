package io.github.haappi;

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

import java.util.ArrayList;

public class GameFragment extends Fragment {

    private ImageView backgroundImage;
    private TextView questionTextView;
    private TextView[] answers;
    private ArrayList<Object> questionMapping;
    private GestureDetector gestureDetector;


    public GameFragment() {

    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View transparentOverlay = view.findViewById(R.id.transparentOverlay);
        GestureDetector gestureDetector = new GestureDetector(requireContext(), new GestureHandler());
        transparentOverlay.setOnTouchListener((v, event) -> {
            Log.d("touch", "touch event: " + event.toString());
            return gestureDetector.onTouchEvent(event);
        });
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    private class GestureHandler extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100; // Adjust this value as needed
        private static final int SWIPE_VELOCITY_THRESHOLD = 100; // Adjust this value as needed

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                // Horizontal swipe (left or right)
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
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
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        // Down swipe
                        handleDownSwipe();
                    } else {
                        // Up swipe
                        handleUpSwipe();
                    }
                }
            }
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}
