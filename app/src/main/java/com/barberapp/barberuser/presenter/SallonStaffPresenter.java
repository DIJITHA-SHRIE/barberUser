package com.barberapp.barberuser.presenter;

import android.text.TextUtils;

import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.pojos.MemberReponse;
import com.barberapp.barberuser.pojos.MobileResponse;
import com.barberapp.barberuser.pojos.SaloonInfo;
import com.barberapp.barberuser.pojos.SaloonServicesResponse;
import com.barberapp.barberuser.pojos.ServicePriceResponse;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.SaloonView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class SallonStaffPresenter<T extends SaloonView> {
    WeakReference<T> tWeakReference;
    CompositeDisposable compositeDisposable;
    public void setSallonStaffView(T view){
        tWeakReference = new WeakReference<>(view);
        compositeDisposable = new CompositeDisposable();
    }
    public void fetchSaloonStaffData(String owenedBy){
        tWeakReference.get().showLoading(true);
        Observable.zip(NetworkManager.getInstance().getApiServices().fetchSaloonServices(owenedBy),
                NetworkManager.getInstance().getApiServices().fetchSaloonMembers(owenedBy),
                new BiFunction<SaloonServicesResponse, MemberReponse, SaloonInfo>() {
                    @Override
                    public SaloonInfo apply(SaloonServicesResponse saloonServicesResponse, MemberReponse memberReponse) throws Exception {
                        SaloonInfo saloonInfo  = new SaloonInfo();
                        saloonInfo.setSaloonServicesResponse(saloonServicesResponse);
                        saloonInfo.setMemberReponse(memberReponse);
                        return saloonInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaloonInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(SaloonInfo saloonInfo) {
                tWeakReference.get().showLoading(false);
                if (saloonInfo!=null){
                      SaloonServicesResponse saloonServicesResponse  = saloonInfo.getSaloonServicesResponse();
                      MemberReponse memberReponse = saloonInfo.getMemberReponse();
                    if (saloonServicesResponse !=null && "200".equals(saloonServicesResponse.getCode()) && "1".equals(saloonServicesResponse.getMessage())){
                        tWeakReference.get().onSaloonServicesFetch(saloonServicesResponse.getData());
                        tWeakReference.get().showSericeTiming(saloonServicesResponse.getOp_time(),saloonServicesResponse.getClo_time());
                    }else {
                        tWeakReference.get().onSallonServicesetchedrror("Saloon Services not available");
                    }
                    if (memberReponse!=null && !TextUtils.isEmpty(memberReponse.getMessage()) && "1".equals(memberReponse.getMessage())){
                        tWeakReference.get().onMemberFetcheed(memberReponse.getData());
                    }else {
                        tWeakReference.get().onMemberFetchedError("Saloon Members not available");
                    }
                }else {
                    tWeakReference.get().onSallonServicesetchedrror("Unable to Fetch Saloon Services.");
                    tWeakReference.get().onMemberFetchedError("Unable to Fetch Members");
                }
            }

            @Override
            public void onError(Throwable e) {
                tWeakReference.get().showLoading(false);
                tWeakReference.get().showError(AppUtils.showError(e));
            }

            @Override
            public void onComplete() {

            }
        });


    }
    public void fetchServicePriceList(String owned_by, final String cat_name){
           tWeakReference.get().showLoadingForPrice(true);
            NetworkManager.getInstance().getApiServices().fetchServicePrice(owned_by,cat_name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ServicePriceResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(ServicePriceResponse servicePriceResponse) {
                            tWeakReference.get().showLoadingForPrice(false);
                              if (servicePriceResponse!=null && "1".equals(servicePriceResponse.getMessage())){
                                  tWeakReference.get().onFetchedServicePrices(servicePriceResponse.getData(),cat_name);
                              }else {
                                  tWeakReference.get().onFetchServicePriceFaied("Unable to fetch Price list.");
                              }
                        }

                        @Override
                        public void onError(Throwable e) {
                            tWeakReference.get().showLoadingForPrice(false);
                            tWeakReference.get().showError(AppUtils.showError(e));
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
    }
    public void bookSabool(HashMap<String,String>hashMap){
        tWeakReference.get().showProgress(true);
        NetworkManager.getInstance().getApiServices().bookSaloon(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(MobileResponse mobileResponse) {
                        tWeakReference.get().showProgress(false);
                        if ("1".equals(mobileResponse.getMessage())){
                            tWeakReference.get().bookingSucced("Thank you for choosing our services! You have succesfully booked this Saloon.");
                        }else {
                            tWeakReference.get().bookingFailed("Oops booking failed! Please try after sometimes");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        tWeakReference.get().showProgress(false);
                        tWeakReference.get().showError(AppUtils.showError(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void onDestroy(){
        compositeDisposable.dispose();
        tWeakReference = null;
    }
}
