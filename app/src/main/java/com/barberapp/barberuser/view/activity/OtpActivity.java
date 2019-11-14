package com.barberapp.barberuser.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.pojos.User;
import com.barberapp.barberuser.presenter.VerifyOtpPresenter;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.view.OtpView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtpActivity extends AppCompatActivity implements OtpView {
    @BindView(R.id.btnVerify)
    Button btnVerify;
    @BindView(R.id.progresBarMobile)
    ProgressBar progresBarMobile;
    private VerifyOtpPresenter<OtpView> viewVerifyOtpPresenter;
    private User user;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etOTP)
    EditText etOTP;
    private AppSharedPrefference appSharedPrefference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        user = getIntent().getParcelableExtra(AppConstants.EXTRA_USER);
        appSharedPrefference = new AppSharedPrefference(this);
        if (user!=null){
            etMobile.setText(user.getMobileNumber());
        }
        viewVerifyOtpPresenter = new VerifyOtpPresenter<>();
        viewVerifyOtpPresenter.setView(this);
        btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = etOTP.getText().toString().trim();
                if (TextUtils.isEmpty(otp)){
                    etOTP.setError("Please provide otp.");
                    return;
                }else if (TextUtils.isEmpty(etMobile.getText().toString().trim())){
                    etMobile.setError("Please enter Mobile number");
                    return;
                }else {
                    if (user!=null){
                        Map<String,String> map = new HashMap<>(5);
                        map.put("mobile_no",user.getMobileNumber());
                        map.put("otp",etOTP.getText().toString().trim());
                        map.put("full_name",user.getName());
                        map.put("email_id",user.getEmail());
                        map.put("password",user.getPassword());
                        viewVerifyOtpPresenter.verifyOtp(map);
                    }
                }

            }
        });
    }

    @Override
    public void onSuccess(String msg) {
        btnVerify.setEnabled(true);
        appSharedPrefference.saveMobileNumber(user.getMobileNumber());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Verified", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(OtpActivity.this, MainBottomNavActivity.class);
        intent.putExtra(AppConstants.EXTRA_MOBILE,user.getMobileNumber());
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void onFailed(String eerorMsg) {
        btnVerify.setEnabled(true);
        Toast.makeText(this, eerorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading(boolean isLoaing) {
        btnVerify.setEnabled(false);
        if (isLoaing){
            progresBarMobile.setVisibility(View.VISIBLE);
        }else {
            progresBarMobile.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        btnVerify.setEnabled(true);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        viewVerifyOtpPresenter.onDestroy();
        super.onDestroy();
    }
}
