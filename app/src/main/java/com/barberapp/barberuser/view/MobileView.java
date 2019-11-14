package com.barberapp.barberuser.view;

public interface MobileView extends BaseView {
    void  onSuccess(String mobilenumber);
    void  onFailed(String errorMsg);
    void onValidateUser(String mobilenumber);
    void onPwNotFound(String mobilenumber);
}
