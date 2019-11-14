package com.barberapp.barberuser.pojos;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("specialize_name")
    @Expose
    private String specializeName;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecializeName() {
        return specializeName;
    }

    public void setSpecializeName(String specializeName) {
        this.specializeName = specializeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return specializeName;
    }
}
