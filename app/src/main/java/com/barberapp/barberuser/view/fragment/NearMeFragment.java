package com.barberapp.barberuser.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barberapp.barberuser.MapsActivity;
import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.CallClickedListner;
import com.barberapp.barberuser.helper.ShowOnMapListner;
import com.barberapp.barberuser.pojos.AdvData;
import com.barberapp.barberuser.pojos.AdvResponse;
import com.barberapp.barberuser.pojos.Saloon;
import com.barberapp.barberuser.presenter.NearMePresenter;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.NearMeView;
import com.barberapp.barberuser.view.activity.BaseActivity;
import com.barberapp.barberuser.view.adapter.NearMeAdpter;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NearMeFragment extends Fragment implements NearMeView, ShowOnMapListner, CallClickedListner, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private static final int REQUEST_CALL = 501;
    @BindView(R.id.rcvNearMe)
    RecyclerView rcvNearMe;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtNoSaloons)
    TextView txtNoSaloons;
    private NearMeAdpter adpter;
    private NearMePresenter<NearMeView> nearMeViewNearMePresenter;
    private String mobile;
    private double searchedLat,searchedLng;
    @BindView(R.id.fabAllMaps)
    FloatingActionButton fabAllMaps;
    private ArrayList<Saloon> fetchedSaloons;
    @BindView(R.id.slider)
    SliderLayout slider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_nearme,container,false);
        ButterKnife.bind(this,view);
        rcvNearMe = view.findViewById(R.id.rcvNearMe);
        rcvNearMe.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvNearMe.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        nearMeViewNearMePresenter = new NearMePresenter<>();
        nearMeViewNearMePresenter.setNearMeView(NearMeFragment.this);
        fetchedSaloons = new ArrayList<>();
        nearMeViewNearMePresenter.fetchAdv();
        if (BaseActivity.myLocation!=null){
            if (searchedLat!=0.0 && searchedLng !=0.0){
                nearMeViewNearMePresenter.fetchSaloonsNearMe(""+searchedLat,""+searchedLat);
            }else {
                nearMeViewNearMePresenter.fetchSaloonsNearMe(""+BaseActivity.myLocation.getLatitude(),""+BaseActivity.myLocation.getLongitude());
            }

        }
        fabAllMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra(AppConstants.EXTRA_SALOONS,fetchedSaloons);
                startActivity(intent);
            }
        });
        return view;

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onFecthSallons(ArrayList<Saloon> saloons) {
        if (saloons!=null && saloons.size()>0){
            fetchedSaloons = saloons;
            txtNoSaloons.setVisibility(View.GONE);
            rcvNearMe.setVisibility(View.VISIBLE);
            adpter = new NearMeAdpter(saloons,getActivity());
            rcvNearMe.setAdapter(adpter);
            adpter.setOnShowonMap(NearMeFragment.this);
            adpter.setOncallClicklisner(NearMeFragment.this);
            fabAllMaps.setVisibility(View.VISIBLE);
        }else {
            txtNoSaloons.setVisibility(View.VISIBLE);
            rcvNearMe.setVisibility(View.GONE);
            fabAllMaps.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailedFetch(String err) {
        txtNoSaloons.setVisibility(View.VISIBLE);
        rcvNearMe.setVisibility(View.GONE);
    }

    @Override
    public void onFetchAdv(AdvResponse response) {
        ArrayList<AdvData> advDataArrayList = response.getData();
        StringBuilder sb = new StringBuilder();
        sb.append(advDataArrayList.size());
        String str = "";
        sb.append(str);
        Log.i("advDataArrayList", sb.toString());
        for (int i = 0; i < advDataArrayList.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            String getAdvImage = ((AdvData) advDataArrayList.get(i)).getImage().replaceAll(" ", "%20");
            StringBuilder sb2 = new StringBuilder();
            String str2 = "http://kagami.co.in/admin/public/images/advimages/";
            sb2.append(str2);
            sb2.append(getAdvImage);
            if (sb2.toString() != null) {
                BaseSliderView description = textSliderView.description(str);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(getAdvImage);
                Log.i("TextsliderImage",sb3.toString());
                description.image(sb3.toString()).empty(R.drawable.ic_salloon).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(this);
            } else {
                textSliderView.description(str).image((int) R.drawable.ic_salloon).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(this);
            }
            this.slider.addSlider(textSliderView);
        }
        this.slider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        this.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        this.slider.setCustomAnimation(new DescriptionAnimation());
        this.slider.setDuration(3000);
        this.slider.addOnPageChangeListener(this);


    }

    @Override
    public void onFailedAdv(String str) {

    }

    @Override
    public void showLoading(boolean isLoaing) {
        if (isLoaing){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        txtNoSaloons.setVisibility(View.VISIBLE);
        rcvNearMe.setVisibility(View.GONE);
    }

    @Override
    public void showonMap(String lat, String lng) {
        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?q=loc:" + lat + "," + lng);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    @Override
    public void onCallClicked(String mobilnumber) {
        mobile = mobilnumber;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (AppUtils.checkPermission(Manifest.permission.CALL_PHONE,getActivity())){
                makeCall(mobile);
            }else {
                AppUtils.requestPermission(NearMeFragment.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }
        }else {
            makeCall(mobile);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CALL){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makeCall(mobile);
            }else {
                Toast.makeText(getActivity(), "App need permision to Make call", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makeCall(String mobile) {
        try {
            Intent my_callIntent = new Intent(Intent.ACTION_CALL);
            my_callIntent.setData(Uri.parse("tel:"+mobile));
            startActivity(my_callIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_map,menu);
    }*/
}
