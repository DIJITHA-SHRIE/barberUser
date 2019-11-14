package com.barberapp.barberuser.presenter;

import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.pojos.MobileResponse;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.OtpView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VerifyOtpPresenter<T extends OtpView> {
    WeakReference<T> weakReference;
    CompositeDisposable compositeDisposable;
    public void setView(T view){
        compositeDisposable = new CompositeDisposable();
        weakReference = new WeakReference<>(view);
    }
    public void verifyOtp(Map<String,String> map){
        weakReference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().verifyotp(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(MobileResponse mobileResponse) {
                        weakReference.get().showLoading(false);
                        //weakReference.get().onSuccess(mobileResponse.getMessage());
                        if (mobileResponse!=null && "1".equals(mobileResponse.getMessage())){
                            weakReference.get().onSuccess("Thanks . Welcome to our Barber Service!");
                        }else {
                            weakReference.get().onFailed("Something went wrong");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        weakReference.get().showLoading(false);
                        weakReference.get().onFailed(AppUtils.showError(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void  onDestroy(){
        compositeDisposable.dispose();
        weakReference=null;
    }
}
