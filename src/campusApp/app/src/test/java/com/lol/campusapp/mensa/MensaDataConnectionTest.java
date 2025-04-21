package com.lol.campusapp.mensa;

import com.lol.campusapp.utils.DateTimeUtils;

import junit.framework.TestCase;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class MensaDataConnectionTest extends TestCase {

    public void testGetMeals() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        if (today != DayOfWeek.SATURDAY && today != DayOfWeek.SUNDAY) {
            assertFalse(MensaDataConnection.getMeals(Canteen.lahnberge, MensaDateRange.today).isEmpty());
        }

        assertFalse(MensaDataConnection.getMeals(Canteen.lahnberge, MensaDateRange.this_week).isEmpty());
    }

    public void testGetIngredientInfo() {
        String info = MensaDataConnection.getIngredientInfo();
        assertFalse(info.isEmpty());
    }
}