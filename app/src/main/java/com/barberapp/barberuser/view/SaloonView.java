package com.barberapp.barberuser.view;

import com.barberapp.barberuser.pojos.SaloonMember;
import com.barberapp.barberuser.pojos.SaloonServices;
import com.barberapp.barberuser.pojos.SaloonServicesResponse;
import com.barberapp.barberuser.pojos.ServiePrice;

import java.util.ArrayList;

public interface SaloonView extends BaseView{
    void onSaloonServicesFetch(ArrayList<SaloonServices> saloonServices);
    void onSallonServicesetchedrror(String errMsg);
    void onMemberFetcheed(ArrayList<SaloonMember> saloonMembers);
    void onMemberFetchedError(String err);
    void onFetchedServicePrices(ArrayList<ServiePrice> serviePriceArrayList,String serviceName);
    void onFetchServicePriceFaied(String err);
    void showLoadingForPrice(boolean isLoading);
    void showSericeTiming(String opepanTime,String closeTime);
    void showProgress(boolean isProgress);
    void bookingSucced(String msg);
    void bookingFailed(String errrBookingMsg);
}
