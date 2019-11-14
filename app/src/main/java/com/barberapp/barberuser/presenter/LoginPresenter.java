package com.barberapp.barberuser.presenter;

import android.view.View;

import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.pojos.MobileResponse;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.LoginView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter<T extends LoginView> {
    WeakReference<T> tWeakReference;
    private CompositeDisposable compositeDisposable;
    public void setView(T view){
        tWeakReference = new WeakReference<>(view);
        compositeDisposable = new CompositeDisposable();
    }
    public void login(final String mobile, String password){
        tWeakReference.get().showLoading(true);
        HashMap<String,String> hashMap = new HashMap<>(2);
        hashMap.put("mobile_no",mobile);
        hashMap.put("password",password);
        NetworkManager.getInstance().getApiServices().login(hashMap)
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
                        if (mobileResponse!=null && "1".equals(mobileResponse.getMessage())){
                            tWeakReference.get().onLoginSuccess(mobile);
                        }else {
                            tWeakReference.get().onLoginFailed(AppConstants.EXTRA_API_ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        tWeakReference.get().showLoading(false);
                        tWeakReference.get().onLoginFailed(AppUtils.showError(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void onDestroy(){
        compositeDisposable.dispose();
        tWeakReference=null;
    }
}
