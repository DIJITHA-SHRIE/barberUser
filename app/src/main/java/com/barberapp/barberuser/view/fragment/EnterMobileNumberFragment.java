package com.barberapp.barberuser.view.fragment;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.presenter.ResetPwdPresenter;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.view.ResetPwdView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EnterMobileNumberFragment extends Fragment implements ResetPwdView {
    @BindView(R.id.btnSendOtp)
    Button btnSendOtp;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.pbarpwd)
    ProgressBar pbarpwd;
    private ResetPwdPresenter<ResetPwdView> viewResetPwdPresenter;
    String mobilenumber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_mobile_number,container,false);
        ButterKnife.bind(this,view);
        viewResetPwdPresenter = new ResetPwdPresenter<>();
        viewResetPwdPresenter.setView(EnterMobileNumberFragment.this);

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobilenumber = etMobile.getText().toString().trim();
                if (TextUtils.isEmpty(mobilenumber)){
                    Toast.makeText(getActivity(), "Please enter your registered mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    viewResetPwdPresenter.forgotPassword(mobilenumber);
                }

            }
        });
        return view;
    }

    @Override
    public void onSuccess(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction().remove(new EnterMobileNumberFragment()).commit();
        AppSharedPrefference appSharedPrefference = new AppSharedPrefference(getActivity());
        appSharedPrefference.saveTempMobile(mobilenumber);
        getFragmentManager().beginTransaction().replace(R.id.frameForgotPwd,new ResetPasswordFragment()).commit();
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean isLoaing) {
        if (isLoaing){
            pbarpwd.setVisibility(View.VISIBLE);
        }else {
            pbarpwd.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
