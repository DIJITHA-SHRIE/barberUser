package com.barberapp.barberuser.view.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.view.fragment.NearMeFragment;
import com.barberapp.barberuser.view.fragment.ProfileFragment;
import com.barberapp.barberuser.view.fragment.SearchForServiceFrag;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;


import java.util.List;

public class MainBottomNavActivity extends BaseActivity {

    private static final String TAG = MainBottomNavActivity.class.getName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final int REQUEST_CODE_LOCATION = 201;
    private static final int REQUEST_CHECK_SETTINGS = 202;
    private static final int REQUEST_LOCATION_SEARCH = 203;
    private TextView mTextMessage;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mlocationRequest;
    private Location mLocation;
    Geocoder geocoder;
    List<Address> addresses;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private ImageView imgAllMaps;
    private Toolbar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_nearme:
                    mTextMessage.setText(BaseActivity.myAddress);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_contaner, new NearMeFragment()).commit();
                    return true;
                case R.id.navigation_offers:
                    mTextMessage.setText(BaseActivity.myAddress);
                    return true;
                case R.id.navigation_search:
                    mTextMessage.setText(BaseActivity.myAddress);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_contaner, new SearchForServiceFrag()).commit();
                    return true;
                case R.id.navigation_fav:
                    mTextMessage.setText(BaseActivity.myAddress);
                    return true;
                case R.id.navigation_account:
                    mTextMessage.setText(BaseActivity.myAddress);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame_contaner, new ProfileFragment()).commit();
                    startActivity(new Intent(MainBottomNavActivity.this,UpdateNavigationActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom_nav);
        toolbar = findViewById(R.id.toolbar);
        mTextMessage = toolbar.findViewById(R.id.message);
        //imgAllMaps = toolbar.findViewById(R.id.imgAllMaps);
        ImageView filter_img =(ImageView)toolbar.findViewById(R.id.imgAllfilter);
        filter_img.setVisibility(View.GONE);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        mTextMessage.setText(BaseActivity.myAddress);
        //new AppSharedPrefference(this).saveMobileNumber("9668452233");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_contaner, new NearMeFragment()).commit();
        /*if (checkPlayServices()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AppUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, MainBottomNavActivity.this)) {
                    getLastKnownLocation();
                } else {
                    AppUtils.requestPermission(MainBottomNavActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
                }
            } else {
                getLastKnownLocation();
            }

        }*/
        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBottomNavActivity.this,PlaceSearchActivity.class);
                startActivityForResult(intent,REQUEST_LOCATION_SEARCH);
                /*try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(MainBottomNavActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }*/
            }
        });
    }
/*
    */




    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     *//*
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void getLastKnownLocation() {
        createLocationRequest();
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    if (ActivityCompat.checkSelfPermission(MainBottomNavActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainBottomNavActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(MainBottomNavActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLocation = location;
                            }
                        }
                    });
                    //return;
                } else {
                    for (Location location : locationResult.getLocations()) {
                        mLocation = location;
                    }
                }

                try {
                    geocoder = new Geocoder(MainBottomNavActivity.this);
                    addresses = geocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String subLocality = addresses.get(0).getSubLocality();
                    String subAdminArea = addresses.get(0).getSubAdminArea();
                    String locality = addresses.get(0).getLocality();
                    String adminArea = addresses.get(0).getAdminArea();
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                            *//*String postalCode = addresses.get(0).getPostalCode();
                            String knownName = addresses.get(0).getFeatureName();*//*
                    //Toast.makeText(MainBottomNavActivity.this, address+city+state+country+ mLocation.getLatitude()+"//"+mLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    mTextMessage.setText(subLocality + "," + locality + "," + subAdminArea);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        };
    }

    protected void createLocationRequest() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainBottomNavActivity.this);
        mlocationRequest = LocationRequest.create();
        mlocationRequest.setInterval(2 * 60 * 1000);
        mlocationRequest.setFastestInterval(2 * 60 * 1000);
        mlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mlocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                //getLastKnownLocation();
                if (ActivityCompat.checkSelfPermission(MainBottomNavActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainBottomNavActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fusedLocationProviderClient.requestLocationUpdates(mlocationRequest, mLocationCallback, null);
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainBottomNavActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_OK) {
            //createLocationRequest();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(mlocationRequest, mLocationCallback, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation();
            } else {
                Toast.makeText(this, "App need Location permision to Work properly", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void stopLocationUpdates() {
        if (mLocationCallback!=null)
        fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (mlocationRequest!=null && mLocationCallback!=null)
        fusedLocationProviderClient.requestLocationUpdates(mlocationRequest,
                mLocationCallback,
                null *//* Looper *//*);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==REQUEST_LOCATION_SEARCH){
            if (data!=null && AppConstants.EXTRA_LATLNG.equals(data.getAction())){
                double searchedLat =data.getDoubleExtra(AppConstants.EXTRA_LAT,0);
                double searchedLng = data.getDoubleExtra(AppConstants.EXTRA_LNG,0);
                String address = data.getStringExtra(AppConstants.EXTRA_ADDRESS);
                mTextMessage.setText(address);
                NearMeFragment nearMeFragment = new NearMeFragment();
                Bundle bundle = new Bundle();
                bundle.putDouble(AppConstants.EXTRA_LAT,searchedLat);
                bundle.putDouble(AppConstants.EXTRA_LNG,searchedLng);
                nearMeFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contaner, nearMeFragment).commit();
            }
        }
    }


}
