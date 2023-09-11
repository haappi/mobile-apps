package io.github.haappi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, saveTextInput;
    private Button submitButton, viewSavedButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        saveTextInput = findViewById(R.id.saveText);
        submitButton = findViewById(R.id.submitButton);
        viewSavedButton = findViewById(R.id.viewSaved);
        textView = findViewById(R.id.textView);

        submitButton.setOnClickListener(view -> {
            String firstName = nameInput.getText().toString();
            String contentToStore = saveTextInput.getText().toString();


            Toast.makeText(MainActivity.this, "Submitted: " + firstName + " - " + contentToStore, Toast.LENGTH_SHORT).show();
        });

        viewSavedButton.setOnClickListener(view -> {
            new Thread() {
                @Override
                public void run() {

                    SubmitDao submitDao = AppDatabase.getInstance(MainActivity.this).classDao();
                    SubmitClass[] submitEntries = submitDao.getAll();

                    StringBuilder stringBuilder = new StringBuilder();
                    for (SubmitClass entry : submitEntries) {
                        stringBuilder.append(entry.toString()).append("\n");
                    }

                    textView.setText(stringBuilder.toString());
                }
            }.start();
        });

    }
}
