package com.barberapp.barberuser.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileMessage implements Parcelable {
    private String id;
    private String name;
    private String phone_no;
    private String mail_id;
    private String otp;
    private String pin;
    private String address;
    private String photo;
    private String referal_code;
    private String fw_reference_code;
    private String status;
    private String created_on;
    public ProfileMessage(){
        super();
    }
    protected ProfileMessage(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone_no = in.readString();
        mail_id = in.readString();
        otp = in.readString();
        pin = in.readString();
        address = in.readString();
        photo = in.readString();
        referal_code = in.readString();
        fw_reference_code = in.readString();
        status = in.readString();
        created_on = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phone_no);
        dest.writeString(mail_id);
        dest.writeString(otp);
        dest.writeString(pin);
        dest.writeString(address);
        dest.writeString(photo);
        dest.writeString(referal_code);
        dest.writeString(fw_reference_code);
        dest.writeString(status);
        dest.writeString(created_on);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfileMessage> CREATOR = new Creator<ProfileMessage>() {
        @Override
        public ProfileMessage createFromParcel(Parcel in) {
            return new ProfileMessage(in);
        }

        @Override
        public ProfileMessage[] newArray(int size) {
            return new ProfileMessage[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getReferal_code() {
        return referal_code;
    }

    public void setReferal_code(String referal_code) {
        this.referal_code = referal_code;
    }

    public String getFw_reference_code() {
        return fw_reference_code;
    }

    public void setFw_reference_code(String fw_reference_code) {
        this.fw_reference_code = fw_reference_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }
}
