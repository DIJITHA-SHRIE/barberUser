package com.barberapp.barberuser.presenter;

import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.pojos.CheckSumReponse;
import com.barberapp.barberuser.pojos.MobileResponse;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.BookingView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BookingPresenter<T extends BookingView> {
    WeakReference<T> tWeakReference;
    CompositeDisposable compositeDisposable;
    public void setSallonStaffView(T view){
        tWeakReference = new WeakReference<>(view);
        compositeDisposable = new CompositeDisposable();
    }
    public void bookSaloon(HashMap<String,String> hashMap){
        tWeakReference.get().showLoading(true);
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
                        tWeakReference.get().showLoading(false);
                        if ("1".equals(mobileResponse.getMessage())){
                            tWeakReference.get().bookingSucced("Thank you for choosing our services! You have succesfully booked this Saloon.");
                        }else {
                            tWeakReference.get().bookingFailed("Oops booking failed! Please try after sometimes");
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

    public void getCheckSumHash(HashMap<String,String> hashMap){
        tWeakReference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().getCheckSum(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CheckSumReponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CheckSumReponse checkSumReponse) {
                        tWeakReference.get().showLoading(false);
                        if (checkSumReponse!=null){
                            tWeakReference.get().onSuccessCheckSum(checkSumReponse);
                        }else {
                            tWeakReference.get().onFailedCheckSum("Oops booking failed! Please try after sometimes");
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

    public void onDestroy(){
        compositeDisposable.dispose();
        tWeakReference = null;
    }
}
