package com.barberapp.barberuser.pojos;

import java.util.ArrayList;

public class SaloonResponse {
    private String code;
    private String message;
    private ArrayList<Saloon> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Saloon> getData() {
        return data;
    }

    public void setData(ArrayList<Saloon> data) {
        this.data = data;
    }
}
