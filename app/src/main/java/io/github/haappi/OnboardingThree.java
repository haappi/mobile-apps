package io.github.haappi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import io.github.haappi.POJOS.User;

public class OnboardingThree extends Fragment {
    public void goBackToMain() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onboarding_weight_and_height, container, false);

        Button savedWorkouts = view.findViewById(R.id.lets_go_button);

        savedWorkouts.setOnClickListener(
                vieww -> {
                    EditText height = view.findViewById(R.id.height_input);
                    EditText weight = view.findViewById(R.id.weight_input);

                    ToggleButton state = view.findViewById(R.id.toggleButton);

                    SharedViewModel.getInstance().setData("height", height.getText().toString());
                    SharedViewModel.getInstance().setData("weight", weight.getText().toString());
                    SharedViewModel.getInstance().setData("state", state.isChecked() ? 1 : 0);

                    User user = new User();
                    user.setFirstName((String) SharedViewModel.getInstance().getData("name"));
                    user.setMetric((Integer) SharedViewModel.getInstance().getData("state"));
                    user.setHeight(
                            Integer.parseInt(
                                    (String) SharedViewModel.getInstance().getData("height")));
                    user.setWeight(
                            Integer.parseInt(
                                    (String) SharedViewModel.getInstance().getData("weight")));
                    User.createUser(user);
                    //            BottomNavigationView bottomNavigationView =
                    // view.findViewById(R.id.nav_bar);
                    //            bottomNavigationView.setVisibility(View.VISIBLE);
                    goBackToMain();
                });

        return view;
    }
}
