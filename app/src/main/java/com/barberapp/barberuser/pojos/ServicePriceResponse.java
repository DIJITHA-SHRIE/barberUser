package com.barberapp.barberuser.pojos;

import java.util.ArrayList;

public class ServicePriceResponse {
    private String message;
    private ArrayList<ServiePrice> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ServiePrice> getData() {
        return data;
    }

    public void setData(ArrayList<ServiePrice> data) {
        this.data = data;
    }
}
