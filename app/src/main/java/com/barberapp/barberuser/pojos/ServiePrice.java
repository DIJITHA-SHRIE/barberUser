package com.barberapp.barberuser.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiePrice implements Parcelable {
    private String id;
    private String saloon_name;
    private String owned_by;
    private String branch;
    private String style_name;
    private String pac_name;
    private String cat_name;
    private String price;
    private String status;
    private String created_date;
    private String times;

    protected ServiePrice(Parcel in) {
        id = in.readString();
        saloon_name = in.readString();
        owned_by = in.readString();
        branch = in.readString();
        style_name = in.readString();
        pac_name = in.readString();
        cat_name = in.readString();
        price = in.readString();
        status = in.readString();
        created_date = in.readString();
        times = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(saloon_name);
        dest.writeString(owned_by);
        dest.writeString(branch);
        dest.writeString(style_name);
        dest.writeString(pac_name);
        dest.writeString(cat_name);
        dest.writeString(price);
        dest.writeString(status);
        dest.writeString(created_date);
        dest.writeString(times);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ServiePrice> CREATOR = new Creator<ServiePrice>() {
        @Override
        public ServiePrice createFromParcel(Parcel in) {
            return new ServiePrice(in);
        }

        @Override
        public ServiePrice[] newArray(int size) {
            return new ServiePrice[size];
        }
    };

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
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

    public String getOwned_by() {
        return owned_by;
    }

    public void setOwned_by(String owned_by) {
        this.owned_by = owned_by;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public String getPac_name() {
        return pac_name;
    }

    public void setPac_name(String pac_name) {
        this.pac_name = pac_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
