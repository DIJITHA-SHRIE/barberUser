package com.barberapp.barberuser.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacilityData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("facility_name")
    @Expose
    private String facilityName;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
