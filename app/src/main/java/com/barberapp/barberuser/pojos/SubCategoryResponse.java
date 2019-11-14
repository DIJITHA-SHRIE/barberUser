package com.barberapp.barberuser.pojos;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubCategoryResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<SubCategoryData> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SubCategoryData>  getData() {
        return data;
    }

    public void setData(ArrayList<SubCategoryData>  data) {
        this.data = data;
    }


}
