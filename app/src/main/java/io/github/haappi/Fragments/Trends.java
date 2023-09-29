package io.github.haappi.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import io.github.haappi.DBHandler;
import io.github.haappi.POJOS.SavedWorkout;
import io.github.haappi.POJOS.User;
import io.github.haappi.POJOS.UserWorkout;
import io.github.haappi.R;
import io.github.haappi.WorkoutAdapater;

public class Trends extends Fragment {
private LineChart lineChart;
private LineChart sets;

    public Trends() {
        super(R.layout.trends_fragment);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trends_fragment, container, false);

        lineChart = view.findViewById(R.id.weightLine);
        sets = view.findViewById(R.id.muscleWeight);

        // Customize the chart appearance and data
        setupLineChart();
        loadChartData();

        return view;


    }

//    private void setupLineChart() {
//        lineChart.getDescription().setText("User Height / Weight Over Time");
//        lineChart.setDrawGridBackground(false);
//
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return "";
//            }
//        });
//
//        YAxis leftAxis = lineChart.getAxisLeft();
//        leftAxis.setAxisMinimum(0f);
//
//        YAxis rightAxis = lineChart.getAxisRight();
//        rightAxis.setEnabled(false);
//
//        Legend legend = lineChart.getLegend();
//        legend.setForm(Legend.LegendForm.LINE);
//    }
//
//    private void loadChartData() {
//        DBHandler dbHandler = DBHandler.getInstance();
//        User user = dbHandler.getUser(0);
//
//        List<Entry> heightEntries = new ArrayList<>();
//        List<Entry> weightEntries = new ArrayList<>();
//
//        LineDataSet heightDataSet = new LineDataSet(heightEntries, "Height");
//        heightDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        heightDataSet.setDrawCircles(true);
//        heightDataSet.setDrawValues(true);
//
//        LineDataSet weightDataSet = new LineDataSet(weightEntries, "Weight");
//        weightDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        weightDataSet.setDrawCircles(true);
//        weightDataSet.setDrawValues(true);
//
//        LineData lineData = new LineData(heightDataSet, weightDataSet);
//
//        lineChart.setData(lineData);
//
//        lineChart.invalidate();
//    }


    private void setupLineChart() {
        // Customize chart appearance
        sets.getDescription().setEnabled(false);
        sets.setDrawGridBackground(false);

        // Customize X-axis
        XAxis xAxis = sets.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Implement custom formatting for X-axis labels (e.g., workout names)
                return ""; // Replace with your formatting logic
            }
        });

        // Customize Y-axis
        YAxis leftAxis = sets.getAxisLeft();
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = sets.getAxisRight();
        rightAxis.setEnabled(false);

        // Customize chart legend
        Legend legend = sets.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
    }

    private void loadChartData() {
        List<SavedWorkout> savedWorkouts = SavedWorkout.getWorkoutsForUser(User.getUser(0));

        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < savedWorkouts.size(); i++) {
            SavedWorkout workout = savedWorkouts.get(i);
            entries.add(new Entry(i, workout.getSets()));
            labels.add(workout.getName());
        }

        LineDataSet dataSet = new LineDataSet(entries, "Sets Performed");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawCircles(true);
        dataSet.setDrawValues(true);

        LineData lineData = new LineData(dataSet);

        sets.setData(lineData);
        sets.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = Math.round(value);
                if (index >= 0 && index < labels.size()) {
                    return labels.get(index);
                }
                return "";
            }
        });

        sets.invalidate();
    }
}


