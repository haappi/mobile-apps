package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView jokeTextView;
    private String jokeTypeSelected = "Any";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "App started");
        /*
       ^ the above variable is from the file named `activity_main` in the `res/layout` direectory.
       you can create more of those for more "screens" in your app.

       You can right click the elment > constraints to make sure the element is in a specific spot
         */

        button = findViewById(R.id.api_call);
        jokeTextView = findViewById(R.id.textView);

        button.setOnClickListener(view -> {
            Log.d("MainActivity", "Button clicked");
            fetchRandomJoke();
        });

        findViewById(R.id.button2).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SecondScreen.class);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            startActivity(intent);
        });
    }

    private void fetchRandomJoke() {
        String url = "https://v2.jokeapi.dev/joke/Any?blacklistFlags=nsfw,religious,political,racist,sexist,explicit";
        // imagine i didnt have all these blacklists

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String joke = response.getString("setup") + "\n\n" + response.getString("delivery");
                        jokeTextView.setText(joke);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        Volley.newRequestQueue(this).add(request); // line 4 of AndroidManifest.xml
    }
}
