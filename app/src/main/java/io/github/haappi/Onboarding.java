package io.github.haappi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Onboarding extends Fragment {
    public void goBackToMain() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    public Onboarding() {
        super(R.layout.onboarding);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onboarding, container, false);

        Button savedWorkouts = view.findViewById(R.id.lets_go_button);

        savedWorkouts.setOnClickListener(
                vieww -> {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    // Set the custom animations
                    transaction.setCustomAnimations(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left,
                            R.anim.slide_in_right,
                            R.anim.slide_out_left);

                    // Replace the current fragment with the new fragment
                    transaction.replace(R.id.fragment_container, new OnboardingTwo());
                    transaction.commit();
                });

        return view;
    }
}
