package com.barberapp.barberuser.view;

import com.barberapp.barberuser.pojos.CheckSumReponse;

public interface BookingView extends BaseView {
    void bookingSucced(String msg);
    void bookingFailed(String err);
    void onSuccessCheckSum(CheckSumReponse checkSumReponse);
    void onFailedCheckSum(String err);
}
