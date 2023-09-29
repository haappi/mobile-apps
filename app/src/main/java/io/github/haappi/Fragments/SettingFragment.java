package io.github.haappi.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import io.github.haappi.DBHandler;
import io.github.haappi.POJOS.User;
import io.github.haappi.R;

public class SettingFragment extends Fragment {
    public SettingFragment() {
        super(R.layout.settings_fragment);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = User.getUser(0);
        ToggleButton toggleButton = view.findViewById(R.id.metricCustomaryToggle);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    user.setMetric(1);
                } else {
                    user.setMetric(0);
                }
                DBHandler.getInstance().update(user);
            }
        });


        AppCompatButton saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(vieww -> {
            DBHandler.getInstance().update(user);
            Toast.makeText(view.getContext(), "Saved!", Toast.LENGTH_SHORT);
        });


        EditText name = view.findViewById(R.id.setName);
        EditText height = view.findViewById(R.id.currentWeight);
        EditText weight = view.findViewById(R.id.currentHeight);

        name.setOnClickListener(v -> {
            user.setFirstName(name.getText().toString());
            DBHandler.getInstance().update(user);
            Toast.makeText(view.getContext(), "Saved!", Toast.LENGTH_SHORT);
        });

        height.setOnClickListener(v -> {
            user.setHeight(Integer.parseInt(height.getText().toString()));
            DBHandler.getInstance().update(user);
            Toast.makeText(view.getContext(), "Saved!", Toast.LENGTH_SHORT);
        });

        weight.setOnClickListener(v -> {
            user.setWeight(Integer.parseInt(weight.getText().toString()));
            DBHandler.getInstance().update(user);
            Toast.makeText(view.getContext(), "Saved!", Toast.LENGTH_SHORT);
        });

        Log.d("SettingFragment", "onViewCreated: ");
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.commit();
    }
}
