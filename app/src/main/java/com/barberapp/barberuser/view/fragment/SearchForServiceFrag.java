package com.barberapp.barberuser.view.fragment;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.helper.CallClickedListner;
import com.barberapp.barberuser.helper.FacilityListener;
import com.barberapp.barberuser.helper.ShowOnMapListner;
import com.barberapp.barberuser.helper.onMultipleServiceListener;
import com.barberapp.barberuser.pojos.CategoryData;
import com.barberapp.barberuser.pojos.CategoryResponse;
import com.barberapp.barberuser.pojos.FacilityResponse;
import com.barberapp.barberuser.pojos.FirebasePartnerResponse;
import com.barberapp.barberuser.pojos.SaloonSearchData;
import com.barberapp.barberuser.pojos.SaloonSearchResponse;
import com.barberapp.barberuser.pojos.SubCategoryData;
import com.barberapp.barberuser.pojos.SubCategoryResponse;
import com.barberapp.barberuser.presenter.SaloonSearchPresenter;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.SearchSaloonView;
import com.barberapp.barberuser.view.activity.BaseActivity;
import com.barberapp.barberuser.view.activity.SubCategoryActivity;
import com.barberapp.barberuser.view.adapter.FacilityAdapter;
import com.barberapp.barberuser.view.adapter.MultipleServiceSelectAdp;
import com.barberapp.barberuser.view.adapter.SearchForServiceAdapter;
import java.util.ArrayList;
import java.util.Calendar;

