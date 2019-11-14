package com.barberapp.barberuser.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AdvResponse {
    @SerializedName("data")
    @Expose
    private ArrayList<AdvData> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public ArrayList<AdvData> getData() {
        return this.data;
    }

    public void setData(ArrayList<AdvData> data2) {
        this.data = data2;
    }


}
