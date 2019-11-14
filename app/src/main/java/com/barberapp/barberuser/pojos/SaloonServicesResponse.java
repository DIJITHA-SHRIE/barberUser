package com.barberapp.barberuser.pojos;

import java.util.ArrayList;

public class SaloonServicesResponse {
    private ArrayList<SaloonServices> data;
    private String message;
    private String code;
    private String op_time;
    private String clo_time;

    public String getOp_time() {
        return op_time;
    }

    public void setOp_time(String op_time) {
        this.op_time = op_time;
    }

    public String getClo_time() {
        return clo_time;
    }

    public void setClo_time(String clo_time) {
        this.clo_time = clo_time;
    }

    public ArrayList<SaloonServices> getData() {
        return data;
    }

    public void setData(ArrayList<SaloonServices> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
