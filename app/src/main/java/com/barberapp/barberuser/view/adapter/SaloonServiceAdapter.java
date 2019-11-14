package com.barberapp.barberuser.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.OnCheckedStaffListner;
import com.barberapp.barberuser.helper.OnSaloonServiceClickListner;
import com.barberapp.barberuser.pojos.SaloonServices;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaloonServiceAdapter extends RecyclerView.Adapter<SaloonServiceAdapter.StaffViewHolder>{
    private ArrayList<SaloonServices> saloonServices;
    private Context context;
    private int lastCheckedItem = -1;
    private OnCheckedStaffListner listner;
    private OnSaloonServiceClickListner onSaloonServiceClickListner;
    public void setSaloonServices(OnSaloonServiceClickListner serviceClickListner){
        this.onSaloonServiceClickListner = serviceClickListner;
    }
    public void setStaffCheck(OnCheckedStaffListner onCheckedStaffListner){
        this.listner = onCheckedStaffListner;
    }

    public SaloonServiceAdapter(ArrayList<SaloonServices> saloonServices, Context context) {
        this.saloonServices = saloonServices;
        this.context = context;
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.services_list_item,viewGroup,false);
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder staffViewHolder, final int position) {
        final SaloonServices saloonServices = this.saloonServices.get(position);
        //staffViewHolder.checkStaff.setChecked(position==lastCheckedItem);
        staffViewHolder.txtSallonService.setText(saloonServices.getName());
        staffViewHolder.checkStaff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listner!=null){
                    listner.onCheckedStaff(saloonServices,position,isChecked);
                }
            }
        });
        staffViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSaloonServiceClickListner!=null){
                    onSaloonServiceClickListner.onSaloonServiceClick(saloonServices,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return saloonServices.size();
    }

    public class StaffViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.checkStaff)
        CheckBox checkStaff;
        @BindView(R.id.txtSallonService)
        TextView txtSallonService;
        public StaffViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            /*checkStaff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });*/
        }
    }
}
