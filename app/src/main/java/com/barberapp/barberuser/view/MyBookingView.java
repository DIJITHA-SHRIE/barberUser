package com.barberapp.barberuser.view;

import com.barberapp.barberuser.pojos.MyBooking;

import java.util.ArrayList;

public interface MyBookingView extends BaseView {
    void ongettingBookings(ArrayList<MyBooking> bookings);
    void onNotGettingBookings(String err);
}
