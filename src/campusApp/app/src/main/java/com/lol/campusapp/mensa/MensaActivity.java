package com.lol.campusapp.mensa;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.lol.campusapp.NavigableActivity;
import com.lol.campusapp.R;

import java.util.List;


public class MensaActivity extends NavigableActivity {
    private Spinner canteenSpinner;
    private Spinner timeSpinner;
    private MensaDateRange selectedDateRange;
    private Canteen selectedCanteen;
    private RecyclerView mealRecyclerView;
    private TextView ingredientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensa);

        selectedDateRange = MensaDateRange.today;
        selectedCanteen = Canteen.lahnberge;

        initIngredientInfo();
        initCanteenSpinner();
        initTimeSpinner();
        initRecyclerView();

        updateDisplay();
    }

    private void initIngredientInfo() {
        ingredientInfo = findViewById(R.id.ingredientInfo);
        ingredientInfo.setText(MensaDataConnection.getIngredientInfo());
    }

    private void initRecyclerView() {
        mealRecyclerView = findViewById(R.id.mealRecyclerView);
    }

    private void initTimeSpinner() {
        timeSpinner = findViewById(R.id.timeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mensa_time_ranges, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        timeSpinner.setAdapter(adapter);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeSpinnerOnItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void timeSpinnerOnItemSelected(int position) {
        if (position == 0) {
            selectedDateRange = MensaDateRange.today;
        } else if (position == 1) {
            selectedDateRange = MensaDateRange.tomorrow;
        } else if (position == 2) {
            selectedDateRange = MensaDateRange.this_week;
        } else if (position == 3) {
            selectedDateRange = MensaDateRange.next_week;
        }
        updateDisplay();
    }

    private void initCanteenSpinner() {
        canteenSpinner = findViewById(R.id.canteenSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.canteen_options, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        canteenSpinner.setAdapter(adapter);

        canteenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                canteenSpinnerOnItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void canteenSpinnerOnItemSelected(int position) {
        if (position == 0) {
            selectedCanteen = Canteen.lahnberge;
        } else if (position == 1) {
            selectedCanteen = Canteen.erlenring;
        } else if (position == 2) {
            selectedCanteen = Canteen.bistro;
        } else if (position == 3) {
            selectedCanteen = Canteen.diner;
        }
        updateDisplay();
    }

    /** gets the menus from MenuDataConnection and displays them in the RecyclerView
     */
    private void updateDisplay() {
        List<Meal> meals = MensaDataConnection.getMeals(selectedCanteen, selectedDateRange);
        MealViewAdapter adapter = new MealViewAdapter(meals);
        mealRecyclerView.setAdapter(adapter);
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}