package com.lol.campusapp.mensa;

public enum MensaDateRange {
    today, tomorrow, this_week, next_week;

    public String getAttributeCode() {
        switch (this) {
            case today:
                return "#heute";
            case tomorrow:
                return "#morgen";
            case this_week:
                return "#diese_woche";
            case next_week:
                return "#nächste_woche";
        }
        return "";
    }

    public boolean isSingle() {
        switch (this) {
            case today:
            case tomorrow:
                return true;
            case this_week:
            case next_week:
                return false;
        }
        return false;
    }
}
