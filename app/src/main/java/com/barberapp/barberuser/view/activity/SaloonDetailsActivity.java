package com.barberapp.barberuser.view.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.OnCheckedStaffListner;
import com.barberapp.barberuser.helper.OnSaloonServiceClickListner;
import com.barberapp.barberuser.helper.OnSelectServices;
import com.barberapp.barberuser.pojos.SaloonMember;
import com.barberapp.barberuser.pojos.SaloonServices;
import com.barberapp.barberuser.pojos.Saloon;
import com.barberapp.barberuser.pojos.ServiePrice;
import com.barberapp.barberuser.presenter.SallonStaffPresenter;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.utils.ServiceTon;
import com.barberapp.barberuser.view.SaloonView;
import com.barberapp.barberuser.view.adapter.MemberAdapter;
import com.barberapp.barberuser.view.adapter.SaloonServiceAdapter;
import com.barberapp.barberuser.view.adapter.ServicePriceAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaloonDetailsActivity extends AppCompatActivity implements SaloonView, OnCheckedStaffListner, OnSaloonServiceClickListner, OnSelectServices {
    private Saloon saloon;
    @BindView(R.id.toolbarImage)
    ImageView toolbarImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtNoStaffs)
    TextView txtNoStaffs;
    @BindView(R.id.progresBarMobile)
    ProgressBar progresBarMobile;
    @BindView(R.id.rcvSaloonStaffs)
    RecyclerView rcvSaloonStaffs;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.rcvMembers)
    RecyclerView rcvMembers;
    @BindView(R.id.progressMembers)
    ProgressBar progressMembers;
    @BindView(R.id.txtNoMembers)
    TextView txtNoMembers;
    @BindView(R.id.txtServiceTiming)
    TextView txtServiceTiming;
    private SallonStaffPresenter<SaloonView> saloonViewSallonStaffPresenter;
    private String saloonOpentime="";
    private ArrayList<ServiePrice>serviePrices;
    private AppSharedPrefference appSharedPrefference;
    @BindView(R.id.btnBook)
    Button btnBook;
    private String totDurations,totTimes,totPrices;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");
    Dialog dialog = null;
    private ProgressDialog progressDialog;
    private static final int REQUEST_BOOKING = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        appSharedPrefference=new AppSharedPrefference(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Booking...");
        progressDialog.setCanceledOnTouchOutside(false);
        saloon = getIntent().getParcelableExtra(AppConstants.EXTRA_SALOON);
        if (saloon!=null){
            collapsingToolbar.setTitle(saloon.getSaloon_name());
            toolbar.setTitle(saloon.getSaloon_name());
        }else {
            collapsingToolbar.setTitle("Saloon Details");
            toolbar.setTitle("Saloon Details");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!TextUtils.isEmpty(saloon.getImage())){
            Picasso.with(getApplicationContext()).load(saloon.getImage())
                    .placeholder(R.drawable.ic_not_available_circle).error(R.drawable.ic_not_available_circle)
                    .into(toolbarImage);
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rcvSaloonStaffs.setLayoutManager(staggeredGridLayoutManager);
        rcvMembers.setLayoutManager(new LinearLayoutManager(this));
        rcvMembers.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        saloonViewSallonStaffPresenter=new SallonStaffPresenter<>();
        saloonViewSallonStaffPresenter.setSallonStaffView(this);
        if (AppUtils.isOnLine(SaloonDetailsActivity.this)){
            saloonViewSallonStaffPresenter.fetchSaloonStaffData(saloon.getOwned_by());
        }else {
            txtNoStaffs.setVisibility(View.VISIBLE);
            txtNoStaffs.setText(AppConstants.NETWORK_ERROR);
        }
        serviePrices=new ArrayList<>();
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviePrices.size()==0){
                    Toast.makeText(SaloonDetailsActivity.this, "Please select our services & then BOOK", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!AppUtils.isOnLine(SaloonDetailsActivity.this)){
                    Toast.makeText(SaloonDetailsActivity.this, "Please check your Internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    try{
                        int totPrice=0,totDuration=0;
                        String serviceIds = "";
                        StringBuilder stringBuilder = new StringBuilder();
                        for (ServiePrice serviePrice:serviePrices){
                            totPrice = totPrice+Integer.valueOf(serviePrice.getPrice());
                            totDuration = totDuration+Integer.valueOf(serviePrice.getTimes());
                            stringBuilder.append(serviePrice.getId());
                            stringBuilder.append(",");
                        }
                        serviceIds = stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
                        totPrices = ""+totPrice;
                        totDurations = totDuration+"";
                        Date date = (Date) simpleDateFormat.parse(saloonOpentime);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        cal.add(Calendar.MINUTE, totDuration);
                        String endTime = simpleDateFormat.format(cal.getTime());
                        Intent intent = new Intent(SaloonDetailsActivity.this,BookingActivity.class);
                        intent.putExtra(AppConstants.EXTRA_SALOON,saloon);
                        intent.putExtra(AppConstants.EXTRA_START_TIME,saloonOpentime);
                        intent.putExtra(AppConstants.EXTRA_END_TIME,endTime);
                        intent.putExtra(AppConstants.EXTRA_TOTAL_DURATION,totDurations);
                        intent.putExtra(AppConstants.EXTRA_TOTAL_PRICE,totPrices);
                        intent.putExtra(AppConstants.EXTRA_SURVICE_IDS,serviceIds);
                        intent.putExtra(AppConstants.EXTRA_MOBILE,appSharedPrefference.getMobileNumber());
                        intent.putExtra(AppConstants.EXTRA_SERVICE_PRICES,serviePrices);
                        startActivityForResult(intent,REQUEST_BOOKING);
                        //bookSaloon(saloonOpentime,appSharedPrefference.getMobileNumber(),totPrices,saloon,endTime,totDurations,serviceIds);
                    }catch (Exception e){
                        Toast.makeText(SaloonDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void bookSaloon(String saloonOpentime, String mobileNumber, String totPrices, Saloon saloon, String endTime, String totDurations, String serviceIds) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user_id",mobileNumber);
        hashMap.put("duration",totDurations);
        hashMap.put("start_time",saloonOpentime);
        hashMap.put("end_time","10:30am");
        hashMap.put("saloon_id",saloon.getId());
        hashMap.put("service_id",serviceIds);
        hashMap.put("total_price",totPrices);
        saloonViewSallonStaffPresenter.bookSabool(hashMap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onSaloonServicesFetch(ArrayList<SaloonServices> saloonServices) {
        if (saloonServices !=null && saloonServices.size()>0){
            txtNoStaffs.setVisibility(View.GONE);
            rcvSaloonStaffs.setVisibility(View.VISIBLE);
            SaloonServiceAdapter saloonServiceAdapter = new SaloonServiceAdapter(saloonServices,this);
            rcvSaloonStaffs.setAdapter(saloonServiceAdapter);
            saloonServiceAdapter.setStaffCheck(this);
            saloonServiceAdapter.setSaloonServices(this);
        }else {
            txtNoStaffs.setVisibility(View.VISIBLE);
            rcvSaloonStaffs.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSallonServicesetchedrror(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMemberFetcheed(ArrayList<SaloonMember> saloonMembers) {
        if (saloonMembers!=null && saloonMembers.size()>0){
            rcvMembers.setVisibility(View.VISIBLE);
            txtNoMembers.setVisibility(View.GONE);
            MemberAdapter memberAdapter  =  new MemberAdapter(this,saloonMembers);
            rcvMembers.setAdapter(memberAdapter);
        }else {
            rcvMembers.setVisibility(View.GONE);
            txtNoMembers.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMemberFetchedError(String err) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFetchedServicePrices(ArrayList<ServiePrice> serviePriceArrayList,String serviceName) {
        dialog = new Dialog(SaloonDetailsActivity.this,
                    android.R.style.Theme_Translucent_NoTitleBar);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.check_service_price_view);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            dialog.getWindow().setAttributes(lp);
            TextView txtSaloonName = dialog.findViewById(R.id.txtSaloonName);
            TextView txtServiceName = dialog.findViewById(R.id.txtServiceName);
            ImageView imgClosePrice = dialog.findViewById(R.id.imgClosePrice);
            imgClosePrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog!=null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });
            txtSaloonName.setText(saloon.getSaloon_name());
            txtServiceName.setText(serviceName);
            RecyclerView rcvServicePriceList = dialog.findViewById(R.id.rcvServicePriceList);
            rcvServicePriceList.setLayoutManager(new LinearLayoutManager(SaloonDetailsActivity.this));
            rcvServicePriceList.addItemDecoration(new DividerItemDecoration(SaloonDetailsActivity.this,DividerItemDecoration.VERTICAL));
            TextView txtNoPrice = dialog.findViewById(R.id.txtNoPrice);
            dialog.show();
            if (serviePriceArrayList!=null && serviePriceArrayList.size()>0){
                txtNoPrice.setVisibility(View.GONE);
                rcvServicePriceList.setVisibility(View.VISIBLE);
                ServicePriceAdapter servicePriceAdapter = new ServicePriceAdapter(SaloonDetailsActivity.this,serviePriceArrayList);
                rcvServicePriceList.setAdapter(servicePriceAdapter);
                servicePriceAdapter.setSelectServiceListner(SaloonDetailsActivity.this);
            }else {
                txtNoPrice.setVisibility(View.VISIBLE);
                rcvServicePriceList.setVisibility(View.GONE);
            }
    }

    @Override
    public void onFetchServicePriceFaied(String err) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingForPrice(boolean isLoading) {
         if (isLoading){
             progresBarMobile.setVisibility(View.VISIBLE);
         }else {
             progresBarMobile.setVisibility(View.GONE);
         }
    }

    @Override
    public void showSericeTiming(String opepanTime, String closeTime) {
        saloonOpentime="";
        txtServiceTiming.setText(opepanTime+"-"+closeTime);
        saloonOpentime =opepanTime;
    }

    @Override
    public void showProgress(boolean isProgress) {
        if (isProgress){
            progressDialog.show();
        }else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void bookingSucced(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void bookingFailed(String errrBookingMsg) {
        Toast.makeText(this, errrBookingMsg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showLoading(boolean isLoaing) {
        if (isLoaing){
            progresBarMobile.setVisibility(View.VISIBLE);
            progressMembers.setVisibility(View.VISIBLE);
        }else {
            progresBarMobile.setVisibility(View.GONE);
            progressMembers.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedStaff(SaloonServices saloonServices, int position, boolean isChecked) {
        Log.v("","");
    }

    @Override
    public void onSaloonServiceClick(SaloonServices saloonServices, int pos) {
        if (AppUtils.isOnLine(SaloonDetailsActivity.this)){
            saloonViewSallonStaffPresenter.fetchServicePriceList(saloon.getOwned_by(),saloonServices.getName());
        }else {
            Toast.makeText(this, AppConstants.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        saloonViewSallonStaffPresenter.onDestroy();
        ServiceTon.getInstance().setServiePrices(null);
        super.onDestroy();
    }

    @Override
    public void onServiceSelected(ServiePrice serviePrice) {
        if (serviePrices.size()>0){
            for (ServiePrice serviePrice1:serviePrices){
                if (serviePrice1.getId().equals(serviePrice.getId())){
                    Toast.makeText(this, "You have already selected this style", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        serviePrices.add(serviePrice);
        ServiceTon.getInstance().setServiePrices(serviePrices);
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==REQUEST_BOOKING){
            if (!TextUtils.isEmpty(data.getAction()) && AppConstants.EXTRA_BOOKING.equals(data.getAction())){
                int bookingStatus = data.getIntExtra(AppConstants.EXTRA_BOOKING,0);
                if (bookingStatus==1){
                    finish();
                }
            }
        }
    }
}
