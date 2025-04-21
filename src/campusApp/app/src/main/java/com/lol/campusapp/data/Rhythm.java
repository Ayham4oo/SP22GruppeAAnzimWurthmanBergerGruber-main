package com.lol.campusapp.data;

public enum Rhythm {
    single, weekly, fortnightly, monthly, block;

    /** will return single if given str not in : "woch", "14tägl", "Einzel", "Block", "mon"
     */
    public static Rhythm valueOfGerman(String str) {
        System.out.println("angegeben = " + str);
        if (str.equals("woch")) {
            return weekly;
        } else if (str.equals("14tägl")) {
            return fortnightly;
        } else if (str.equals("Einzel")) {
            return single;
        } else if (str.equals("Block")) {
            return block;
        } else if (str.equals("mon")) {
            return monthly;
        } else {
            System.out.println("elsecase");
            return weekly;
        }
    }
}
