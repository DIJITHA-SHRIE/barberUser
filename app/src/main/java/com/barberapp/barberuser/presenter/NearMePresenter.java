package com.barberapp.barberuser.presenter;

import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.pojos.AdvResponse;
import com.barberapp.barberuser.pojos.SaloonResponse;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.NearMeView;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NearMePresenter<T extends NearMeView> {
    CompositeDisposable compositeDisposable;
    WeakReference<T> tWeakReference;
    public void setNearMeView(T view){
        compositeDisposable = new CompositeDisposable();
        tWeakReference = new WeakReference<>(view);
    }
    public void fetchSaloonsNearMe(String lat,String lng){
        tWeakReference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().fetchSallonsNearMe(lat,lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaloonResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(SaloonResponse saloonResponse) {
                        tWeakReference.get().showLoading(false);
                         if (saloonResponse!=null && "200".equals(saloonResponse.getCode())){
                             tWeakReference.get().onFecthSallons(saloonResponse.getData());
                         }else {
                             tWeakReference.get().onFailedFetch(AppConstants.EXTRA_API_ERROR);
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
    public void fetchAdv() {
        ((NearMeView) this.tWeakReference.get()).showLoading(true);
        NetworkManager.getInstance().getApiServices().fetchAdvAPI().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdvResponse>() {
            public void onSubscribe(Disposable d) {
                NearMePresenter.this.compositeDisposable.add(d);
            }

            public void onNext(AdvResponse saloonResponse) {
                ((NearMeView) NearMePresenter.this.tWeakReference.get()).showLoading(false);
                if (saloonResponse != null) {
                    if ("1".equals(saloonResponse.getMessage())) {
                        ((NearMeView) NearMePresenter.this.tWeakReference.get()).onFetchAdv(saloonResponse);
                        return;
                    }
                }
                ((NearMeView) NearMePresenter.this.tWeakReference.get()).onFailedAdv(AppConstants.EXTRA_API_ERROR);
            }

            public void onError(Throwable e) {
                ((NearMeView) NearMePresenter.this.tWeakReference.get()).showLoading(false);
                ((NearMeView) NearMePresenter.this.tWeakReference.get()).showError(AppUtils.showError(e));
            }

            public void onComplete() {
            }
        });
    }

    public void  onDestroy(){
        compositeDisposable.dispose();
        tWeakReference = null;
    }
}
