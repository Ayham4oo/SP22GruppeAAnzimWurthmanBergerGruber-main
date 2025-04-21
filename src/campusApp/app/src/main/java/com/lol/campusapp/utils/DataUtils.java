package com.lol.campusapp.utils;

import com.lol.campusapp.data.User;

/**
 * administers the current user
 * implemented following singleton pattern
 */
public class DataUtils {

    public static final DataUtils instance = new DataUtils();

    private User currentUser;

    private DataUtils(){}

    /**
     * @param user to be set
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
