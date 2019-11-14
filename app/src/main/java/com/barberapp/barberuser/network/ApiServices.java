package com.barberapp.barberuser.network;

import com.barberapp.barberuser.pojos.AdvResponse;
import com.barberapp.barberuser.pojos.CategoryResponse;
import com.barberapp.barberuser.pojos.CheckSumReponse;
import com.barberapp.barberuser.pojos.FacilityResponse;
import com.barberapp.barberuser.pojos.MemberReponse;
import com.barberapp.barberuser.pojos.MobileResponse;
import com.barberapp.barberuser.pojos.MyBookingResponse;
import com.barberapp.barberuser.pojos.ResetResponse;
import com.barberapp.barberuser.pojos.SaloonResponse;
import com.barberapp.barberuser.pojos.SaloonSearchResponse;
import com.barberapp.barberuser.pojos.SaloonServicesResponse;
import com.barberapp.barberuser.pojos.ServicePriceResponse;
import com.barberapp.barberuser.pojos.SubCategoryResponse;
import com.barberapp.barberuser.pojos.ValidateUserResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {
    @FormUrlEncoded
    @POST("m_user_mobile_register.php")
    Observable<MobileResponse> registerMobile(@Field("phone_no")String mobilenumber);

    @FormUrlEncoded
    @POST("m_user_all_api.php")
    Observable<ValidateUserResponse> validateUser(@Field("mobile_no")String mobilenumber);

    @FormUrlEncoded
    @POST("m_user_name.php")
    Observable<MobileResponse> verifyotp(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("m_user_login.php")
    Observable<MobileResponse> login(@FieldMap HashMap<String,String> hashMap);


    @FormUrlEncoded
    @POST("m_find_location.php")
    Observable<SaloonResponse> fetchSallonsNearMe(@Field("pnt_lat") String lat,@Field("pnt_lng") String lng);


    @GET("saloon_adv.php")
    Observable<AdvResponse> fetchAdvAPI();



    @GET("m_saloon_category.php")
    Observable<CategoryResponse> fetchCategoryAPI();


    @GET("m_saloon_facilities.php")
    Observable<FacilityResponse> fetchFacilityAPI();

    @FormUrlEncoded
    @POST("m_saloon_subCategory.php")
    Observable<SubCategoryResponse> fetchSubCatAPI(@Field("cat_id") String cat_id);


    @FormUrlEncoded
    @POST("m_search_saloon.php")
    Observable<SaloonSearchResponse> fetchSallonSearchAPI(@Field("pnt_lat") String lat, @Field("pnt_lng") String lng,
                                                          @Field("gender") String gender, @Field("expertise_id") String expertise_id,
                                                          @Field("serviceTime") String serviceTime,@Field("facilities_id") String facilities_id);

    @FormUrlEncoded
    @POST("m_user_update.php")
    Observable<MobileResponse> updateProfile(@FieldMap HashMap<String,String> hashMap);

    @FormUrlEncoded
    @POST("m_services_user.php")
    Observable<SaloonServicesResponse> fetchSaloonServices(@Field("owned_by")String owendBy);

    @FormUrlEncoded
    @POST("m_member_user.php")
    Observable<MemberReponse> fetchSaloonMembers(@Field("owned_by")String owendBy);

    @FormUrlEncoded
    @POST("m_service_price_user.php")
    Observable<ServicePriceResponse> fetchServicePrice(@Field("owned_by")String owenedBy,@Field("cat_name")String cat_name);

    @FormUrlEncoded
    @POST("m_book_appointment.php")
    Observable<MobileResponse> bookSaloon(@FieldMap HashMap<String,String> hashMap);

    @FormUrlEncoded
    @POST("m_user_book_status.php")
    Observable<MyBookingResponse> myBookings(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("m_user_forgot_pwd.php")
    Observable<MobileResponse> forgotPwd(@Field("phone_no") String mobileno);

    @FormUrlEncoded
    @POST("m_user_change_password.php")
    Observable<ResetResponse> resetPwd(@Field("phone_no")String phone_no, @Field("password")String password, @Field("otp")String otp);
    /*@FormUrlEncoded
    @POST("m_user_change_password.php")
    Call<ResetResponse> resetPwd(@Field("phone_no")String phone_no, @Field("password")String password, @Field("otp")String otp);*/

    @FormUrlEncoded
    @POST("generateChecksum.php")
    Observable<CheckSumReponse> getCheckSum(@FieldMap HashMap<String,String> map);
}
