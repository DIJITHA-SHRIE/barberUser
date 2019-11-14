package com.barberapp.barberuser.presenter;

import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.pojos.MobileResponse;
import com.barberapp.barberuser.pojos.ResetResponse;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.ResetPwdView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPwdPresenter<T extends ResetPwdView> {
    WeakReference<T> tWeakReference;
    private CompositeDisposable compositeDisposable;
    public void setView(T view){
        tWeakReference = new WeakReference<>(view);
        compositeDisposable = new CompositeDisposable();
    }
    public void forgotPassword(final String mobile){
        tWeakReference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().forgotPwd(mobile)
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
                            tWeakReference.get().onSuccess("OTP has been sent to your Mobile number");
                        }else {
                            tWeakReference.get().onFailed("Something went wrong. Please try again!");
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
    public void resetPassword(String mobileno,String newpwd, String otp){
        tWeakReference.get().showLoading(true);
       /* Call<ResetResponse> resetResponseCall = NetworkManager.getInstance().getResetApiService().resetPwd(mobileno,newpwd,otp);
        resetResponseCall.enqueue(new Callback<ResetResponse>() {
            @Override
            public void onResponse(Call<ResetResponse> call, Response<ResetResponse> response) {
                tWeakReference.get().showLoading(false);
                if (response!= null && response.isSuccessful()){
                    tWeakReference.get().onSuccess("Password changed successful");
                }else {
                    //tWeakReference.get().onFailed("Something went wrong");
                    tWeakReference.get().onSuccess("Password changed successful");

                }
            }

            @Override
            public void onFailure(Call<ResetResponse> call, Throwable t) {
                tWeakReference.get().showLoading(false);
                tWeakReference.get().showError("Something went wrong");
                //tWeakReference.get().onSuccess("Password changed successful");
            }
        });*/
        /*HashMap<String,String> map = new HashMap<>();
        map.put("phone_no",mobileno);
        map.put("password",newpwd);
        map.put("otp",otp);*/
        NetworkManager.getInstance().getApiServices().resetPwd(mobileno,newpwd,otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResetResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ResetResponse mobileResponse) {
                        tWeakReference.get().showLoading(false);
                        if (mobileResponse!=null && "1".equals(mobileResponse.getMessage())){
                            tWeakReference.get().onSuccess("Password changed successful");
                        }else {
                            tWeakReference.get().onFailed("Something went wrong");
                        }
                        //tWeakReference.get().onSuccess("Password changed successful");
                    }

                    @Override
                    public void onError(Throwable e) {
                        tWeakReference.get().showLoading(false);
                        tWeakReference.get().showError("Something went wrong");
                        //tWeakReference.get().onSuccess("Password changed successful");
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
