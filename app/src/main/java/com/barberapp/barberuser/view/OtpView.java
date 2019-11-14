package com.barberapp.barberuser.view;

public interface OtpView extends BaseView {
    void onSuccess(String msg);
    void onFailed(String eerorMsg);
}
