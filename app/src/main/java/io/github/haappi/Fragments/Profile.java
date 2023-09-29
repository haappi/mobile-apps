package io.github.haappi.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import io.github.haappi.POJOS.User;
import io.github.haappi.POJOS.UserWorkout;
import io.github.haappi.R;
import io.github.haappi.WorkoutAdapater;

import java.util.List;

public class Profile extends Fragment {
    private static final double PEEK_HEIGHT = 0.80;

    public Profile() {
        super(R.layout.profile_fragment);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        TextView nameView = view.findViewById(R.id.nameView);
        TextView workoutCount = view.findViewById(R.id.workoutsPastWeek);
        ListView savedWorkouts = view.findViewById(R.id.listView);

        User user = User.getUser(0);

        nameView.setText("Hey, " + user.getFirstName());
        // get count of workouts from the last seven days
        long sevenDaysAgo = System.currentTimeMillis() - (604800 * 1000);
        List<UserWorkout> workouts =
                UserWorkout.getWorkoutsBetweenTimestamps(System.currentTimeMillis(), sevenDaysAgo);

        WorkoutAdapater adapter = new WorkoutAdapater(view.getContext(), workouts);
        savedWorkouts.setAdapter(adapter);

        return view;
    }
}
