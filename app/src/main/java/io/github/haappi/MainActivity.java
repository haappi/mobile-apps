package io.github.haappi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                                SQLiteDatabase database =
                                        DBHandler.getInstance().getWritableDatabase();

                                Student student1 = new Student("Student1 Name");
                                Student student2 = new Student("Student2 Name");

                                ClassObj class1 = new ClassObj("Class1 Name");
                                ClassObj class2 = new ClassObj("Class2 Name");

                                ContentValues enrollment1 = new ContentValues();
                                enrollment1.put(
                                        DBHandler.COLUMN_STUDENT_ID, student1.getStudentId());
                                enrollment1.put(DBHandler.COLUMN_CLASS_ID, class1.getClassId());
                                database.insert(DBHandler.TABLE_STUDENT_CLASSES, null, enrollment1);

                                ContentValues enrollment2 = new ContentValues();
                                enrollment2.put(
                                        DBHandler.COLUMN_STUDENT_ID, student2.getStudentId());
                                enrollment2.put(DBHandler.COLUMN_CLASS_ID, class1.getClassId());
                                database.insert(DBHandler.TABLE_STUDENT_CLASSES, null, enrollment2);

                                ContentValues enrollment3 = new ContentValues();
                                enrollment3.put(
                                        DBHandler.COLUMN_STUDENT_ID, student2.getStudentId());
                                enrollment3.put(DBHandler.COLUMN_CLASS_ID, class2.getClassId());
                                database.insert(DBHandler.TABLE_STUDENT_CLASSES, null, enrollment3);

                                Toast.makeText(
                                                MainActivity.this,
                                                student1.toString(),
                                                Toast.LENGTH_LONG)
                                        .show();
                            });
                });

        viewSavedButton.setOnClickListener(
                view -> {
                    Log.d(
                            "student 0",
                            DBHandler.getInstance().getStudentWithEnrolledClasses(0).toString());
                    //                    Log.d("all students",
                    // DBHandler.getInstance().getAllStudents().stream().collect(ArrayList::new),
                    // (list, entry) -> list.add(entry.toString)),
                    //
                    //                            ArrayList::addAll;);
                    //                    ArrayList<SubmitClass> all =
                    // DBHandler.getInstance().getAll();
                    //
                    //                    ArrayList<String> entries =
                    //                            all.stream()
                    //                                    .collect(
                    //                                            ArrayList::new,
                    //                                            (list, entry) ->
                    // list.add(entry.toString()),
                    //                                            ArrayList::addAll);
                    //
                    //                    ArrayAdapter<String> adapter =
                    //                            new ArrayAdapter<>(
                    //                                    MainActivity.this,
                    //                                    android.R.layout.simple_list_item_1,
                    //                                    entries);
                    //                    listView.setAdapter(adapter);
                });
    }
}
