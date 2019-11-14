package com.barberapp.barberuser.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.pojos.ValidateUser;
import com.barberapp.barberuser.presenter.ProfileInfoPresenter;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.utils.AppUtils;
import com.barberapp.barberuser.view.ProfileView;
import com.barberapp.barberuser.view.activity.EditProfileActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyProfileFragment extends Fragment implements ProfileView {
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.progresBarMobile)
    ProgressBar progresBarMobile;
    private ProfileInfoPresenter<ProfileView> profileViewProfileInfoPresenter;
    private AppSharedPrefference appSharedPrefference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        appSharedPrefference=new AppSharedPrefference(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofile,container,false);
        ButterKnife.bind(this,view);
        profileViewProfileInfoPresenter = new ProfileInfoPresenter<>();
        profileViewProfileInfoPresenter.setProfileView(MyProfileFragment.this);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_menu_pic,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.actionEdit){
            Intent  intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public void onProfileSuccess(ValidateUser profileMessage) {
        if (profileMessage!=null){
            //ProfileDataTon.getInstance().setProfileMessage(profileMessage);
            etAddress.setText(profileMessage.getAddress());
            etEmail.setText(profileMessage.getMail_id());
            etMobile.setText(profileMessage.getPhone_no());
            etName.setText(profileMessage.getName());
            if (!TextUtils.isEmpty(profileMessage.getPhoto()))
            Picasso.with(getContext()).load(profileMessage.getPhoto()).placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
                    .into(imgProfile);

        }
    }

    @Override
    public void onProfileFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileUpdated() {
        Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
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
}
