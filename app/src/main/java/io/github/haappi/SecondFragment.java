package io.github.haappi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import io.github.haappi.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavHostFragment.findNavController(SecondFragment.this)
                                .navigate(R.id.action_SecondFragment_to_FirstFragment);
                    }
                });

        view.setOnTouchListener(
                (view1, motionEvent) -> {
                    view1.performClick();
                    switch (motionEvent.getAction()) {
                        case (MotionEvent.ACTION_DOWN):
                            Log.d("SecondFragment", "Action was DOWN");
                            return true;

                        case (MotionEvent.ACTION_MOVE):
                            Log.d("SecondFragment", "Action was MOVE");
                            return true;

                        case (MotionEvent.ACTION_UP):
                            Log.d("SecondFragment", "Action was UP");
                            return true;

                        case (MotionEvent.ACTION_CANCEL):
                            Log.d("SecondFragment", "Action was CANCEL");
                            return true;

                        case (MotionEvent.ACTION_OUTSIDE):
                            Log.d(
                                    "SecondFragment",
                                    "Movement occurred outside bounds "
                                            + "of current screen element");
                            return true;

                        default:
                            Log.d("SecondFragment", "Action was " + motionEvent.getAction());
                            return true;
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
