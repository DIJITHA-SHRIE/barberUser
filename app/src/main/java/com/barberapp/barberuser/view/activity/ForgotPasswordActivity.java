package com.barberapp.barberuser.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.view.fragment.EnterMobileNumberFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends AppCompatActivity {
    @BindView(R.id.txtForgotPwdOrChPwd)
    TextView txtForgotPwdOrChPwd;
    String userPasswordOPtion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        userPasswordOPtion = getIntent().getStringExtra(AppConstants.EXTRA_USER_PASSWORD_OPTION);
        if (AppConstants.EXTRA_CHANGE_PASSWORD.equals(userPasswordOPtion)){
            txtForgotPwdOrChPwd.setText("Change Password");
        }
        if (AppConstants.EXTRA_FORGOT_PASSWORD.equals(userPasswordOPtion)){
            txtForgotPwdOrChPwd.setText("Forgot Password");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frameForgotPwd,new EnterMobileNumberFragment()).commit();
    }
}
