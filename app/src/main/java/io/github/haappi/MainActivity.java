package io.github.haappi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.haappi.Fragments.MainFragment;
import io.github.haappi.Fragments.Profile;
import io.github.haappi.Fragments.SettingFragment;
import io.github.haappi.Fragments.Trends;
import io.github.haappi.Fragments.WorkoutFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler.init(this);
        setContentView(R.layout.main_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.add_workout);

        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.exit_app) {
                        finish();
                        return true;
                    } else if (item.getItemId() == R.id.trends_button) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new Trends())
                                .addToBackStack(null)
                                .commit();
                        return true;
                    } else if (item.getItemId() == R.id.profile_button) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new Profile())
                                .addToBackStack(null)
                                .commit();
                        return true;
                    } else if (item.getItemId() == R.id.add_workout) {
                        // chcek if the screen is already on the workout fragment
                        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                                instanceof WorkoutFragment) {
                            Log.d("MainActivity", "onCreate: already on workout fragment");
                            return true;
                        } else {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new MainFragment())
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        }
                    } else if (item.getItemId() == R.id.settings_button) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new SettingFragment())
                                .addToBackStack(null)
                                .commit();
                        return true;
                    } else {
                        return false;
                    }
                });
    }
}
