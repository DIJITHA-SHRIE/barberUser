package com.barberapp.barberuser.view.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.FacilityListener;
import com.barberapp.barberuser.pojos.FacilityData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {
    Activity context;
    ArrayList<FacilityData> facilityDataArrayList;
    FacilityListener facilityListener;
    public static ArrayList<String> getCheckedvalues;
    String menu;

    public FacilityAdapter(Activity context, ArrayList<FacilityData> facilityDataArrayList, FacilityListener facilityListener,String menu) {
        this.context = context;
        this.facilityDataArrayList = facilityDataArrayList;
        this.facilityListener =facilityListener;
        getCheckedvalues = new ArrayList<>();
        this.menu = menu;
    }

    @NonNull
    @Override
    public FacilityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.facility_adp_lay,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityAdapter.ViewHolder holder, final int i) {
        if (this.menu.equals("SEARCH")) {
            holder.fac_more.setVisibility(View.VISIBLE);
            holder.check_facility.setVisibility(View.GONE);
        } else {
            holder.fac_more.setVisibility(View.GONE);
            holder.check_facility.setVisibility(View.VISIBLE);
        }
        holder.check_facility.setText(((FacilityData) this.facilityDataArrayList.get(i)).getFacilityName());
        holder.check_facility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    FacilityAdapter.getCheckedvalues.add(((FacilityData) FacilityAdapter.this.facilityDataArrayList.get(i)).getId());
                } else if (FacilityAdapter.getCheckedvalues.size() > 0) {
                    for (int i = 0; i < FacilityAdapter.getCheckedvalues.size(); i++) {
                        if (((String) FacilityAdapter.getCheckedvalues.get(i)).contains(((FacilityData) FacilityAdapter.this.facilityDataArrayList.get(i)).getId())) {
                            FacilityAdapter.getCheckedvalues.remove(i);
                        }
                    }
                }
            }
        });
        holder.fac_more.setText(((FacilityData) this.facilityDataArrayList.get(i)).getFacilityName());

    }

    @Override
    public int getItemCount() {
        return facilityDataArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.check_facility)
        CheckBox check_facility;
        @BindView(R.id.fac_more)
        TextView fac_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
