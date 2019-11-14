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
import com.barberapp.barberuser.presenter.MobilePresenter;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.MobileView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MobileNumberActivity extends AppCompatActivity implements MobileView {
    @BindView(R.id.btnContinue)
    Button btnContinue;
    MobilePresenter<MobileView> mobileViewMobilePresenter;
    @BindView(R.id.etMobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.progresBarMobile)
    ProgressBar progresBarMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);
        ButterKnife.bind(this);
        mobileViewMobilePresenter = new MobilePresenter<>();
        mobileViewMobilePresenter.setView(this);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber = etMobileNumber.getText().toString();
                if (!AppUtils.isOnLine(MobileNumberActivity.this)) {
                    Toast.makeText(MobileNumberActivity.this,AppConstants.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
                    return;
                }else  if (TextUtils.isEmpty(mobileNumber)){
                    etMobileNumber.setError("Please provide mobile number");
                    return;
                }else if (mobileNumber.length()!=10) {
                    etMobileNumber.setError("Invalid Mobile number");
                    return;
                }else {
                    etMobileNumber.setError(null);
                    btnContinue.setEnabled(false);
                    btnContinue.setSelected(true);
                    mobileViewMobilePresenter.validateUser(mobileNumber);
                }
            }
        });
    }

    @Override
    public void onSuccess(String mobileNumber) {
        btnContinue.setEnabled(false);
        btnContinue.setSelected(false);
        Intent intent = new Intent(MobileNumberActivity.this,SignupActivity.class);
        intent.putExtra(AppConstants.EXTRA_MOBILE,mobileNumber);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void onFailed(String errorMsg) {
        btnContinue.setEnabled(true);
        btnContinue.setSelected(false);
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidateUser(String mobilenumber) {
        btnContinue.setEnabled(false);
        btnContinue.setSelected(false);
        Intent intent = new Intent(MobileNumberActivity.this,LoginActivity.class);
        intent.putExtra(AppConstants.EXTRA_MOBILE,mobilenumber);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void onPwNotFound(String mobilenumber) {
        mobileViewMobilePresenter.requestOtp(mobilenumber);
    }

    @Override
    public void showLoading(boolean isLoaing) {
        if (isLoaing){
            progresBarMobile.setVisibility(View.VISIBLE);
        }else {
            progresBarMobile.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        btnContinue.setEnabled(true);
        btnContinue.setSelected(false);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        mobileViewMobilePresenter.onDestroy();
        super.onDestroy();
    }
}
