package com.barberapp.barberuser.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.pojos.MyBooking;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.utils.AvenuesParams;
import com.barberapp.barberuser.utils.MyRandomNumber;
import com.barberapp.barberuser.view.activity.WebViewActivity;
import com.paytm.pgsdk.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyBookingHolder>{
    private ArrayList<MyBooking> myBookings;
    private Context context;
    private AppSharedPrefference appSharedPrefference;
    private static final int CCAVENU_CODE=201;

    public MyBookingAdapter(ArrayList<MyBooking> myBookings, Context context) {
        this.myBookings = myBookings;
        this.context = context;
        appSharedPrefference = new AppSharedPrefference(context);
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
        String status = myBooking.getStatus();

        myBookingHolder.txtSaloonTititle.setText(myBooking.getSaloon_name());
        //myBookingHolder.txtStyle.setText(myBooking.getStyle_name());
        myBookingHolder.txtTiming.setText(myBooking.getStart_time()+"-"+myBooking.getEnd_time());
        myBookingHolder.txtTotalPrice.setText("Rs "+ myBooking.getTotal_price());
        myBookingHolder.txtDate.setText(myBooking.getBooking_time());

        if(status.equals("0")){
            myBookingHolder.pay_money.setText("WAIT LIST");
            myBookingHolder.pay_money.setClickable(false);
        }
       else  if(status.equals("1")){
            myBookingHolder.pay_money.setText("PAY");
            myBookingHolder.pay_money.setClickable(true);
        } else if(status.equals("2")){
            myBookingHolder.pay_money.setText("REJECTED");
            myBookingHolder.pay_money.setClickable(false);

        } else if(status.equals("3")){
            myBookingHolder.pay_money.setText("PAID");
            myBookingHolder.pay_money.setClickable(false);
        }

        myBookingHolder.pay_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addMoney(myBooking.getTotal_price());
            }
        });
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
        @BindView(R.id.pay_money)
        Button pay_money;
        public MyBookingHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    private void addMoney(String amount) {
        String orderid = appSharedPrefference.getMobileNumber()+""+ MyRandomNumber.getInstance().getRandmNo();
        Log.v("RANDOM>>>",orderid);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("orderid",orderid);
        hashMap.put("email","");
        hashMap.put("mobile",appSharedPrefference.getMobileNumber());
        hashMap.put("amount",amount);
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            Log.v("PAYTM>>",entry.getKey()+" : "+entry.getValue());
        }
        //bookingViewBookingPresenter.getCheckSumHash(hashMap);
        String vAccessCode = "AVQD86GG54BF75DQFB";
        String vMerchantId = "184046";
        String vCurrency = "INR";
        String vAmount = amount;
        if(!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")){
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(AvenuesParams.ACCESS_CODE, vAccessCode);
            intent.putExtra(AvenuesParams.MERCHANT_ID, vMerchantId);
            intent.putExtra(AvenuesParams.ORDER_ID, orderid);
            intent.putExtra(AvenuesParams.CURRENCY, vCurrency);
            intent.putExtra(AvenuesParams.AMOUNT, vAmount);

            intent.putExtra(AvenuesParams.REDIRECT_URL, "https://www.kagami.co.in/ccavenue/ccavResponseHandler.php");
            intent.putExtra(AvenuesParams.CANCEL_URL, "https://www.kagami.co.in/ccavenue/ccavResponseHandler.php");
            intent.putExtra(AvenuesParams.RSA_KEY_URL, "https://www.kagami.co.in/ccavenue/GetRSA.php");


            ((Activity) context).startActivityForResult(intent,CCAVENU_CODE);
        }
    }
}
