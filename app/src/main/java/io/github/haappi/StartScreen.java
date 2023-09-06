package io.github.haappi;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

public class StartScreen extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
    }

    public void startGame(View view) {
        Button playButton = findViewById(R.id.play_button);
        TextView textView1 = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);

        playButton.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);

        Fragment frag = new GameFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, frag);
        transaction.commit();
    }

    public void launchSettings(View view) {
        //    setChildrenVisibility(findViewById(R.id.start_screen), false);
        Button playButton = findViewById(R.id.play_button);
        TextView textView1 = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);

        playButton.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);

        Fragment frag = new SettingsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onBackPressed() {
        super.onBackPressed();
        setChildrenVisibility(findViewById(R.id.start_screen), true);
    }

    public static void setChildrenVisibility(View view, boolean visible) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();

            for (int i = 0; i < childCount; i++) {
                View childView = viewGroup.getChildAt(i);
                childView.setVisibility(visible ? View.VISIBLE : View.GONE);
                Log.d("childView", "childView: " + childView.toString() + " visible: " + visible);

                setChildrenVisibility(childView, visible);
            }
        }
    }
}
