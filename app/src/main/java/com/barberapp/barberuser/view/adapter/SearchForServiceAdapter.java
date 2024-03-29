package com.barberapp.barberuser.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.CallClickedListner;
import com.barberapp.barberuser.helper.ShowOnMapListner;
import com.barberapp.barberuser.pojos.CategoryResponse;
import com.barberapp.barberuser.pojos.FacilityResponse;
import com.barberapp.barberuser.pojos.FirebasePartnerResponse;
import com.barberapp.barberuser.pojos.SaloonMember;
import com.barberapp.barberuser.pojos.SaloonSearchData;
import com.barberapp.barberuser.pojos.SaloonSearchResponse;
import com.barberapp.barberuser.pojos.SaloonServices;
import com.barberapp.barberuser.pojos.ServiePrice;
import com.barberapp.barberuser.pojos.SubCategoryResponse;
import com.barberapp.barberuser.presenter.SallonStaffPresenter;
import com.barberapp.barberuser.presenter.SaloonSearchPresenter;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.SaloonView;
import com.barberapp.barberuser.view.SearchSaloonView;
import com.barberapp.barberuser.view.activity.BaseActivity;
import com.barberapp.barberuser.view.activity.SearchBasedFacility;
import com.google.android.gms.location.ActivityTransition;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchForServiceAdapter extends RecyclerView.Adapter<SearchForServiceAdapter.ViewHolder> implements SearchSaloonView,SaloonView {
    ArrayList<SaloonSearchData> saloonSearchDataArrayList;
    SaloonSearchPresenter<SearchSaloonView> presenter;
    Context context;
    private ShowOnMapListner showonMap;
    private CallClickedListner callClickedListner;
    String subCategoryVal;
    private SallonStaffPresenter<SaloonView> saloonViewSallonStaffPresenter;
    SaloonSearchData selectedSaloonSearchData;
    private AppSharedPrefference appSharedPrefference;


    public void setOncallClicklisner(CallClickedListner listner){
        this.callClickedListner=listner;
    }
    public void setOnShowonMap(ShowOnMapListner showonMap){
        this.showonMap = showonMap;
    }

    public SearchForServiceAdapter(ArrayList<SaloonSearchData> saloonSearchDataArrayList, Context context,String subCategoryVal) {
        this.saloonSearchDataArrayList = saloonSearchDataArrayList;
        this.context = context;
        this.subCategoryVal=subCategoryVal;
        Log.i("SubCategoryValue",subCategoryVal);
        presenter = new SaloonSearchPresenter<>();
        this.presenter.setSearchSaloonView(this);
        this.saloonViewSallonStaffPresenter = new SallonStaffPresenter<>();
        this.saloonViewSallonStaffPresenter.setSallonStaffView(this);
        appSharedPrefference = new AppSharedPrefference(context);
    }

    @NonNull
    @Override
    public SearchForServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.near_me_items,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchForServiceAdapter.ViewHolder holder, int i) {
        SaloonSearchData data = saloonSearchDataArrayList.get(i);

        holder.txtSallonName.setText(data.getSaloonName());
        holder.txtSaloonMobile.setText(data.getMobileNo());
        holder.txtSaloonLocation.setText(data.getLocation());
        holder.txtDistance.setText(AppUtils.distance(BaseActivity.myLocation.getLatitude(),BaseActivity.myLocation.getLongitude(),Double.valueOf(data.getLatitude()),Double.valueOf(data.getLongitude())) +"km");

        holder.imgShowonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showonMap!=null){
                    showonMap.showonMap(data.getLatitude(),data.getLongitude());
                }
            }
        });

        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callClickedListner!=null){
                    callClickedListner.onCallClicked(data.getMobileNo());
                }
            }
        });

        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent in = new Intent(context, SearchBasedFacility.class);
                ((Activity) context).startActivityForResult(in, 1002);
            }
        });
        if(data.getTotalPrice()!=null){

        holder.txtTotalPrice.setText("Rs:"+ data.getTotalPrice() );}
        else{
            holder.txtTotalPrice.setText("Rs:"+ "NA" );
        }

        String getSaloonImage = data.getImage().replaceAll(" ", "%20");
        Picasso with = Picasso.with(this.context);
        StringBuilder sb2 = new StringBuilder();
        String str = "http://kagami.co.in/admin/public/images/logo/";
        sb2.append(str);
        sb2.append(getSaloonImage);
        with.load(sb2.toString()).placeholder(R.drawable.ic_salloon).into(holder.imgItemImage);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(getSaloonImage);
        Log.i("KagamiImage", sb3.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Saloon view clicked",Toast.LENGTH_SHORT).show();
                //title,msg and to whom
                presenter.fetchFirebaseRes("Booking Arrived",subCategoryVal,data.getOwned_by());

                selectedSaloonSearchData =  data;

                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("user_id",appSharedPrefference.getMobileNumber());
                hashMap.put("duration","30");
                hashMap.put("start_time","9:00am");              //hardcoded later should be dynamic
                hashMap.put("end_time","10:30am");
                hashMap.put("saloon_id",selectedSaloonSearchData.getOwned_by());
                hashMap.put("service_id",subCategoryVal);
                hashMap.put("total_price",selectedSaloonSearchData.getTotalPrice());
                saloonViewSallonStaffPresenter.bookSabool(hashMap);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(saloonSearchDataArrayList!=null){
            return saloonSearchDataArrayList.size();}
        else{
            return 0;
        }
    }

    @Override
    public void onSaloonServicesFetch(ArrayList<SaloonServices> saloonServices) {

    }

    @Override
    public void onSallonServicesetchedrror(String errMsg) {

    }

    @Override
    public void onMemberFetcheed(ArrayList<SaloonMember> saloonMembers) {

    }

    @Override
    public void onMemberFetchedError(String err) {

    }

    @Override
    public void onFetchedServicePrices(ArrayList<ServiePrice> serviePriceArrayList, String serviceName) {

    }

    @Override
    public void onFetchServicePriceFaied(String err) {

    }

    @Override
    public void showLoadingForPrice(boolean isLoading) {

    }

    @Override
    public void showSericeTiming(String opepanTime, String closeTime) {

    }

    @Override
    public void showProgress(boolean isProgress) {

    }

    @Override
    public void bookingSucced(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void bookingFailed(String errrBookingMsg) {

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtSallonName)
        TextView txtSallonName;
        @BindView(R.id.txtSaloonMobile)
        TextView txtSaloonMobile;
        @BindView(R.id.txtSaloonLocation)
        TextView txtSaloonLocation;
        @BindView(R.id.txtDistance)
        TextView txtDistance;
        @BindView(R.id.imgItemImage)
        ImageView imgItemImage;
        @BindView(R.id.imgShowonMap)
        ImageView imgShowonMap;
        @BindView(R.id.imgCall)
        ImageView imgCall;
        @BindView(R.id.imgMore)
        ImageView imgMore;
        @BindView(R.id.txtTotalPrice)
        TextView txtTotalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public void onFetchSearchedSaloon(SaloonSearchResponse responses) {

    }

    @Override
    public void onFetchSaloonError(String error) {

    }

    @Override
    public void onFetchCategory(CategoryResponse response) {

    }

    @Override
    public void onFetchCategoryError(String error) {

    }

    @Override
    public void onFetchSubCategory(SubCategoryResponse response) {

    }

    @Override
    public void onFetchSubCategoryError(String error) {

    }

    @Override
    public void onFacilitySearch(FacilityResponse response) {

    }

    @Override
    public void onFacilitySearchError(String error) {

    }

    @Override
    public void onFirebasePartnerCall(FirebasePartnerResponse response) {
        if(response.getSuccess()== 1){
            Toast.makeText(context,"Notification sent to the saloon Owner",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFirebasePartnerError(String err) {

    }

    @Override
    public void showLoading(boolean isLoaing) {

    }

    @Override
    public void showError(String error) {

    }
}

