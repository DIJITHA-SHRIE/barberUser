package com.barberapp.barberuser.utils;

import com.barberapp.barberuser.pojos.ServiePrice;

import java.util.ArrayList;

public class ServiceTon {
    private static final ServiceTon ourInstance = new ServiceTon();

    public static ServiceTon getInstance() {
        return ourInstance;
    }

    private ServiceTon() {
    }

    public static ServiceTon getOurInstance() {
        return ourInstance;
    }
    private ArrayList<ServiePrice> serviePrices;

    public ArrayList<ServiePrice> getServiePrices() {
        return serviePrices;
    }

    public void setServiePrices(ArrayList<ServiePrice> serviePrices) {
        this.serviePrices = serviePrices;
    }
}
