package com.lol.campusapp.mensa;

public enum Canteen {
    erlenring, lahnberge, bistro, diner;

    public String getAttributeCode() {
        switch (this) {
            case erlenring:
                return "330";
            case lahnberge:
                return "340";
            case bistro:
                return "460";
            case diner:
                return "420";
        }
        return "";
    }
}
