package com.barberapp.barberuser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.barberapp.barberuser.pojos.Saloon;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.view.activity.SaloonDetailsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Saloon> fetchedSaloons;
    private boolean isMapInflated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fetchedSaloons =  getIntent().getParcelableArrayListExtra(AppConstants.EXTRA_SALOONS);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        isMapInflated = true;
        mMap.setOnMarkerClickListener(MapsActivity.this);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        if (fetchedSaloons!=null && fetchedSaloons.size()>0){
              updateMap();
        }else {
            Toast.makeText(this, "Nothing to Show", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMap() {
        try{
            if (mMap!=null){
                mMap.clear();
                if (fetchedSaloons.size()==1){
                    Saloon  saloon=fetchedSaloons.get(0);
                    LatLng latLng=new LatLng(Double.valueOf(saloon.getLatitude()),Double.valueOf(saloon.getLongitude()));
                    Marker marker=mMap.addMarker(new MarkerOptions().position(latLng));

                    marker.setIcon(bitmapDescriptorFromVector(MapsActivity.this,R.drawable.ic_pin));
                    marker.setTag(saloon);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14.0f));
                }else {
                    final LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Saloon saloon : fetchedSaloons) {
                        LatLng latLng = new LatLng(Double.valueOf(saloon.getLatitude()), Double.valueOf(saloon.getLongitude()));
                        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
                        marker.setIcon(bitmapDescriptorFromVector(MapsActivity.this,R.drawable.ic_pin));
                        marker.setTag(saloon);
                        builder.include(marker.getPosition());

                    }
                    if(isMapInflated){
                        LatLngBounds latLngBounds=builder.build();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,20));
                    }else{
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LatLngBounds latLngBounds=builder.build();
                                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,20));
                            }
                        },2000);
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        try{
            Saloon saloon = (Saloon)marker.getTag();
            Intent intent = new Intent(MapsActivity.this, SaloonDetailsActivity.class);
            intent.putExtra(AppConstants.EXTRA_SALOON,saloon);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
