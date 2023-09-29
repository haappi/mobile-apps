package io.github.haappi.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("MainFragment", "onViewCreated: ");
        //        displayName = getView().findViewById(R.id.displayName);
        //        displayName.setText("Hey, " + Database.getDBHandler().getFirstName());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        //        fragmentTransaction.replace(R.id.stopwatchContainer, StopwatchFragment);
        fragmentTransaction.commit();
    }
}
