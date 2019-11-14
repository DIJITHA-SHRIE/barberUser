package com.barberapp.barberuser.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.CallClickedListner;
import com.barberapp.barberuser.helper.ShowOnMapListner;
import com.barberapp.barberuser.pojos.Saloon;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.activity.BaseActivity;
import com.barberapp.barberuser.view.activity.SaloonDetailsActivity;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class NearMeAdpter extends RecyclerView.Adapter<NearMeAdpter.NearMeViewHolder>{
    private Context context;
    private ArrayList<Saloon> saloons;
    private ShowOnMapListner showonMap;
    private CallClickedListner callClickedListner;
    public void setOncallClicklisner(CallClickedListner listner){
        this.callClickedListner=listner;
    }
    public void setOnShowonMap(ShowOnMapListner showonMap){
        this.showonMap = showonMap;
    }
    public NearMeAdpter(ArrayList<Saloon> items, Context context){
        this.saloons = items;
        this.context = context;
    }
    @NonNull
    @Override
    public NearMeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.near_me_items,viewGroup,false);
        return new NearMeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NearMeViewHolder nearMeViewHolder, int i) {
        final Saloon saloon = saloons.get(i);
        nearMeViewHolder.txtSallonName.setText(saloon.getSaloon_name());
        nearMeViewHolder.txtSaloonMobile.setText(saloon.getOwner()+"("+saloon.getMobile_no()+")");
        nearMeViewHolder.txtSaloonLocation.setText(saloon.getLocation());
        //nearMeViewHolder.txtDistance.setText(saloon.getDistance()+"km");
        nearMeViewHolder.txtDistance.setText(AppUtils.distance(BaseActivity.myLocation.getLatitude(),BaseActivity.myLocation.getLongitude(),Double.valueOf(saloon.getLatitude()),Double.valueOf(saloon.getLongitude())) +"km");
        if (!TextUtils.isEmpty(saloon.getImage())){

            Picasso.with(context).load(saloon.getImage())
                    .placeholder(R.drawable.ic_not_available_circle).error(R.drawable.ic_not_available_circle)
                    .into(nearMeViewHolder.imgItemImage);
            //Glide.with(context).load(saloon.getImage()).into(nearMeViewHolder.imgItemImage);

        }
        nearMeViewHolder.imgShowonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showonMap!=null){
                    showonMap.showonMap(saloon.getLatitude(),saloon.getLongitude());
                }
            }
        });
        nearMeViewHolder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callClickedListner!=null){
                    callClickedListner.onCallClicked(saloon.getMobile_no());
                }
            }
        });
        nearMeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SaloonDetailsActivity.class);
                intent.putExtra(AppConstants.EXTRA_SALOON,saloon);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return saloons.size();
    }

    public class NearMeViewHolder extends RecyclerView.ViewHolder{
        private TextView txtSallonName,txtSaloonMobile,txtSaloonLocation,txtDistance;
        private ImageView imgItemImage,imgShowonMap,imgCall;
        public NearMeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSallonName = itemView.findViewById(R.id.txtSallonName);
            txtSaloonMobile = itemView.findViewById(R.id.txtSaloonMobile);
            txtSaloonLocation = itemView.findViewById(R.id.txtSaloonLocation);
            txtDistance=itemView.findViewById(R.id.txtDistance);
            imgItemImage = itemView.findViewById(R.id.imgItemImage);
            imgShowonMap = itemView.findViewById(R.id.imgShowonMap);
            imgCall = itemView.findViewById(R.id.imgCall);
        }
    }
}
