package com.barberapp.barberuser.presenter;

import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.pojos.MyBookingResponse;
import com.barberapp.barberuser.view.MyBookingView;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyBookingPresenter<T extends MyBookingView> {
    WeakReference<T> reference;
    CompositeDisposable disposable;
    public void setView(T view){
        disposable=new CompositeDisposable();
        reference=new WeakReference<T>(view);
    }
    public void showMyBookings(String userid){
        reference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().myBookings(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyBookingResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(MyBookingResponse myBookingResponse) {
                        reference.get().showLoading(false);
                        if (myBookingResponse!=null && "1".equals(myBookingResponse.getMessage())){
                            reference.get().ongettingBookings(myBookingResponse.getData());
                        }else {
                            reference.get().onNotGettingBookings("Something went wrong . Please try after sometime");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        reference.get().showLoading(false);
                        reference.get().onNotGettingBookings("Something went wrong . Please try after sometime");
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
