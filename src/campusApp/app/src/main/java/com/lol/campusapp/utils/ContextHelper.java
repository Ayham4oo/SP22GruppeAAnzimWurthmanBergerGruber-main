package com.lol.campusapp.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Provides context to non Activity classes that interact with the DB (they need a context to do so)
 */
public class ContextHelper extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context instance;

    public ContextHelper(){
        instance = this;
    }

    /**
     * @return current application context
     */
    public static Context getContext(){
        return instance;
    }

    /**
     * sets a new context
     * @param context to be set
     */
    public static void setContext(Context context) {
        instance = context;
    }
}
