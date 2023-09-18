package io.github.haappi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class WorkoutFragment extends Fragment {
    private final static double PEEK_HEIGHT = 0.80;
//    public WorkoutFragment() {
//        super(R.layout.workout_fragment);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_fragment, container, false);

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(view);
        behavior.setPeekHeight((int) (getResources().getDisplayMetrics().heightPixels * PEEK_HEIGHT));

        return view;
    }

}