public class SearchForServiceFrag extends Fragment implements SearchSaloonView, ShowOnMapListner, CallClickedListner, FacilityListener, onMultipleServiceListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CALL = 501;
    @BindView(R.id.book_linear_lay)
    LinearLayout book_linear_lay;
    ArrayList<CategoryData> categoryDataArrayList = new ArrayList<>();
    @BindView(R.id.category_spin)
    Spinner category_spin;
    String genderVal;
    @BindView(R.id.gender_ss)
    Spinner gender_ss;
    ArrayList<String> getMultipleService;
    ArrayList<String> getSelecedSubServices = new ArrayList<>();
    ArrayList<String> getSelecedSubServicesName = new ArrayList<>();
    ImageView image_filter;
    @BindView(R.id.linear_book_img)
    LinearLayout linear_book_img;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    @BindView(R.id.progress_ss)
    ProgressBar progress_ss;
    /* access modifiers changed from: private */
    public SaloonSearchPresenter<SearchSaloonView> saloonSearchPresenter;
    @BindView(R.id.saloon_search_rev)
    RecyclerView saloon_search_rev;
    @BindView(R.id.seacrchSaloonBtn)
    Button seacrchSaloonBtn;

    @BindView(R.id.ss_area)
    EditText ss_area;
    @BindView(R.id.ss_location)
    EditText ss_location;

    ArrayList<SubCategoryData> subCategoryDataArrayList;
    String subCategoryVal;
    @BindView(R.id.subCategory_spin)
    Spinner subCategory_spin;
    @BindView(R.id.time_ss)
    EditText time_ss;
    @BindView(R.id.ss_popup_serv_cat)
    EditText ss_popup_serv_cat;
    @BindView(R.id.ss_act_serv_sub_cat)
    EditText ss_act_serv_sub_cat;

    String trimmedSelectedSubServ;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && data != null) {
            this.getSelecedSubServices = data.getStringArrayListExtra("SELECTEDSUBSERVICES");
            this.getSelecedSubServicesName = data.getStringArrayListExtra("SELECTEDSUBSERVICES_NAME");
            this.ss_act_serv_sub_cat.setText(this.getSelecedSubServicesName.toString());
        }
    }

    public static SearchForServiceFrag newInstance(String param1, String param2) {
        SearchForServiceFrag fragment = new SearchForServiceFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_for, container, false);
        ButterKnife.bind( this, view);
        this.saloonSearchPresenter = new SaloonSearchPresenter<>();
        this.saloonSearchPresenter.setSearchSaloonView(this);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView textAddress = (TextView) toolbar.findViewById(R.id.message);
        this.image_filter = (ImageView) toolbar.findViewById(R.id.imgAllfilter);
        textAddress.setText("Book Service");
        this.image_filter.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SearchForServiceFrag.this.callFacility();
            }
        });
        this.saloonSearchPresenter.fetchCategory();
        this.ss_location.setText(BaseActivity.locality);
        this.ss_area.setText(BaseActivity.subLocality);
        return view;
    }

    /* access modifiers changed from: private */
    public void callFacility() {
        this.saloonSearchPresenter.fetchFacilities();
    }

    public void onButtonPressed(Uri uri) {
        OnFragmentInteractionListener onFragmentInteractionListener = this.mListener;
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentInteraction(uri);
        }
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public void onFetchSearchedSaloon(SaloonSearchResponse responses) {
        this.image_filter.setVisibility(View.VISIBLE);
        this.progress_ss.setVisibility(View.GONE);
        this.book_linear_lay.setVisibility(View.GONE);
        this.saloon_search_rev.setVisibility(View.VISIBLE);
        ArrayList<SaloonSearchData> saloonSearchDataArrayList = responses.getData();
        Log.i("saloonSearchArrayList", responses.getMessage());
        if (saloonSearchDataArrayList != null) {
            SearchForServiceAdapter adapter = new SearchForServiceAdapter(saloonSearchDataArrayList, getContext(),trimmedSelectedSubServ);
            this.saloon_search_rev.setLayoutManager(new LinearLayoutManager(getContext()));
            this.saloon_search_rev.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
            this.saloon_search_rev.setAdapter(adapter);
            adapter.setOnShowonMap(this);
            adapter.setOncallClicklisner(this);
            return;
        }
        Toast.makeText(getContext(), "No Saloon Available", Toast.LENGTH_LONG).show();
    }

    public void onFetchSaloonError(String error) {
    }

    public void onFetchCategory(CategoryResponse response) {
        this.categoryDataArrayList = response.getData();
        this.progress_ss.setVisibility(View.GONE);
        ArrayAdapter<CategoryData> catAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, response.getData());
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.category_spin.setAdapter(catAdapter);
    }

    public void onFetchCategoryError(String error) {
    }

    public void onFetchSubCategory(SubCategoryResponse response) {
        this.subCategoryDataArrayList = new ArrayList<>();
        this.subCategoryDataArrayList = response.getData();
        this.progress_ss.setVisibility(View.GONE);
        ArrayList<SubCategoryData> arrayList = this.subCategoryDataArrayList;
        if (arrayList == null || arrayList.size() <= 0) {
            ArrayAdapter<String> catAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.subCat));
            catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.subCategory_spin.setAdapter(catAdapter);
        } else {
            ArrayAdapter<SubCategoryData> catAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, response.getData());
            catAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.subCategory_spin.setAdapter(catAdapter2);
        }
        Intent in = new Intent(getActivity(), SubCategoryActivity.class);
        in.putExtra("SUBCATEGORIES", this.subCategoryDataArrayList);
        in.putExtra("SELECTEDSERVICE", this.getMultipleService);
        startActivityForResult(in, 1001);
    }

    public void onFetchSubCategoryError(String error) {
    }

    public void onFacilitySearch(FacilityResponse response) {
        showFacilityInPopUp(response);
    }

    private void showFacilityInPopUp(FacilityResponse response) {
        final FacilityAdapter adapter = new FacilityAdapter(getActivity(), response.getData(), this, "MENU");
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_facility, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, -2, -2);
        RecyclerView rv_pop_facility = (RecyclerView) popupView.findViewById(R.id.rv_facility);
        Button close_facility = (Button) popupView.findViewById(R.id.close_facility);
        ((Button) popupView.findViewById(R.id.search_facility_btn)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FacilityAdapter facilityAdapter = adapter;
                ArrayList<String> getCheckedvalues = FacilityAdapter.getCheckedvalues;
                StringBuilder sb = new StringBuilder();
                sb.append(getCheckedvalues);
                String str = "";
                sb.append(str);
                Log.i("getCheckedvalues", sb.toString());
                if (getCheckedvalues.size() > 0) {
                    SaloonSearchPresenter access$100 = SearchForServiceFrag.this.saloonSearchPresenter;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(BaseActivity.myLocation.getLatitude());
                    String sb3 = sb2.toString();
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str);
                    sb4.append(BaseActivity.myLocation.getLongitude());
                    access$100.fetchSearchSaloons(sb3, sb4.toString(), SearchForServiceFrag.this.genderVal, SearchForServiceFrag.this.subCategoryVal, SearchForServiceFrag.this.time_ss.getText().toString(), getCheckedvalues.toString());
                } else {
                    SaloonSearchPresenter access$1002 = SearchForServiceFrag.this.saloonSearchPresenter;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(str);
                    sb5.append(BaseActivity.myLocation.getLatitude());
                    String sb6 = sb5.toString();
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(str);
                    sb7.append(BaseActivity.myLocation.getLongitude());
                    access$1002.fetchSearchSaloons(sb6, sb7.toString(), SearchForServiceFrag.this.genderVal, SearchForServiceFrag.this.subCategoryVal, SearchForServiceFrag.this.time_ss.getText().toString(), "0");
                }
                popupWindow.dismiss();
            }
        });
        close_facility.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        rv_pop_facility.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_pop_facility.setAdapter(adapter);
        popupWindow.showAtLocation(popupView, 5, 0, 0);
    }

    public void onFacilitySearchError(String error) {
    }

    @Override
    public void onFirebasePartnerCall(FirebasePartnerResponse response) {

    }

    @Override
    public void onFirebasePartnerError(String err) {

    }

    public void showLoading(boolean isLoaing) {
        this.progress_ss.setVisibility(View.GONE);
    }

    public void showError(String error) {
    }

    public void onCallClicked(String mobilnumber) {
        if (VERSION.SDK_INT >= 23) {
            String str = "android.permission.CALL_PHONE";
            if (AppUtils.checkPermission(str, getActivity())) {
                makeCall(mobilnumber);
            } else {
                AppUtils.requestPermission((Fragment) this, new String[]{str}, (int) REQUEST_CALL);
            }
        } else {
            makeCall(mobilnumber);
        }
    }

    public void showonMap(String lat, String lng) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://maps.google.com/maps?q=loc:");
        sb.append(lat);
        sb.append(",");
        sb.append(lng);
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse(sb.toString()));
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            String str = "com.google.android.apps.maps";
            mapIntent.setClassName(str, "com.google.android.maps.MapsActivity");
            mapIntent.setPackage(str);
            startActivity(mapIntent);
        }
    }

    public void onFacilitySearchListener(String[] getvalues) {
    }

    public void onGetMultipleServiceListener(ArrayList<String> multipleServices, ArrayList<String> getMultipleServiceName) {
        Log.i("multipleServices", multipleServices.toString());
        this.getMultipleService = new ArrayList<>();
        this.getMultipleService = multipleServices;
        this.ss_popup_serv_cat.setText(getMultipleServiceName.toString());
    }

    @OnClick(R.id.seacrchSaloonBtn)
    public void onSearchSaloonBtn(View view) {
        String str = " ";
        String str2 = "";
        Log.i("getSelecedSubFrag", this.getSelecedSubServices.toString().replaceAll(str, str2));
         trimmedSelectedSubServ = this.getSelecedSubServices.toString().replaceAll(str, str2);
        SaloonSearchPresenter<SearchSaloonView> saloonSearchPresenter2 = this.saloonSearchPresenter;
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(BaseActivity.myLocation.getLatitude());
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append(BaseActivity.myLocation.getLongitude());
        saloonSearchPresenter2.fetchSearchSaloons(sb2, sb3.toString(), this.genderVal, trimmedSelectedSubServ, this.time_ss.getText().toString(), "0");
    }

    private void makeCall(String mobile) {
        try {
            Intent my_callIntent = new Intent("android.intent.action.CALL");
            StringBuilder sb = new StringBuilder();
            sb.append("tel:");
            sb.append(mobile);
            my_callIntent.setData(Uri.parse(sb.toString()));
            startActivity(my_callIntent);
        } catch (ActivityNotFoundException e) {
            FragmentActivity activity = getActivity();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Error in your phone call");
            sb2.append(e.getMessage());
            Toast.makeText(activity, sb2.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @OnItemSelected(R.id.category_spin)
    public void onCategorySelected(int position) {
        this.categoryDataArrayList.size();
    }

    @OnItemSelected(R.id.subCategory_spin)
    public void onSubCategorySelected(int position) {
        ArrayList<SubCategoryData> arrayList = this.subCategoryDataArrayList;
        if (arrayList != null && arrayList.size() > 0) {
            this.subCategoryVal = ((SubCategoryData) this.subCategoryDataArrayList.get(position)).getId();
        }
    }
    @OnClick(R.id.time_ss)
    public void onGetTime(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                EditText editText = SearchForServiceFrag.this.time_ss;
                StringBuilder sb = new StringBuilder();
                sb.append(selectedHour);
                sb.append(":");
                sb.append(selectedMinute);
                editText.setText(sb.toString());
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @OnItemSelected(R.id.gender_ss)
    public void onGetGender(int positio) {
        this.genderVal = this.gender_ss.getItemAtPosition(positio).toString();
    }

    private void multipleServiceInPopUp() {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.multiple_service_list, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, -2, -2);
        ListView list_multi_service = (ListView) popupView.findViewById(R.id.list_multi_service);
        ((Button) popupView.findViewById(R.id.multi_service_ok)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SearchForServiceFrag.this.getMultipleService != null) {
                    SearchForServiceFrag.this.saloonSearchPresenter.fetchSubCategory(SearchForServiceFrag.this.getMultipleService.toString());
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        list_multi_service.setAdapter(new MultipleServiceSelectAdp(getContext(), this.categoryDataArrayList, this));
        popupWindow.showAsDropDown(this.ss_popup_serv_cat, 0, 5);
    }

    @OnClick(R.id.ss_popup_serv_cat)
    public void onMultiServicePop(View view) {
        multipleServiceInPopUp();
    }
    @OnClick(R.id.ss_act_serv_sub_cat)
    public void onSelectSubCategories(View view) {
        Intent in = new Intent(getActivity(), SubCategoryActivity.class);
        in.putExtra("SUBCATEGORIES", this.subCategoryDataArrayList);
        in.putExtra("SELECTEDSERVICE", this.getMultipleService);
        startActivityForResult(in, 1001);
    }
}