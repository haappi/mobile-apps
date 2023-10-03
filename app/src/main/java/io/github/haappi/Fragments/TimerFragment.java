package io.github.haappi.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import io.github.haappi.R;

public class TimerFragment extends BottomSheetDialogFragment {
    private TextView timer;
    private RecyclerView recyclerView; // for hortionzal scrolling between excrises
    private ListView workoutsInList;
    private Button endWorkout;

    public TimerFragment() {
        throw new RuntimeException("Not implelemted yet dingus");
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.timer_layout, container, false);
//
//        timer = view.findViewById(R.id.timer);
        return null;
    }
}
