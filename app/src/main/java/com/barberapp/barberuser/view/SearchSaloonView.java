package com.barberapp.barberuser.view;

import com.barberapp.barberuser.pojos.CategoryResponse;
import com.barberapp.barberuser.pojos.FacilityResponse;
import com.barberapp.barberuser.pojos.FirebasePartnerResponse;
import com.barberapp.barberuser.pojos.SaloonSearchResponse;
import com.barberapp.barberuser.pojos.SubCategoryResponse;

import java.util.ArrayList;

public interface SearchSaloonView extends BaseView{
    void onFetchSearchedSaloon(SaloonSearchResponse responses);
    void onFetchSaloonError(String error);
    void onFetchCategory(CategoryResponse response);
    void onFetchCategoryError(String error);
    void onFetchSubCategory(SubCategoryResponse response);
    void onFetchSubCategoryError(String error);
    void onFacilitySearch(FacilityResponse response);
    void onFacilitySearchError(String error);
    void onFirebasePartnerCall(FirebasePartnerResponse response);
    void onFirebasePartnerError(String err);

}
