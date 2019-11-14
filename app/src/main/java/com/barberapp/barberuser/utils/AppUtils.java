package com.barberapp.barberuser.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class AppUtils {
    public static boolean checkPermission(String permission, Context context) {
        int statusCode = ContextCompat.checkSelfPermission(context, permission);
        return statusCode == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(AppCompatActivity activity, String[] permission, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission[0])) {
            Toast.makeText(activity, "Application need permission", Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(activity, permission, requestCode);
    }

    public static void requestPermission(Fragment fragment, String[] permission, int requestCode) {
        fragment.requestPermissions(permission, requestCode);
    }

    public static boolean isOnLine(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivity.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivity.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivity != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network",
                                    "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String showError(Throwable t) {
        if (t instanceof SocketTimeoutException){
            return AppConstants.EXTRA_TIME_OUT;
        }else if (t instanceof UnknownHostException){
            return AppConstants.HOST_UNKNOWN;
        }else if (t instanceof IOException){
            return  AppConstants.NETWORK_ERROR;
        }
        else {
            Log.e("ERRR",t.getMessage());
            return t.getMessage();
        }
    }
    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static String distance(double lat1, double lon1, double lat2, double lon2) {
        try{
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1))
                    * Math.sin(deg2rad(lat2))
                    + Math.cos(deg2rad(lat1))
                    * Math.cos(deg2rad(lat2))
                    * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String d = decimalFormat.format(dist);
            return Double.valueOf(d).toString();
        }catch (Exception e){
            //e.printStackTrace();
            return "";
        }

    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
     /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            List<SubscriptionInfo> subscription = SubscriptionManager.from(getApplicationContext()).getActiveSubscriptionInfoList();
            for (int i = 0; i < subscription.size(); i++) {
                SubscriptionInfo info = subscription.get(i);
                Log.d(TAG, "number " + info.getNumber());
                Log.d(TAG, "network name : " + info.getCarrierName());
                Log.d(TAG, "country iso " + info.getCountryIso());
            }
        }*/
     private static final String FORMAT = "%02d:%02d:%02d";
    public static String parseTime(long milliseconds) {
         return String.format(FORMAT,
                 TimeUnit.MILLISECONDS.toHours(milliseconds),
                 TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(
                         TimeUnit.MILLISECONDS.toHours(milliseconds)),
                 TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(
                         TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
     }
}
