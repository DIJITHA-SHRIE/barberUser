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
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.presenter.ResetPwdPresenter;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.view.ResetPwdView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResetPasswordFragment extends Fragment implements ResetPwdView {
    private ResetPwdPresenter<ResetPwdView> viewResetPwdPresenter;
    @BindView(R.id.etfpwdotp)
    EditText etfpwdotp;
    @BindView(R.id.etnewPwd)
    EditText etnewPwd;
    @BindView(R.id.etnewConfirmedPwd)
    EditText etnewConfirmedPwd;
    @BindView(R.id.btnResetPwd)
    Button btnResetPwd;
    @BindView(R.id.pbarpwd)
    ProgressBar pbarpwd;
    String mobile = "";
    private AppSharedPrefference appSharedPrefference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password,container,false);
        ButterKnife.bind(this,view);
        appSharedPrefference = new AppSharedPrefference(getActivity());
        viewResetPwdPresenter = new ResetPwdPresenter<>();
        viewResetPwdPresenter.setView(ResetPasswordFragment.this);

        btnResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = etfpwdotp.getText().toString().trim();
                String password = etnewConfirmedPwd.getText().toString().trim();
                String oldpwd = etnewPwd.getText().toString().trim();
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(oldpwd)){
                    Toast.makeText(getActivity(), "Filled can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!TextUtils.equals(password,oldpwd)){
                    Toast.makeText(getActivity(), "Password Missmacthed", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    viewResetPwdPresenter.resetPassword(appSharedPrefference.getTempMobile(),password,otp);
                }

            }
        });
        return view;
    }

    @Override
    public void onSuccess(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        getActivity().finish();
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
