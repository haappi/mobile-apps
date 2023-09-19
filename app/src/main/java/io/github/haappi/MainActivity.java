package io.github.haappi;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;

import io.github.haappi.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler.instance =  new DBHandler(MainActivity.this);

        Button button = findViewById(R.id.load_csv);

        button.setOnClickListener((view) -> {
            CSVReader.loadCSVFile("sample.csv");
            String allEntries = DBHandler.instance.getAll().stream().reduce("", (a, b) -> a + "\n" + b);
            Log.d("CSV File Content", allEntries);
        });
    }

}
