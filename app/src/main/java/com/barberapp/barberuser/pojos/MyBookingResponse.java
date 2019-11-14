package com.barberapp.barberuser.pojos;

import java.util.ArrayList;

public class MyBookingResponse {
    private ArrayList<MyBooking>data;
    private String message;

    public ArrayList<MyBooking> getData() {
        return data;
    }

    public void setData(ArrayList<MyBooking> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
