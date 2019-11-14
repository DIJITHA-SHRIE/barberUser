package com.barberapp.barberuser.network;

import com.google.android.gms.common.api.Api;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private static final NetworkManager ourInstance = new NetworkManager();

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    public static NetworkManager getOurInstance() {
        return ourInstance;
    }

    public void setApiServices(ApiServices apiServices) {
        this.apiServices = apiServices;
    }

    private NetworkManager() {
       Retrofit retrofit = new Retrofit.Builder().client(getClient())
                .baseUrl(AppUrls.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiServices = retrofit.create(ApiServices.class);
    }
    private ApiServices apiServices;
    public static OkHttpClient getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();
        return client;
    }
    public ApiServices getApiServices(){
        return apiServices;
    }


}
