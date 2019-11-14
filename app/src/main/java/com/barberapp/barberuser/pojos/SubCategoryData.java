package com.barberapp.barberuser.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryData implements Parcelable {


    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_list")
    @Expose
    private String categoryList;
    @SerializedName("del_status")
    @Expose
    private String delStatus;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;

    protected SubCategoryData(Parcel in) {
        this.id = in.readString();
        this.categoryId = in.readString();
        this.categoryList = in.readString();
        this.status = in.readString();
        this.delStatus = in.readString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId2) {
        this.categoryId = categoryId2;
    }

    public String getCategoryList() {
        return this.categoryList;
    }

    public void setCategoryList(String categoryList2) {
        this.categoryList = categoryList2;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public String getDelStatus() {
        return this.delStatus;
    }

    public void setDelStatus(String delStatus2) {
        this.delStatus = delStatus2;
    }

    public String toString() {
        return this.categoryList;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.categoryId);
        parcel.writeString(this.categoryList);
        parcel.writeString(this.status);
        parcel.writeString(this.delStatus);
    }
    public static final Creator<SubCategoryData> CREATOR = new Creator<SubCategoryData>() {
        public SubCategoryData createFromParcel(Parcel in) {
            return new SubCategoryData(in);
        }

        public SubCategoryData[] newArray(int size) {
            return new SubCategoryData[size];
        }
    };
}
