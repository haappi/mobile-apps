package io.github.haappi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class OnboardingTwo extends Fragment {
    public void goBackToMain() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    public OnboardingTwo() {
        super(R.layout.onboarding_name);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onboarding_name, container, false);

        Button savedWorkouts = view.findViewById(R.id.lets_go_button);

        savedWorkouts.setOnClickListener(
                vieww -> {
                    EditText name = view.findViewById(R.id.name_input);
                    EditText age = view.findViewById(R.id.age_input);

                    SharedViewModel.getInstance().setData("name", name.getText().toString());
                    SharedViewModel.getInstance().setData("age", age.getText().toString());

                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    // Set the custom animations
                    transaction.setCustomAnimations(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left,
                            R.anim.slide_in_right,
                            R.anim.slide_out_left);

                    // Replace the current fragment with the new fragment
                    transaction.replace(R.id.fragment_container, new OnboardingThree());
                    transaction.commit();
                });

        return view;
    }
}
