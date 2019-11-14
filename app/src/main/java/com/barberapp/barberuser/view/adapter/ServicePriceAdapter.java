package com.barberapp.barberuser.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.OnSelectServices;
import com.barberapp.barberuser.pojos.ServiePrice;
import com.barberapp.barberuser.view.activity.BookingActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  ServicePriceAdapter extends RecyclerView.Adapter<ServicePriceAdapter.ServicePriceViewHolder>{
    private Context context;
    private ArrayList<ServiePrice> serviePrices;
    private OnSelectServices onSelectServices;
    public void setSelectServiceListner(OnSelectServices selectServiceListner){
        this.onSelectServices = selectServiceListner;
    }

    public ServicePriceAdapter(Context context, ArrayList<ServiePrice> serviePrices) {
        this.context = context;
        this.serviePrices = serviePrices;
    }

    @NonNull
    @Override
    public ServicePriceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_price_list_item,viewGroup,false);
        return new ServicePriceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicePriceViewHolder servicePriceViewHolder, int position) {
        final ServiePrice serviePrice = serviePrices.get(position);
        servicePriceViewHolder.txtStyleName.setText(serviePrice.getStyle_name());
        servicePriceViewHolder.txtPacName.setText(serviePrice.getPac_name());
        servicePriceViewHolder.txtStyleType.setText(serviePrice.getCat_name());
        servicePriceViewHolder.txtServiceCost.setText("Rs."+ serviePrice.getPrice() );
        servicePriceViewHolder.txtServiceTime.setText(serviePrice.getTimes());
        servicePriceViewHolder.btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectServices!=null){
                    onSelectServices.onServiceSelected(serviePrice);
                }
            }
        });
        if (context instanceof BookingActivity){
            servicePriceViewHolder.btnAddService.setVisibility(View.GONE);
        }else {
            //servicePriceViewHolder.btnAddService.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(serviePrice.getTimes()) || TextUtils.isEmpty(serviePrice.getPrice())){
                servicePriceViewHolder.btnAddService.setVisibility(View.GONE);
            }else {
                servicePriceViewHolder.btnAddService.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return serviePrices.size();
    }

    public class ServicePriceViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtStyleName)
        TextView txtStyleName;
        @BindView(R.id.txtPacName)
        TextView txtPacName;
        @BindView(R.id.txtStyleType)
        TextView txtStyleType;
        @BindView(R.id.txtServiceCost)
        TextView txtServiceCost;
        @BindView(R.id.btnAddService)
        Button btnAddService;
        @BindView(R.id.btnChooseTimeSlot)
        Button btnChooseTimeSlot;
        @BindView(R.id.txtServiceTime)
        TextView txtServiceTime;
        public ServicePriceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
