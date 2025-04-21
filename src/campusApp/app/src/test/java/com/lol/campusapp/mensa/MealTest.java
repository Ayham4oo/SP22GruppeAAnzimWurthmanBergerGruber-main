package com.lol.campusapp.mensa;

import static org.junit.Assert.*;

import org.junit.Test;

public class MealTest {

    @Test
    public void printPrice() {
        Meal meal = new Meal();
        meal.setPrice(2.43f);
        assertEquals(meal.printPrice(), "2,43 €");

        meal.setPrice(0.4f);
        assertEquals(meal.printPrice(), "0,40 €");
    }
}