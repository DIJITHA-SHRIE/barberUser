package com.barberapp.barberuser.view.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.SubcatListener;
import com.barberapp.barberuser.pojos.SubCategoryData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    Activity context;
    ArrayList<String> getMultipleService;
    ArrayList<String> getSelecedSubServices = new ArrayList<>();
    ArrayList<String> getSelecedSubServicesName = new ArrayList<>();
    SubcatListener listener;
    ArrayList<SubCategoryData> subCategoryDataArrayList;

    public SubCategoryAdapter(ArrayList<SubCategoryData> subCategoryDataArrayList2, Activity context2,
                              ArrayList<String> getMultipleService2, SubcatListener listener2) {
        this.subCategoryDataArrayList = subCategoryDataArrayList2;
        this.context = context2;
        this.getMultipleService = getMultipleService2;
        this.listener = listener2;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.multiple_service_selection,viewGroup,false);
        return new SubCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.sub_cat_check.setText(((SubCategoryData) this.subCategoryDataArrayList.get(i)).getCategoryList());
        holder.sub_cat_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    SubCategoryAdapter.this.getSelecedSubServices.add(((SubCategoryData) SubCategoryAdapter.this.subCategoryDataArrayList.get(i)).getId().trim());
                    SubCategoryAdapter.this.getSelecedSubServicesName.add(((SubCategoryData) SubCategoryAdapter.this.subCategoryDataArrayList.get(i)).getCategoryList());
                    SubCategoryAdapter.this.listener.onSubCatListener(SubCategoryAdapter.this.getSelecedSubServices, SubCategoryAdapter.this.getSelecedSubServicesName);
                } else if (SubCategoryAdapter.this.getSelecedSubServices.size() > 0 && SubCategoryAdapter.this.getSelecedSubServices.contains(((SubCategoryData) SubCategoryAdapter.this.subCategoryDataArrayList.get(i)).getId())) {
                    SubCategoryAdapter.this.getSelecedSubServices.remove(((SubCategoryData) SubCategoryAdapter.this.subCategoryDataArrayList.get(i)).getId());
                    SubCategoryAdapter.this.getSelecedSubServicesName.remove(((SubCategoryData) SubCategoryAdapter.this.subCategoryDataArrayList.get(i)).getCategoryList());
                    SubCategoryAdapter.this.listener.onSubCatListener(SubCategoryAdapter.this.getSelecedSubServices, SubCategoryAdapter.this.getSelecedSubServicesName);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        ArrayList<SubCategoryData> arrayList = this.subCategoryDataArrayList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;


    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.simpleCheckedTextView)
        CheckBox sub_cat_check;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind( this, itemView);
        }

    }
}
