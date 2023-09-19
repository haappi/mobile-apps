package io.github.haappi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler.instance = new DBHandler(this);

        Button button = findViewById(R.id.load_csv);

        button.setOnClickListener(
                (view) -> { // this is a lambda expression, just easier instead of the whole @Override nonsense
                    CSVReader.loadCSVFile(MainActivity.this, "sample.csv");
                    String allEntries =
                            DBHandler.instance.getAll().stream().reduce("", (a, b) -> a + "\n" + b);
                    Log.d("CSV File Content", allEntries);
                });

        /*
        * To get the CSV file to load, you need to put it in the assets folder. `src/main/assets/file.csv`
        * You can look at the method call of CSVReader#loadCSVFile to see how it works (hint, it's just BufferedReader from CSA)
        *
        *
        * */
    }
}
