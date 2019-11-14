package com.barberapp.barberuser.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.pojos.MyBooking;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyBookingHolder>{
    private ArrayList<MyBooking> myBookings;
    private Context context;

    public MyBookingAdapter(ArrayList<MyBooking> myBookings, Context context) {
        this.myBookings = myBookings;
        this.context = context;
    }

    @NonNull
    @Override
    public MyBookingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.mybooking_list,viewGroup,false);
        return new MyBookingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingHolder myBookingHolder, int i) {
        MyBooking myBooking = myBookings.get(i);
        myBookingHolder.txtSaloonTititle.setText(myBooking.getSaloon_name());
        myBookingHolder.txtStyle.setText(myBooking.getStyle_name());
        myBookingHolder.txtTiming.setText(myBooking.getStart_time()+"-"+myBooking.getEnd_time());
        myBookingHolder.txtTotalPrice.setText("Rs "+ myBooking.getTotal_price());
        myBookingHolder.txtDate.setText(myBooking.getBooking_time());
    }

    @Override
    public int getItemCount() {
        return myBookings.size();
    }

    public class MyBookingHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtSaloonTititle)
        TextView txtSaloonTititle;
        @BindView(R.id.txtStyle)
        TextView txtStyle;
        @BindView(R.id.txtTiming)
        TextView txtTiming;
        @BindView(R.id.txtTotalPrice)
        TextView txtTotalPrice;
        @BindView(R.id.txtDate)
        TextView txtDate;
        public MyBookingHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
