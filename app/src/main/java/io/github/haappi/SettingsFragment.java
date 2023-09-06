package io.github.haappi;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private Spinner spinnerOption1;
    private Spinner spinnerOption2;
    private SwitchCompat infiniteModeSwitch;

    public static final String PREFERENCES_NAME = "TriviaPreferences";
    public static final String OPTION1_KEY = "Type";
    public static final String OPTION2_KEY = "Difficulty";
    public static final String INFINITE_MODE_KEY = "InfiniteMode";

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        //        setChildrenVisibility(view, true);

        spinnerOption1 = view.findViewById(R.id.spinnerOption1);
        spinnerOption2 = view.findViewById(R.id.spinnerOption2);
        infiniteModeSwitch = view.findViewById(R.id.switch1);

        // how do i make a dropdown in android studio and add options from my strings.xml
        ArrayAdapter<CharSequence> option1Adapter =
                ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.option1_array,
                        android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> option2Adapter =
                ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.option2_array,
                        android.R.layout.simple_spinner_item);

        option1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        option2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOption1.setAdapter(option1Adapter);
        spinnerOption2.setAdapter(option2Adapter);

        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFERENCES_NAME, 0);
        int selectedOption1 = preferences.getInt(OPTION1_KEY, 0); // gets option 1, or zero if null
        int selectedOption2 = preferences.getInt(OPTION2_KEY, 0);
        boolean infiniteMode = preferences.getBoolean(INFINITE_MODE_KEY, false);

        spinnerOption1.setSelection(selectedOption1);
        spinnerOption2.setSelection(selectedOption2);
        infiniteModeSwitch.setChecked(infiniteMode);

        spinnerOption1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> parentView,
                            View selectedItemView,
                            int position,
                            long id) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt(OPTION1_KEY, position);
                        editor.apply();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });

        spinnerOption2.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> parentView,
                            View selectedItemView,
                            int position,
                            long id) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt(OPTION2_KEY, position);
                        editor.apply();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });

        infiniteModeSwitch.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(INFINITE_MODE_KEY, isChecked);
                    editor.apply();
                });

        return view;
    }
}
