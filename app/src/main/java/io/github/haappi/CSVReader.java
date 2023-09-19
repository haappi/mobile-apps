package io.github.haappi;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    public static void loadCSVFile(String path) {
        DBHandler dbHandler = DBHandler.instance;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            reader.readLine(); // ignore the header line

            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(","); // read the comma separated line
                // values = {bobby,18,content}

                String name = values[0]; // read the zero index (name)
                int age = Integer.parseInt(values[1]); // read the age index
                String customContent = values[2]; // i think you know what this does

                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHandler.COLUMN_NAME, name);
                contentValues.put(DBHandler.COLUMN_AGE, age);
                contentValues.put(DBHandler.COLUMN_CONTENT, customContent);

                dbHandler
                        .getWritableDatabase()
                        .insert(
                                DBHandler.TABLE_NAME,
                                null,
                                contentValues); // who knows what the 2nd arg is for
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            dbHandler.close();
        }
    }
}
