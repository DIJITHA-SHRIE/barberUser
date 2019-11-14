package com.barberapp.barberuser.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.pojos.User;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etName)
    EditText etName;
    String mobileNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mobileNumber = getIntent().getStringExtra(AppConstants.EXTRA_MOBILE);
        etMobile.setText(mobileNumber);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobilenumber = etMobile.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mobilenumber)){
                    etMobile.setError("Invalid Mobile number");
                    return;
                }else if (mobilenumber.length()!=10){
                    etMobile.setError("Invalid Mobile number");
                    return;
                }else if (TextUtils.isEmpty(name)){
                    etName.setError("Name can't be blank");
                    return;
                }else if (TextUtils.isEmpty(email)){
                    etEmail.setError("Email can't be blank");
                    return;
                }else if (!AppUtils.isValidEmail(email)){
                    etEmail.setError("Invalid email format");
                    return;
                }else if (TextUtils.isEmpty(password)){
                    etPassword.setError("Password can't be blank");
                    return;
                }else if(!AppUtils.isOnLine(SignupActivity.this)){
                    Toast.makeText(SignupActivity.this, AppConstants.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    etPassword.setError(null);
                    etEmail.setError(null);
                    etName.setError(null);
                    etMobile.setError(null);
                    User user = new User();
                    user.setEmail(email);
                    user.setMobileNumber(mobilenumber);
                    user.setName(name);
                    user.setPassword(password);
                    if (user!=null){
                        Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
                        intent.putExtra(AppConstants.EXTRA_USER,user);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                    }
                }

            }
        });
    }
}
