package io.github.haappi;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
                    CSVReader.loadCSVFile(MainActivity.this, "sample.csv", DBHandler.instance.getWritableDatabase());
                    Toast.makeText(this, "Loaded CSV file into Database", Toast.LENGTH_SHORT).show();
                });


        Button load = findViewById(R.id.show_entries);
        load.setOnClickListener(
                (view) -> {
                    String allEntries =
                            DBHandler.instance.getAll().stream().reduce("", (a, b) -> a + "\n" + b);

                    ListView listView = findViewById(R.id.listView);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, DBHandler.instance.getAll());
                    listView.setAdapter(adapter);

                    Log.d("CSV File Content", allEntries);
                });

        findViewById(R.id.delete_all).setOnClickListener(view -> {
            DBHandler.instance.deleteAll();
            Toast.makeText(this, "Deleted all entries", Toast.LENGTH_SHORT).show();
        });

        /*
        * To get the CSV file to load, you need to put it in the assets folder. `src/main/assets/file.csv`
        * You can look at the method call of CSVReader#loadCSVFile to see how it works (hint, it's just BufferedReader from CSA)
        *
        *
        * */
    }
}
