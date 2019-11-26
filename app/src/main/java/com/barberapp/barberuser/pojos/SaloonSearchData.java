package com.barberapp.barberuser.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaloonSearchData {
    public String getOwned_by() {
        return owned_by;
    }

    public void setOwned_by(String owned_by) {
        this.owned_by = owned_by;
    }

    @SerializedName("owned_by")
    @Expose
    private String owned_by;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("expertise_id")
    @Expose
    private String expertiseId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("owned_by_name")
    @Expose
    private String ownedByName;
    @SerializedName("saloon_name")
    @Expose
    private String saloonName;
    @SerializedName("TotalPrice")
    @Expose
    private String totalPrice;

    public String getImage() {
        return this.image;
    }

    public void setImage(String image2) {
        this.image = image2;
    }

    public String getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(String totalPrice2) {
        this.totalPrice = totalPrice2;
    }

    public String getSaloonName() {
        return this.saloonName;
    }

    public void setSaloonName(String saloonName2) {
        this.saloonName = saloonName2;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo2) {
        this.mobileNo = mobileNo2;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location2) {
        this.location = location2;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude2) {
        this.longitude = longitude2;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude2) {
        this.latitude = latitude2;
    }

    public String getOwnedByName() {
        return this.ownedByName;
    }

    public void setOwnedByName(String ownedByName2) {
        this.ownedByName = ownedByName2;
    }

    public String getExpertiseId() {
        return this.expertiseId;
    }

    public void setExpertiseId(String expertiseId2) {
        this.expertiseId = expertiseId2;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance2) {
        this.distance = distance2;
    }
}
