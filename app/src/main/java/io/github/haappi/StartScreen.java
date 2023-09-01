package io.github.haappi;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;

import io.github.haappi.databinding.ActivityMainBinding;

public class StartScreen extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

//        NavController navController =
//                Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAnchorView(R.id.fab)
                                .setAction("Action", null)
                                .show();
                    }
                });
//        AppCompatButton playButton = findViewById(R.id.play_button);
//        playButton.setOnClickListener(event -> {
//            Log.d("button", "play button pressed");
//        });


        RelativeLayout rectangularBox = findViewById(R.id.rectangularBox);
        gestureDetector = new GestureDetector(this, new GestureHandler());

        rectangularBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private final class GestureHandler extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e2.getX() - e1.getX();
            float deltaY = e2.getY() - e1.getY();

            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                // Horizontal swipe
                if (deltaX > 0) {
                    // Right to left swipe
                    Toast.makeText(StartScreen.this, "Right to Left Swipe", Toast.LENGTH_SHORT).show();
                } else {
                    // Left to right swipe
                    Toast.makeText(StartScreen.this, "Left to Right Swipe", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Vertical swipe
                if (deltaY > 0) {
                    // Down to up swipe
                    Toast.makeText(StartScreen.this, "Down to Up Swipe", Toast.LENGTH_SHORT).show();
                } else {
                    // Up to down swipe
                    Toast.makeText(StartScreen.this, "Up to Down Swipe", Toast.LENGTH_SHORT).show();
                }
            }

            return true;
        }

    }

}
