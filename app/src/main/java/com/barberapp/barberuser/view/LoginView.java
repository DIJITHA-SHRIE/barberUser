package com.barberapp.barberuser.view;

public interface LoginView extends BaseView{
    void onLoginSuccess(String msg);
    void onLoginFailed(String err);
}
