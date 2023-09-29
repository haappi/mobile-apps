package io.github.haappi.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import io.github.haappi.R;

public class MainFragment extends Fragment {
    public MainFragment() {
        super(R.layout.main_fragment);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        Button startWorkoutButton = view.findViewById(R.id.start_button);
        startWorkoutButton.setOnClickListener(
                vieww -> {
                    TimerFragment timerFragment = new TimerFragment();
                    FragmentTransaction fragmentTransaction =
                            requireActivity().getSupportFragmentManager().beginTransaction();
                    timerFragment.show(fragmentTransaction, timerFragment.getTag());
                });

        Button trendsButton = view.findViewById(R.id.view_trends);
        trendsButton.setOnClickListener(
                vieww -> {
                    requireActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new Trends())
                            .addToBackStack(null)
                            .commit();
                });

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startWorkoutButton = view.findViewById(R.id.start_button);

        Log.d("MainFragment", "onViewCreated: ");
        //        displayName = getView().findViewById(R.id.displayName);
        //        displayName.setText("Hey, " + Database.getDBHandler().getFirstName());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        //        fragmentTransaction.replace(R.id.stopwatchContainer, StopwatchFragment);
        fragmentTransaction.commit();
    }
}
