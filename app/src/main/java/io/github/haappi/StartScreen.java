package io.github.haappi;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.snackbar.Snackbar;

import io.github.haappi.databinding.ActivityMainBinding;

public class StartScreen extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //        NavController navController =
        //                Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //        appBarConfiguration = new
        // AppBarConfiguration.Builder(navController.getGraph()).build();
        //        NavigationUI.setupActionBarWithNavController(this, navController,
        // appBarConfiguration);

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

    }


}
