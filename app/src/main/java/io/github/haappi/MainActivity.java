package io.github.haappi;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, saveTextInput;
    private Button submitButton, viewSavedButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.name);
        saveTextInput = findViewById(R.id.whatToSave);
        submitButton = findViewById(R.id.add);
        viewSavedButton = findViewById(R.id.listContent);
        listView = findViewById(R.id.listView);

        DBHandler.getInstance(MainActivity.this);

        submitButton.setOnClickListener(
                view -> {
                    String firstName = nameInput.getText().toString();
                    String contentToStore = saveTextInput.getText().toString();

                    CompletableFuture.runAsync(
                            () -> {
                                SubmitClass entry = new SubmitClass(firstName, contentToStore);
                                DBHandler.getInstance().add(entry);
                                Toast.makeText(
                                                MainActivity.this,
                                                "Saved: " + entry,
                                                Toast.LENGTH_SHORT)
                                        .show();
                            });
                });

        viewSavedButton.setOnClickListener(
                view -> {
                    ArrayList<SubmitClass> all = DBHandler.getInstance().getAll();

                    ArrayList<String> entries =
                            all.stream()
                                    .collect(
                                            ArrayList::new,
                                            (list, entry) -> list.add(entry.toString()),
                                            ArrayList::addAll);

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(
                                    MainActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    entries);
                    listView.setAdapter(adapter);
                });
    }
}
