package com.barberapp.barberuser.utils;

import com.barberapp.barberuser.pojos.User;

public class UserTon {
    private static final UserTon ourInstance = new UserTon();

    public static UserTon getInstance() {
        return ourInstance;
    }

    private UserTon() {
    }
    private User user;

    public static UserTon getOurInstance() {
        return ourInstance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
