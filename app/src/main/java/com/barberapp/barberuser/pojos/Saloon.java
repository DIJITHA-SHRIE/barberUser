package com.barberapp.barberuser.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Saloon implements Parcelable {
    private String id;
    private String saloon_name;
    private String branch;
    private String mail_id;
    private String mobile_no;
    private String location;
    private String status;
    private String owned_by;
    private String distance;
    private String latitude;
    private String longitude;
    private String image;
    private String owner;
    public Saloon(){
        super();
    }
    protected Saloon(Parcel in) {
        id = in.readString();
        saloon_name = in.readString();
        branch = in.readString();
        mail_id = in.readString();
        mobile_no = in.readString();
        location = in.readString();
        status = in.readString();
        owned_by = in.readString();
        distance = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        image = in.readString();
        owner = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(saloon_name);
        dest.writeString(branch);
        dest.writeString(mail_id);
        dest.writeString(mobile_no);
        dest.writeString(location);
        dest.writeString(status);
        dest.writeString(owned_by);
        dest.writeString(distance);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(image);
        dest.writeString(owner);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Saloon> CREATOR = new Creator<Saloon>() {
        @Override
        public Saloon createFromParcel(Parcel in) {
            return new Saloon(in);
        }

        @Override
        public Saloon[] newArray(int size) {
            return new Saloon[size];
        }
    };

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaloon_name() {
        return saloon_name;
    }

    public void setSaloon_name(String saloon_name) {
        this.saloon_name = saloon_name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwned_by() {
        return owned_by;
    }

    public void setOwned_by(String owned_by) {
        this.owned_by = owned_by;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
