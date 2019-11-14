package com.barberapp.barberuser.view.activity;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.utils.AppConstants;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceSearchActivity extends BaseActivity implements OnMapReadyCallback {
    public static final String TAG = PlaceSearchActivity.class.getName();
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private boolean isMapInflated;
    private GoogleMap googleMap;
    PlaceAutocompleteFragment autocompleteFragment;
    SupportMapFragment mapFragment;
    private Place myPlace;
    private FrameLayout frame_set_location;
    private Location mLocation;
    private String mAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.placeMap);
        mapFragment.getMapAsync(this);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .build();
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                myPlace = place;
                if (isMapInflated){
                    showLocationOnMap(myPlace,googleMap);
                }else {
                    mapFragment.getMapAsync(PlaceSearchActivity.this);
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        frame_set_location = findViewById(R.id.frame_set_location);
        frame_set_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }*/

    }

    private void showLocationOnMap(Place place,GoogleMap mGoogleMap) {
        if (mGoogleMap!=null){
            mGoogleMap.clear();
            mGoogleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(""+place.getName())).showInfoWindow();
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(place.getLatLng())      // Sets the center of the map to Mountain View
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                myPlace = place;
                if (isMapInflated){
                    showLocationOnMap(myPlace,googleMap);
                }else {
                    mapFragment.getMapAsync(this);
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.v("","");
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap=map;
        isMapInflated = true;
        if (myPlace==null && BaseActivity.myLocation!=null){
            googleMap.clear();
            LatLng latLng = new LatLng(BaseActivity.myLocation.getLatitude(),BaseActivity.myLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(latLng).title(BaseActivity.myAddress)).showInfoWindow();
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)      // Sets the center of the map to Mountain View
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void finish() {
        if (myPlace!=null){
            Intent intent = new Intent();
            intent.setAction(AppConstants.EXTRA_LATLNG);
            intent.putExtra(AppConstants.EXTRA_LAT,myPlace.getLatLng().latitude);
            intent.putExtra(AppConstants.EXTRA_LNG,myPlace.getLatLng().longitude);
            intent.putExtra(AppConstants.EXTRA_ADDRESS,myPlace.getName());
            setResult(RESULT_OK,intent);
        }
        super.finish();
    }
}
