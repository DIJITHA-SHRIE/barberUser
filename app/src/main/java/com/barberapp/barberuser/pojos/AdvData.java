package com.barberapp.barberuser.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvData {
    @SerializedName("image")
    @Expose
    private String image;

    public String getImage() {
        return this.image;
    }

    public void setImage(String image2) {
        this.image = image2;
    }


}
