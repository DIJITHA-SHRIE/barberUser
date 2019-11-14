package com.barberapp.barberuser.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SaloonSearchResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<SaloonSearchData> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SaloonSearchData>  getData() {
        return data;
    }

    public void setData(ArrayList<SaloonSearchData>  data) {
        this.data = data;
    }
}
