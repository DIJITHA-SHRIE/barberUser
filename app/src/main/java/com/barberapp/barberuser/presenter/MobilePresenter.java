package com.barberapp.barberuser.presenter;

import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.pojos.MobileResponse;
import com.barberapp.barberuser.pojos.ValidateUserResponse;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.MobileView;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MobilePresenter<T extends MobileView> {
    WeakReference<T> reference;
    CompositeDisposable disposable;
    public void setView(T view){
        disposable=new CompositeDisposable();
        reference=new WeakReference<T>(view);
    }
    public void requestOtp(final String mobileNumber){
        reference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().registerMobile(mobileNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(MobileResponse mobileResponse) {
                        reference.get().showLoading(false);
                        if (mobileResponse!=null && "1".equals(mobileResponse.getMessage())){
                            reference.get().onSuccess(mobileNumber);
                        }else {
                            reference.get().onFailed(AppConstants.EXTRA_API_ERROR);
                        }
                        //reference.get().onSuccess(mobileNumber);
                        /*if (mobileResponse!=null && !TextUtils.equals("0",mobileResponse.getMessage())){
                            reference.get().onSuccess(mobileNumber);
                        }else {
                            reference.get().onFailed(AppConstants.EXTRA_API_ERROR);
                        }*/
                    }

                    @Override
                    public void onError(Throwable e) {
                        reference.get().showLoading(false);
                        reference.get().onFailed(AppUtils.showError(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
 public void validateUser(final String mobileNumber){
     reference.get().showLoading(true);
     NetworkManager.getInstance().getApiServices().validateUser(mobileNumber)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(new Observer<ValidateUserResponse>() {
                 @Override
                 public void onSubscribe(Disposable d1) {
                     disposable.add(d1);
                 }

                 @Override
                 public void onNext(ValidateUserResponse validateUserResponse) {
                     reference.get().showLoading(false);
                     if (validateUserResponse!=null && validateUserResponse.getData()!=null && "1".equals(validateUserResponse.getMessage())) {
                         if (!"NA".equals(validateUserResponse.getData().get(0).getPassword())){
                             reference.get().onValidateUser(mobileNumber);
                         }else {
                             reference.get().onPwNotFound(mobileNumber);
                         }
                     }else {
                         reference.get().onPwNotFound(mobileNumber);
                     }
                     //reference.get().onSuccess(mobileNumber);
                        /*if (mobileResponse!=null && !TextUtils.equals("0",mobileResponse.getMessage())){
                            reference.get().onSuccess(mobileNumber);
                        }else {
                            reference.get().onFailed(AppConstants.EXTRA_API_ERROR);
                        }*/
                 }

                 @Override
                 public void onError(Throwable e) {
                     reference.get().showLoading(false);
                     reference.get().onFailed(AppUtils.showError(e));
                 }

                 @Override
                 public void onComplete() {

                 }
             });
 }
 public void  onDestroy(){
        disposable.dispose();
        reference=null;
 }
}
