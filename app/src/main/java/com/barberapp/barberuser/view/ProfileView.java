package com.barberapp.barberuser.view;

import com.barberapp.barberuser.pojos.ValidateUser;

public interface ProfileView extends BaseView {
    void onProfileSuccess(ValidateUser validateUser);
    void  onProfileFailed(String error);
    void onProfileUpdated();
}
