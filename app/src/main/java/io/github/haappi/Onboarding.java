package io.github.haappi;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Onboarding extends AppCompatActivity {
    public void changeToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
