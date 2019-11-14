package com.barberapp.barberuser.utils;

import java.util.Random;

public class MyRandomNumber {
    Random rand;

    int randmNo;

    public int getRandmNo() {
        return rand.nextInt(1000);
    }


    public static MyRandomNumber getOurInstance() {
        return ourInstance;
    }

    private static final MyRandomNumber ourInstance = new MyRandomNumber();

    public static MyRandomNumber getInstance() {
        return ourInstance;
    }

    private MyRandomNumber() {
        rand = new Random();
    }

}
