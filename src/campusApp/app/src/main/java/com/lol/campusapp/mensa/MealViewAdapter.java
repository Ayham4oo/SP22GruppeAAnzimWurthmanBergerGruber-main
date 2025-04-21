package com.lol.campusapp.mensa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lol.campusapp.R;
import com.lol.campusapp.utils.DateTimeUtils;

import java.util.List;

public class MealViewAdapter extends RecyclerView.Adapter<MealViewAdapter.ViewHolder> {
    private List<Meal> meals;

    public MealViewAdapter(List<Meal> meals) {
        this.meals = meals;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mealTitle;
        private final TextView mealType;
        private final TextView mealDate;
        private final TextView mealPrice;

        public ViewHolder(View view) {
            super(view);

            mealTitle = view.findViewById(R.id.MealTitleView);
            mealType = view.findViewById(R.id.MealTypeView);
            mealDate = view.findViewById(R.id.MealDateView);
            mealPrice = view.findViewById(R.id.MealPriceView);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_recyclerview_row, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Meal meal = meals.get(position);
        viewHolder.mealTitle.setText(meal.getTitle());
        viewHolder.mealType.setText(meal.getType());

        String dateString = DateTimeUtils.getAbbrvDayOfWeek(meal.getDate()) + ", " +
                DateTimeUtils.printDate(meal.getDate());
        viewHolder.mealDate.setText(dateString);
        viewHolder.mealPrice.setText(meal.printPrice());
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
