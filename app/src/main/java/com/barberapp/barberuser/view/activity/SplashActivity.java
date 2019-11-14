package com.barberapp.barberuser.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.utils.AppUtils;

import java.util.List;

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getName();
    private Handler handler;
    private Button btngetStarted;
    private AppSharedPrefference appSharedPrefference;

    public static final int REQUEST_MULTIPLE_PERMISSION = 301;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appSharedPrefference = new AppSharedPrefference(this);
        handler = new Handler();
        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, IndicatorsActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);*/
        btngetStarted = findViewById(R.id.btngetStarted);
        btngetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (AppUtils.checkPermission(Manifest.permission.READ_PHONE_STATE, SplashActivity.this)) {
                        goNext();
                    } else {
                        AppUtils.requestPermission(SplashActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_MULTIPLE_PERMISSION);
                    }
                } else {
                    goNext();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_MULTIPLE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goNext();
            }
        }
    }

    public void goNext() {
        if (TextUtils.isEmpty(appSharedPrefference.getMobileNumber()) && !appSharedPrefference.isLoggedIn()){
            Intent intent = new Intent(SplashActivity.this, MobileNumberActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();

        }else if (!TextUtils.isEmpty(appSharedPrefference.getMobileNumber()) && appSharedPrefference.isLoggedIn()){
            Intent intent = new Intent(SplashActivity.this, MainBottomNavActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        }
        else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        }
    }
}
