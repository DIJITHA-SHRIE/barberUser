package com.barberapp.barberuser.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPrefference {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public AppSharedPrefference(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("BarberUser",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveMobileNumber(String mobileNumber){
        editor.putString(AppConstants.EXTRA_MOBILE,mobileNumber);
        editor.apply();
    }
    public String getMobileNumber(){
        return sharedPreferences.getString(AppConstants.EXTRA_MOBILE,"");
    }
    public void setLogin(boolean isLogin)
    {
        editor.putBoolean(AppConstants.EXTRA_ISLOGGEDIN,isLogin);
        editor.apply();
    }
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(AppConstants.EXTRA_ISLOGGEDIN,false);
    }
    public void clearSharedPreff(){
        editor.clear();
        editor.commit();
    }
    public void saveTempMobile(String mobile)
    {
        editor.putString(AppConstants.EXTRA_TEMP_MOBILE,mobile);
        editor.commit();
    }
    public String getTempMobile(){
        return sharedPreferences.getString(AppConstants.EXTRA_TEMP_MOBILE,"");
    }

    public void saveFBToken(String token){
        editor.putString(Constants.FIREBASE_TOKEN,token);
        editor.commit();
    }

    public String getFbToken(){
        return sharedPreferences.getString(Constants.FIREBASE_TOKEN,"");
    }
}
