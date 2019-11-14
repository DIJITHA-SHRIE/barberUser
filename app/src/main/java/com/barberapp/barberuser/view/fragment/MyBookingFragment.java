package com.barberapp.barberuser.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.pojos.MyBooking;
import com.barberapp.barberuser.presenter.MyBookingPresenter;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.MyBookingView;
import com.barberapp.barberuser.view.adapter.MyBookingAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyBookingFragment extends Fragment implements MyBookingView {
    @BindView(R.id.rcvMyBooking)
    RecyclerView rcvMyBooking;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtNoBBoking)
    TextView txtNoBBoking;
    private MyBookingPresenter<MyBookingView> myBookingPresenter;
    private AppSharedPrefference appSharedPrefference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mybookings,container,false);
        ButterKnife.bind(this,view);
        rcvMyBooking.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvMyBooking.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        appSharedPrefference = new AppSharedPrefference(getActivity());
        myBookingPresenter = new MyBookingPresenter<>();
        myBookingPresenter.setView(MyBookingFragment.this);
        if (AppUtils.isOnLine(getActivity())){
            txtNoBBoking.setText("No Booking Avialable");
            txtNoBBoking.setVisibility(View.GONE);
            rcvMyBooking.setVisibility(View.VISIBLE);
            myBookingPresenter.showMyBookings(appSharedPrefference.getMobileNumber());
        }else {
            txtNoBBoking.setVisibility(View.VISIBLE);
            rcvMyBooking.setVisibility(View.GONE);
            txtNoBBoking.setText("No Internet Avialable");
        }
        return view;
    }

    @Override
    public void ongettingBookings(ArrayList<MyBooking> myBookings) {
        if (myBookings!=null && myBookings.size()>0){
            txtNoBBoking.setVisibility(View.GONE);
            rcvMyBooking.setVisibility(View.VISIBLE);
            MyBookingAdapter myBookingAdapter = new MyBookingAdapter(myBookings,getActivity());
            rcvMyBooking.setAdapter(myBookingAdapter);
        }else {
            txtNoBBoking.setVisibility(View.VISIBLE);
            rcvMyBooking.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNotGettingBookings(String err) {
        Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean isLoaing) {
        if (isLoaing){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
