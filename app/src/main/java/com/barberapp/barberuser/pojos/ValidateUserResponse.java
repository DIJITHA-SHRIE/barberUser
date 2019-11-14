package com.barberapp.barberuser.pojos;

import java.util.ArrayList;

public class ValidateUserResponse {
    private ArrayList<ValidateUser> data;
    private String message;

    public ArrayList<ValidateUser> getData() {
        return data;
    }

    public void setData(ArrayList<ValidateUser> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
