package com.barberapp.barberuser.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.CallClickedListner;
import com.barberapp.barberuser.helper.ShowOnMapListner;
import com.barberapp.barberuser.pojos.SaloonSearchData;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.activity.BaseActivity;
import com.barberapp.barberuser.view.activity.SearchBasedFacility;
import com.google.android.gms.location.ActivityTransition;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchForServiceAdapter extends RecyclerView.Adapter<SearchForServiceAdapter.ViewHolder> {
    ArrayList<SaloonSearchData> saloonSearchDataArrayList;
    Context context;
    private ShowOnMapListner showonMap;
    private CallClickedListner callClickedListner;
    public void setOncallClicklisner(CallClickedListner listner){
        this.callClickedListner=listner;
    }
    public void setOnShowonMap(ShowOnMapListner showonMap){
        this.showonMap = showonMap;
    }

    public SearchForServiceAdapter(ArrayList<SaloonSearchData> saloonSearchDataArrayList, Context context) {
        this.saloonSearchDataArrayList = saloonSearchDataArrayList;
        this.context = context;
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

        holder.txtTotalPrice.setText("Rs:"+ data.getTotalPrice() );

    }

    @Override
    public int getItemCount() {
        if(saloonSearchDataArrayList!=null){
        return saloonSearchDataArrayList.size();}
        else{
            return 0;
        }
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
}
