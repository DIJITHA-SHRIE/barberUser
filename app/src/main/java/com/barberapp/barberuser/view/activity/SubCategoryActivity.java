package com.barberapp.barberuser.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.SubcatListener;
import com.barberapp.barberuser.pojos.SubCategoryData;
import com.barberapp.barberuser.view.adapter.SubCategoryAdapter;
import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity implements SubcatListener {
    ArrayList<String> getMultipleService = new ArrayList<>();
    ArrayList<String> getSelecedSubServices;
    ArrayList<String> getSelecedSubServicesName;
    @BindView(R.id.rv_sub_cat)
    RecyclerView rv_sub_cat;
    @BindView(R.id.subCat_ok)
    Button subCat_ok;
    ArrayList<SubCategoryData> subCategoryDataArrayList = new ArrayList<>();

    /* access modifiers changed from: protected */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        ButterKnife.bind((Activity) this);
        this.subCategoryDataArrayList = getIntent().getParcelableArrayListExtra("SUBCATEGORIES");
        this.getMultipleService = getIntent().getStringArrayListExtra("SELECTEDSERVICE");
        SubCategoryAdapter adapter = new SubCategoryAdapter(this.subCategoryDataArrayList, this, this.getMultipleService, this);
        this.rv_sub_cat.setLayoutManager(new LinearLayoutManager(this));
        this.rv_sub_cat.setAdapter(adapter);
    }

    public void onSubCatListener(ArrayList<String> getSelecedSubServices2, ArrayList<String> getSelecedSubServicesName2) {
        this.getSelecedSubServices = new ArrayList<>();
        this.getSelecedSubServicesName = new ArrayList<>();
        this.getSelecedSubServices = getSelecedSubServices2;
        StringBuilder sb = new StringBuilder();
        sb.append(getSelecedSubServices2.size());
        String str = "";
        sb.append(str);
        Log.i("getSelecedSubServices", sb.toString());
        this.getSelecedSubServicesName = getSelecedSubServicesName2;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getSelecedSubServicesName2.size());
        sb2.append(str);
        Log.i("getSelecedSubName", sb2.toString());
    }

    @OnClick(R.id.subCat_ok)
    public void onSubItemClick(View view) {
        Intent in = new Intent();
        in.putExtra("SELECTEDSUBSERVICES", this.getSelecedSubServices);
        in.putExtra("SELECTEDSUBSERVICES_NAME", this.getSelecedSubServicesName);
        setResult(1001, in);
        finish();
    }
}

