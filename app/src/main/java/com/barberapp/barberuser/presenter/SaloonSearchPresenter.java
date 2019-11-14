package com.barberapp.barberuser.presenter;

import com.barberapp.barberuser.network.NetworkManager;
import com.barberapp.barberuser.pojos.CategoryResponse;
import com.barberapp.barberuser.pojos.FacilityResponse;
import com.barberapp.barberuser.pojos.SaloonResponse;
import com.barberapp.barberuser.pojos.SaloonSearchResponse;
import com.barberapp.barberuser.pojos.SubCategoryResponse;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.SearchSaloonView;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SaloonSearchPresenter<T extends SearchSaloonView> {
    CompositeDisposable compositeDisposable;
    WeakReference<T> tWeakReference;
    public void setSearchSaloonView(T view){
        compositeDisposable = new CompositeDisposable();
        tWeakReference = new WeakReference<>(view);
    }
    public void fetchSearchSaloons(String lat,String lng,String gender,String expertise_id,String serviceTime,String facilities_id){
        tWeakReference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().fetchSallonSearchAPI(lat,lng,gender,expertise_id,serviceTime,facilities_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaloonSearchResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(SaloonSearchResponse saloonResponse) {
                        tWeakReference.get().showLoading(false);
                        if (saloonResponse!=null ){
                            tWeakReference.get().onFetchSearchedSaloon(saloonResponse);
                        }else {
                            tWeakReference.get().onFetchSaloonError(AppConstants.EXTRA_API_ERROR);
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
    public void fetchCategory(){
        tWeakReference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().fetchCategoryAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CategoryResponse saloonResponse) {
                        tWeakReference.get().showLoading(false);
                        if (saloonResponse!=null ){
                            tWeakReference.get().onFetchCategory(saloonResponse);
                        }else {
                            tWeakReference.get().onFetchCategoryError(AppConstants.EXTRA_API_ERROR);
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
    public void fetchSubCategory(String catId){
        tWeakReference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().fetchSubCatAPI(catId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SubCategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(SubCategoryResponse saloonResponse) {
                        tWeakReference.get().showLoading(false);
                        if (saloonResponse!=null ){
                            tWeakReference.get().onFetchSubCategory(saloonResponse);
                        }else {
                            tWeakReference.get().onFetchSubCategoryError(AppConstants.EXTRA_API_ERROR);
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
    public void fetchFacilities(){
        tWeakReference.get().showLoading(true);
        NetworkManager.getInstance().getApiServices().fetchFacilityAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FacilityResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(FacilityResponse saloonResponse) {
                        tWeakReference.get().showLoading(false);
                        if (saloonResponse!=null ){
                            tWeakReference.get().onFacilitySearch(saloonResponse);
                        }else {
                            tWeakReference.get().onFacilitySearchError(AppConstants.EXTRA_API_ERROR);
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
}
