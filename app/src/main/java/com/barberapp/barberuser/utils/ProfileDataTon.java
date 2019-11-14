package com.barberapp.barberuser.utils;

import com.barberapp.barberuser.pojos.ProfileMessage;

public class ProfileDataTon {
    private static final ProfileDataTon ourInstance = new ProfileDataTon();

    public static ProfileDataTon getInstance() {
        return ourInstance;
    }

    private ProfileDataTon() {
    }
    private ProfileMessage profileMessage;

    public static ProfileDataTon getOurInstance() {
        return ourInstance;
    }

    public ProfileMessage getProfileMessage() {
        return profileMessage;
    }

    public void setProfileMessage(ProfileMessage profileMessage) {
        this.profileMessage = profileMessage;
    }
}
