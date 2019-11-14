package com.barberapp.barberuser.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.pojos.ProfileMessage;
import com.barberapp.barberuser.pojos.ValidateUser;
import com.barberapp.barberuser.presenter.ProfileInfoPresenter;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.utils.ProfileDataTon;
import com.barberapp.barberuser.view.ProfileView;
import com.barberapp.barberuser.view.activity.ProfileImageActivity;
import com.barberapp.barberuser.view.activity.UpdateNavigationActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EditProfileFragment extends Fragment implements ProfileView {
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.etFullName)
    EditText etFullName;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etEmailId)
    EditText etEmailId;
    @BindView(R.id.etFullAddress)
    EditText etFullAddress;
    @BindView(R.id.btnUpdateProfile)
    Button btnUpdateProfile;
    @BindView(R.id.progresBarMobile)
    ProgressBar progresBarMobile;
    private ProfileInfoPresenter<ProfileView> profileViewProfileInfoPresenter;
    private AppSharedPrefference appSharedPrefference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile,container,false);
        ButterKnife.bind(this,view);
        /*if (ProfileDataTon.getInstance()!=null){
            if (ProfileDataTon.getInstance().getProfileMessage()!=null){
                etFullAddress.setText(ProfileDataTon.getInstance().getProfileMessage().getAddress());
                etEmailId.setText(ProfileDataTon.getInstance().getProfileMessage().getMail_id());
                etPhoneNumber.setText(ProfileDataTon.getInstance().getProfileMessage().getPhone_no());
                etFullName.setText(ProfileDataTon.getInstance().getProfileMessage().getName());
                Picasso.get().load(ProfileDataTon.getInstance().getProfileMessage().getPhoto()).placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
                        .into(imgProfile);

            }
        }*/
        profileViewProfileInfoPresenter = new ProfileInfoPresenter<>();
        profileViewProfileInfoPresenter.setProfileView(EditProfileFragment.this);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                imgProfile,
                                ViewCompat.getTransitionName(imgProfile));
                startActivity(intent, options.toBundle());
            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname =etFullName.getText().toString().trim();
                String emailid = etEmailId.getText().toString().trim();
                String phone  = etPhoneNumber.getText().toString().trim();
                String fulladdress = etFullAddress.getText().toString().trim();
                if (!AppUtils.isOnLine(getActivity())){
                    Toast.makeText(getActivity(), AppConstants.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(fullname)){
                    etFullName.setError("Name can't be blank.");
                    return;
                }else if (TextUtils.isEmpty(emailid)){
                    etFullName.setError(null);
                    etEmailId.setError("Email id can't be blank");
                    return;
                }else if (TextUtils.isEmpty(fulladdress)){
                    etFullName.setError(null);
                    etEmailId.setError(null);
                    etFullAddress.setError("Address can't be blank");
                    return;
                }else {
                    profileViewProfileInfoPresenter.updateMyProfile(phone,fullname,emailid,fulladdress);
                }
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appSharedPrefference=new AppSharedPrefference(getActivity());
        if (AppUtils.isOnLine(getActivity())){
            profileViewProfileInfoPresenter.fetchProfileInfo(appSharedPrefference.getMobileNumber());
        }
    }

    @Override
    public void onProfileSuccess(ValidateUser validateUser) {
        if (!TextUtils.isEmpty(validateUser.getAddress()) && !TextUtils.equals("NA",validateUser.getAddress())){
            etFullAddress.setText(validateUser.getAddress());
        }
        etEmailId.setText(validateUser.getMail_id());
        etPhoneNumber.setText(validateUser.getPhone_no());
        etFullName.setText(validateUser.getName());
        if (!TextUtils.isEmpty(validateUser.getPhoto())){
            Picasso.with(getContext()).load(validateUser.getPhoto()).placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
                    .into(imgProfile);
        }

    }

    @Override
    public void onProfileFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileUpdated() {
        Intent intent = new Intent(getActivity(), UpdateNavigationActivity.class);
        startActivity(intent);
        getActivity().finish();
        /*if (AppUtils.isOnLine(getActivity())){
            profileViewProfileInfoPresenter.fetchProfileInfo(appSharedPrefference.getMobileNumber());
        }*/
    }

    @Override
    public void showLoading(boolean isLoaing) {
        if (isLoaing){
            progresBarMobile.setVisibility(View.VISIBLE);
        }else {
            progresBarMobile.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileViewProfileInfoPresenter.onDestroy();
    }
}
