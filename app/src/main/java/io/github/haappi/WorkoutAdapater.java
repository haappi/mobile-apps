package io.github.haappi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import io.github.haappi.POJOS.UserWorkout;

import java.util.List;

public class WorkoutAdapater extends ArrayAdapter<UserWorkout> {
    private final Context context;
    private final List<UserWorkout> workouts;

    public WorkoutAdapater(Context context, List<UserWorkout> workouts) {
        super(context, 0, workouts);
        this.context = context;
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Inflate or reuse a layout for each list item view
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context)
                            .inflate(R.layout.list_item_formatting_workout, parent, false);
        }

        // Get the UserWorkout object at the current position
        UserWorkout workout = getItem(position);

        // Bind the data from the UserWorkout object to the views in the layout
        if (workout != null) {
            TextView workoutNameTextView = convertView.findViewById(R.id.workoutTextView);

            // Set the data to the TextViews
            workoutNameTextView.setText(workout.getCustomName());
        }

        return convertView;
    }
}
