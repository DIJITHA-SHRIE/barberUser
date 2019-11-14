package com.barberapp.barberuser.view;

import com.barberapp.barberuser.pojos.AdvResponse;
import com.barberapp.barberuser.pojos.Saloon;

import java.util.ArrayList;

public interface NearMeView extends BaseView{
    void onFecthSallons(ArrayList<Saloon>saloons);
    void onFailedFetch(String err);
    void onFetchAdv(AdvResponse advResponse);

    void onFailedAdv(String str);

}
