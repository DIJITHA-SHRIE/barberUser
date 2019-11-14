package com.barberapp.barberuser.pojos;

import java.util.ArrayList;

public class MemberReponse {
    private ArrayList<SaloonMember> data;
    private String message;

    public ArrayList<SaloonMember> getData() {
        return data;
    }

    public void setData(ArrayList<SaloonMember> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
