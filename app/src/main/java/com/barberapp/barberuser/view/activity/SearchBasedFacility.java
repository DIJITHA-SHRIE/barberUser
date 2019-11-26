package com.barberapp.barberuser.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.FacilityListener;
import com.barberapp.barberuser.pojos.CategoryResponse;
import com.barberapp.barberuser.pojos.FacilityResponse;
import com.barberapp.barberuser.pojos.FirebasePartnerResponse;
import com.barberapp.barberuser.pojos.SaloonSearchResponse;
import com.barberapp.barberuser.pojos.SubCategoryResponse;
import com.barberapp.barberuser.presenter.SaloonSearchPresenter;
import com.barberapp.barberuser.view.SearchSaloonView;
import com.barberapp.barberuser.view.adapter.FacilityAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchBasedFacility extends AppCompatActivity implements SearchSaloonView, FacilityListener {
    @BindView(R.id.close_facility)
    Button close_facility;
    @BindView(R.id.rv_facility)
    RecyclerView rv_facility;
    private SaloonSearchPresenter<SearchSaloonView> saloonSearchPresenter;
    @BindView(R.id.search_facility_btn)
    Button search_facility_btn;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_facility);
        ButterKnife.bind( this);
        this.search_facility_btn.setVisibility(View.GONE);
        callFacility();
    }

    private void callFacility() {
        this.saloonSearchPresenter = new SaloonSearchPresenter<>();
        this.saloonSearchPresenter.setSearchSaloonView(this);
        this.saloonSearchPresenter.fetchFacilities();
    }

    public void onFacilitySearchListener(String[] getvalues) {
    }

    public void onFetchSearchedSaloon(SaloonSearchResponse responses) {
    }

    public void onFetchSaloonError(String error) {
    }

    public void onFetchCategory(CategoryResponse response) {
    }

    public void onFetchCategoryError(String error) {
    }

    public void onFetchSubCategory(SubCategoryResponse response) {
    }

    public void onFetchSubCategoryError(String error) {
    }

    public void onFacilitySearch(FacilityResponse response) {
        showFacilityInPopUp(response);
    }

    private void showFacilityInPopUp(FacilityResponse response) {
        FacilityAdapter adapter = new FacilityAdapter(this, response.getData(), this, "SEARCH");
        this.rv_facility.setLayoutManager(new LinearLayoutManager(this));
        this.rv_facility.setAdapter(adapter);
    }

    public void onFacilitySearchError(String error) {
    }

    @Override
    public void onFirebasePartnerCall(FirebasePartnerResponse response) {

    }

    @Override
    public void onFirebasePartnerError(String err) {

    }

    public void showLoading(boolean isLoaing) {
    }

    public void showError(String error) {
    }
    @OnClick(R.id.close_facility)
    public void onClose(View view) {
        setResult(1002, new Intent());
        finish();
    }
}
