package com.barberapp.barberuser.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.pojos.CheckSumReponse;
import com.barberapp.barberuser.pojos.Saloon;
import com.barberapp.barberuser.pojos.SaloonServices;
import com.barberapp.barberuser.pojos.ServiePrice;
import com.barberapp.barberuser.presenter.BookingPresenter;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.utils.AvenuesParams;
import com.barberapp.barberuser.utils.MyRandomNumber;
import com.barberapp.barberuser.utils.ServiceUtility;
import com.barberapp.barberuser.view.BookingView;
import com.barberapp.barberuser.view.adapter.SaloonServiceAdapter;
import com.barberapp.barberuser.view.adapter.ServicePriceAdapter;
import com.paytm.pgsdk.Log;
import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingActivity extends AppCompatActivity implements BookingView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rcvSelectedServices)
    RecyclerView rcvSelectedServices;
    private ArrayList<ServiePrice> prices;
    private Saloon saloon;
    private String saloonOpentime,endTime,totdurations,totPrices,serviceIds,mobileNumber;
    @BindView(R.id.txtSaloonName)
    TextView txtSaloonName;
    @BindView(R.id.tstStartTime)
    TextView tstStartTime;
    @BindView(R.id.txtEndTme)
    TextView txtEndTme;
    @BindView(R.id.btnBook)
    Button btnBook;
    private BookingPresenter<BookingView> bookingViewBookingPresenter;
    private ProgressDialog progressDialog;
    private int bookingStatus=0;
    private PaytmOrder Order;
    private static final int CCAVENU_CODE=209;
    //for Staging
    PaytmPGService service = PaytmPGService.getStagingService();
    // for Production
    //PaytmPGService service = PaytmPGService.getProductionService();
    PaytmClientCertificate Certificate;
    private AppSharedPrefference appSharedPrefference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appSharedPrefference = new AppSharedPrefference(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Booking...");
        progressDialog.setCanceledOnTouchOutside(false);
        rcvSelectedServices.setLayoutManager(new LinearLayoutManager(this));
        rcvSelectedServices.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        saloon = getIntent().getParcelableExtra(AppConstants.EXTRA_SALOON);
        saloonOpentime = getIntent().getStringExtra(AppConstants.EXTRA_START_TIME);
        endTime = getIntent().getStringExtra(AppConstants.EXTRA_END_TIME);
        totdurations = getIntent().getStringExtra(AppConstants.EXTRA_TOTAL_DURATION);
        totPrices = getIntent().getStringExtra(AppConstants.EXTRA_TOTAL_PRICE);
        serviceIds = getIntent().getStringExtra(AppConstants.EXTRA_SURVICE_IDS);
        mobileNumber = getIntent().getStringExtra(AppConstants.EXTRA_MOBILE);
        prices = getIntent().getParcelableArrayListExtra(AppConstants.EXTRA_SERVICE_PRICES);
        txtSaloonName.setText(saloon.getSaloon_name());
        tstStartTime.setText(saloonOpentime);
        txtEndTme.setText(endTime);
        ServicePriceAdapter servicePriceAdapter = new ServicePriceAdapter(this,prices);
        rcvSelectedServices.setAdapter(servicePriceAdapter);
        bookingViewBookingPresenter=new BookingPresenter<>();
        bookingViewBookingPresenter.setSallonStaffView(this);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppUtils.isOnLine(BookingActivity.this)){
                    Toast.makeText(BookingActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    AlertDialog.Builder alBuilder = new AlertDialog.Builder(BookingActivity.this,R.style.AlertDialogTheme);
                    alBuilder.setTitle("Are you sure to book?");
                    alBuilder.setMessage("You are booking for "+saloon.getSaloon_name()+" . Time of booking: "+ saloonOpentime+" Please try to be on time to avoid inconveniences.");
                    alBuilder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getCheckSumHash(saloonOpentime,mobileNumber,totPrices,saloon,endTime,totdurations,serviceIds);
                        }
                    });
                    alBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }

    private void getCheckSumHash(String saloonOpentime, String mobileNumber, String totPrices, Saloon saloon, String endTime, String totdurations, String serviceIds) {
        String orderid = appSharedPrefference.getMobileNumber()+""+ MyRandomNumber.getInstance().getRandmNo();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("orderid",orderid);
        hashMap.put("email","");
        hashMap.put("mobile",mobileNumber);
        hashMap.put("amount",totPrices);
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            Log.v("PAYTM>>",entry.getKey()+" : "+entry.getValue());
        }
        //bookingViewBookingPresenter.getCheckSumHash(hashMap);
        String vAccessCode = "AVQD86GG54BF75DQFB";
        String vMerchantId = "184046";
        String vCurrency = "INR";
        String vAmount = totPrices;
        if(!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")){
            Intent intent = new Intent(this,WebViewActivity.class);
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

    private void paywithPaytm(final String saloonOpentime, final String mobileNumber, final String totPrices, final Saloon saloon, final String endTime, final String totdurations, final String serviceIds, final CheckSumReponse checkSumReponse) {
        HashMap<String, String> paramMap = new HashMap<String,String>(11);
        paramMap.put( "MID" , checkSumReponse.getMID());
// Key in your staging and production MID available in your dashboard
        paramMap.put( "ORDER_ID" , checkSumReponse.getORDER_ID());
        paramMap.put( "CUST_ID" , checkSumReponse.getCUST_ID());
        paramMap.put( "MOBILE_NO" , checkSumReponse.getMOBILE_NO());
        paramMap.put( "EMAIL" , "");
        paramMap.put( "CHANNEL_ID" , "WAP");
        paramMap.put( "TXN_AMOUNT" , checkSumReponse.getTXN_AMOUNT());
        paramMap.put( "WEBSITE" , "APPSTAGING");
// This is the staging value. Production value is available in your dashboard
        paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
// This is the staging value. Production value is available in your dashboard
        paramMap.put( "CALLBACK_URL", checkSumReponse.getCALLBACK_URL());
        paramMap.put( "CHECKSUMHASH" , checkSumReponse.getCHECKSUMHASH());
        PaytmOrder Order = new PaytmOrder(paramMap);
        service.initialize(Order, null);
        service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
                Log.v("PAYTM>>",inErrorMessage);
                Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();
            }
            public void onTransactionResponse(Bundle inResponse) {
                Log.v("PAYTM>>",inResponse.toString());
                Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                bookSaloon(saloonOpentime,mobileNumber,totPrices,saloon,endTime,totdurations,serviceIds,checkSumReponse);

            }
            public void networkNotAvailable() {
                Log.v("PAYTM>>","Network connection error: Check your internet connectivity");
                Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
            }
            public void clientAuthenticationFailed(String inErrorMessage) {
                Log.v("PAYTM>>",inErrorMessage);
                Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
            }
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                Log.v("PAYTM>>",inErrorMessage);
                Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
            }
            public void onBackPressedCancelTransaction() {
                Log.v("PAYTM>>","Transaction cancelled");
                Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();
            }
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Log.v("PAYTM>>","Transaction cancelled");
                Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bookSaloon(String saloonOpentime, String mobileNumber, String totPrices, Saloon saloon, String endTime, String totdurations, String serviceIds,CheckSumReponse checkSumReponse) {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user_id",mobileNumber);
        hashMap.put("duration",totdurations);
        hashMap.put("start_time",saloonOpentime);
        hashMap.put("end_time",endTime);
        hashMap.put("saloon_id",saloon.getId());
        hashMap.put("service_id",serviceIds);
        hashMap.put("total_price",totPrices);
        bookingViewBookingPresenter.bookSaloon(hashMap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void bookingSucced(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        bookingStatus=1;
        Intent intent = new Intent();
        intent.setAction(AppConstants.EXTRA_BOOKING);
        intent.putExtra(AppConstants.EXTRA_BOOKING,bookingStatus);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void bookingFailed(String err) {
        Toast.makeText(this, err, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onSuccessCheckSum(CheckSumReponse checkSumReponse) {
        paywithPaytm(saloonOpentime,mobileNumber,totPrices,saloon,endTime,totdurations,serviceIds,checkSumReponse);
    }

    @Override
    public void onFailedCheckSum(String err) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean isLoaing) {
        if (isLoaing){
            progressDialog.show();
        }else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookingViewBookingPresenter=null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CCAVENU_CODE && resultCode == RESULT_OK && data != null) {
            bookSaloon(saloonOpentime,mobileNumber,totPrices,saloon,endTime,totdurations,serviceIds,null);
        }
    }
}
