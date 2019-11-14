package com.barberapp.barberuser.presenter;

import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.pojos.MobileResponse;
import com.barberapp.barberuser.pojos.ProfileResponse;
import com.barberapp.barberuser.pojos.ValidateUserResponse;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.ProfileView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileInfoPresenter<T extends ProfileView> {
    CompositeDisposable compositeDisposable;
    WeakReference<T> tWeakReference;
    public void setProfileView(T view){
        compositeDisposable = new CompositeDisposable();
        tWeakReference = new WeakReference<>(view);
    }
    public void fetchProfileInfo(String mobile){
        tWeakReference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().validateUser(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ValidateUserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ValidateUserResponse validateUserResponse) {
                        tWeakReference.get().showLoading(false);
                         if (validateUserResponse!=null  && "1".equals(validateUserResponse.getMessage())){
                             tWeakReference.get().onProfileSuccess(validateUserResponse.getData().get(0));
                         }else {
                             tWeakReference.get().onProfileFailed(AppConstants.EXTRA_API_ERROR);
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
    public void updateMyProfile(String phone_no,String name,String mail_id,String address){
        tWeakReference.get().showLoading(true);
        HashMap<String,String> hashMap = new HashMap<>(4);
        hashMap.put("phone_no",phone_no);
        hashMap.put("name",name);
        hashMap.put("mail_id",mail_id);
        hashMap.put("address",address);
        NetworkManager.getInstance().getApiServices().updateProfile(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d1) {
                        compositeDisposable.add(d1);
                    }

                    @Override
                    public void onNext(MobileResponse mobileResponse) {
                        tWeakReference.get().showLoading(false);
                        if (mobileResponse!=null  && "1".equals(mobileResponse.getMessage())){
                            tWeakReference.get().onProfileUpdated();
                        }else {
                            tWeakReference.get().onProfileFailed(AppConstants.EXTRA_API_ERROR);
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
    public void  onDestroy(){
        compositeDisposable.dispose();
        tWeakReference = null;
    }
}
