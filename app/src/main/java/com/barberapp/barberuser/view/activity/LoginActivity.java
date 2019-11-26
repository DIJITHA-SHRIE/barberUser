package com.barberapp.barberuser.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.presenter.LoginPresenter;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginView {
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etpassword)
    EditText etpassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.progresBarMobile)
    ProgressBar progresBarMobile;
    @BindView(R.id.txtForgotPassword)
    TextView txtForgotPassword;
    private LoginPresenter<LoginView> loginViewLoginPresenter;
    private AppSharedPrefference appSharedPrefference;
    private String mobile,fb_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mobile = getIntent().getStringExtra(AppConstants.EXTRA_MOBILE);
        if (!TextUtils.isEmpty(mobile)){
            etMobile.setText(mobile);
        }
        appSharedPrefference = new AppSharedPrefference(this);
        fb_token=appSharedPrefference.getFbToken();
        Log.i("FB_TOKEN",fb_token);
        loginViewLoginPresenter = new LoginPresenter<>();
        loginViewLoginPresenter.setView(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = etMobile.getText().toString().trim();
                String password = etpassword.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)){
                    etMobile.setError("Please enter mobile number");
                    return;
                }else if (TextUtils.isEmpty(password)){
                    etpassword.setError("Please enter password");
                    return;
                }else if (!AppUtils.isOnLine(LoginActivity.this)){
                    Toast.makeText(LoginActivity.this, AppConstants.NETWORK_ERROR
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    etpassword.setError(null);
                    etMobile.setError(null);
                    loginViewLoginPresenter.login(mobile,password,fb_token);
                }
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                intent.putExtra(AppConstants.EXTRA_USER_PASSWORD_OPTION,AppConstants.EXTRA_FORGOT_PASSWORD);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onLoginSuccess(String msg) {
        appSharedPrefference.setLogin(true);
        appSharedPrefference.saveMobileNumber(msg);
        Intent intent=new Intent(LoginActivity.this,MainBottomNavActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed(String err) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        loginViewLoginPresenter.onDestroy();
        super.onDestroy();
    }
}
