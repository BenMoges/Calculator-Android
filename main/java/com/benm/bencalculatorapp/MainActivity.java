package com.benm.bencalculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements CalculatorAdapter.ItemClickListener {

    CalculatorAdapter adapter;

    TextView tvDisplay;
    String inProgressDisplay = "";

    ArrayList<String> currentDisplay = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // data to populate the RecyclerView with
        ArrayList<String> calculatorInputs = new ArrayList<>();

        // first row
        calculatorInputs.add("7");
        calculatorInputs.add("8");
        calculatorInputs.add("9");
        calculatorInputs.add("÷");

        // second row
        calculatorInputs.add("4");
        calculatorInputs.add("5");
        calculatorInputs.add("6");
        calculatorInputs.add("x");

        // third row
        calculatorInputs.add("1");
        calculatorInputs.add("2");
        calculatorInputs.add("3");
        calculatorInputs.add("-");

        // fourth row
        calculatorInputs.add(".");
        calculatorInputs.add("0");
        calculatorInputs.add("=");
        calculatorInputs.add("+");

        // fifth and final row
        calculatorInputs.add("CLR");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvCalculator);
        // use grid layout so we can display 4 items per row
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CalculatorAdapter(this, calculatorInputs);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {

        // if input is an operator then add the in progress value and the operator value to the current display and display on screen
        if ((adapter.getItem(position).equals("÷") || adapter.getItem(position).equals("+") || adapter.getItem(position).equals("-") || adapter.getItem(position).equals("x")) && !inProgressDisplay.isEmpty()) {
            currentDisplay.add(inProgressDisplay);
            currentDisplay.add(adapter.getItem(position));

            tvDisplay.setText(currentDisplay.get(0) + " " + currentDisplay.get(1));

            inProgressDisplay = "";

        } else if (adapter.getItem(position).equals("=")) {

            // check if user has entered to valeus to operate against
            if (!inProgressDisplay.isEmpty()) {
                // if so, add to the current display
                currentDisplay.add(inProgressDisplay);

                if (currentDisplay.size() == 3) {
                    String newValue = "";

                    // get the new value and set the display
                    if (currentDisplay.get(1).equals("÷")) {
                        // divide
                        newValue = String.valueOf(Integer.valueOf(currentDisplay.get(0)) / Integer.valueOf(currentDisplay.get(2)));

                    } else if (currentDisplay.get(1).equals("+")) {
                        // add
                        newValue = String.valueOf(Integer.valueOf(currentDisplay.get(0)) + Integer.valueOf(currentDisplay.get(2)));

                    } else if (currentDisplay.get(1).equals("-")) {
                        // subtract
                        newValue = String.valueOf(Integer.valueOf(currentDisplay.get(0)) - Integer.valueOf(currentDisplay.get(2)));

                    } else if (currentDisplay.get(1).equals("x")) {
                        // multiply
                        newValue = String.valueOf(Integer.valueOf(currentDisplay.get(0)) * Integer.valueOf(currentDisplay.get(2)));
                    }

                    currentDisplay.clear();

                    inProgressDisplay = newValue;
                    tvDisplay.setText(newValue);

                } else {
                    // not enough values so don't evalute yet
                    Toast.makeText(this, "Error, not valid", Toast.LENGTH_SHORT).show();
                }
            }


        } else if (adapter.getItem(position).equals("CLR")) {
            // clear the values
            currentDisplay.clear();
            inProgressDisplay = "";
            tvDisplay.setText("");

        } else {
            // append the in progress display string with the input and then update the calculator display
            inProgressDisplay = inProgressDisplay.concat(adapter.getItem(position));
            tvDisplay.setText(inProgressDisplay);
        }

    }

}
