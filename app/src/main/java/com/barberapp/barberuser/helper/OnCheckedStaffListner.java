package com.barberapp.barberuser.helper;

import com.barberapp.barberuser.pojos.SaloonServices;

public interface OnCheckedStaffListner {
    void onCheckedStaff(SaloonServices saloonServices, int position, boolean isChecked);
}
