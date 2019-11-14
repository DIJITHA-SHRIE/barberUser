package com.barberapp.barberuser.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.utils.AvenuesParams;
import com.barberapp.barberuser.utils.MyRandomNumber;
import com.barberapp.barberuser.view.activity.WebViewActivity;
import com.paytm.pgsdk.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.app.Activity.RESULT_OK;


public class MyWalletFragment extends Fragment {
    private Button btnAddMoney;
    private EditText etAmount;
    private AppSharedPrefference appSharedPrefference;
    private static final int CCAVENU_CODE=201;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mywallet,container,false);
        btnAddMoney = view.findViewById(R.id.btnAddMoney);
        appSharedPrefference = new AppSharedPrefference(getActivity());
        etAmount = view.findViewById(R.id.etAmount);
        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = etAmount.getText().toString().trim();
                if (!TextUtils.isEmpty(amount)){
                    etAmount.setError(null);
                    addMoney(amount);
                }else {
                    etAmount.setError("Amount can't be empty");
                }
            }
        });
        return view;
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
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra(AvenuesParams.ACCESS_CODE, vAccessCode);
            intent.putExtra(AvenuesParams.MERCHANT_ID, vMerchantId);
            intent.putExtra(AvenuesParams.ORDER_ID, orderid);
            intent.putExtra(AvenuesParams.CURRENCY, vCurrency);
            intent.putExtra(AvenuesParams.AMOUNT, vAmount);

            intent.putExtra(AvenuesParams.REDIRECT_URL, "https://www.kagami.co.in/ccavenue/ccavResponseHandler.php");
            intent.putExtra(AvenuesParams.CANCEL_URL, "https://www.kagami.co.in/ccavenue/ccavResponseHandler.php");
            intent.putExtra(AvenuesParams.RSA_KEY_URL, "https://www.kagami.co.in/ccavenue/GetRSA.php");


            startActivityForResult(intent,CCAVENU_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CCAVENU_CODE && resultCode == RESULT_OK && data != null) {
            Toast.makeText(getActivity(), "Amount added to wallet.", Toast.LENGTH_SHORT).show();
        }
    }
}
