package com.barberapp.barberuser.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.barberapp.barberuser.BuildConfig;
import com.barberapp.barberuser.R;
import com.barberapp.barberuser.utils.AppConstants;
import com.barberapp.barberuser.utils.AppSharedPrefference;
import com.barberapp.barberuser.view.fragment.EditProfileFragment;
import com.barberapp.barberuser.view.fragment.LoadingFragment;
import com.barberapp.barberuser.view.fragment.MyBookingFragment;
import com.barberapp.barberuser.view.fragment.MyProfileFragment;
import com.barberapp.barberuser.view.fragment.MyWalletFragment;
import com.barberapp.barberuser.view.fragment.RefferEarcnFragment;

public class UpdateNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    private TextView txtAppVersion;
    private AppSharedPrefference appSharedPrefference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_navigation);
        appSharedPrefference = new AppSharedPrefference(UpdateNavigationActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Edit Profile");
        replaceFragment(new MyProfileFragment(),"Profile");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        txtAppVersion = headerView.findViewById(R.id.txtAppVersion);
        try{
            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            txtAppVersion.setText("Version: "+ versionName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        if (id == R.id.nav_edit_profile) {
            replaceFragment(new MyProfileFragment(),"My Profile");
        } else if (id == R.id.nav_mywallet) {
            replaceFragment(new MyWalletFragment(),"My Wallet");
        }else if (id==R.id.nav_mybooking){
            replaceFragment(new MyBookingFragment(),"My Bookings");
        } else if (id == R.id.nav_aboutus) {
            bundle.putString(AppConstants.EXTRA_LOADABLE_URL,AppConstants.EXTRA_ABOUT_US);
            replaceFragmentWithBundle(new LoadingFragment(),"About Us",bundle);
        } else if (id == R.id.nav_support) {
            bundle.putString(AppConstants.EXTRA_LOADABLE_URL,AppConstants.EXTRA_HELP_AND_SUPPORT_URL);
            replaceFragmentWithBundle(new LoadingFragment(),"Help & Support",bundle);
        } else if (id == R.id.nav_reffer) {
            replaceFragment(new RefferEarcnFragment(),"Refer your Friends");
        }else if (id==R.id.nav_terms_cond){
            bundle.putString(AppConstants.EXTRA_LOADABLE_URL,AppConstants.EXTRA_TERMS_AND_CONDITION_URL);
            replaceFragmentWithBundle(new LoadingFragment(),"Terms & Conditions",bundle);
        }else if (id==R.id.nav_privacy_policy){
            bundle.putString(AppConstants.EXTRA_LOADABLE_URL,AppConstants.EXTRA_PRIVACY_POLICY_URL);
            replaceFragmentWithBundle(new LoadingFragment(),"Privacy Policy",bundle);
        }else if (id==R.id.nav_cancelation){
            bundle.putString(AppConstants.EXTRA_LOADABLE_URL,AppConstants.EXTRA_CANCELLATION_POLICY);
            replaceFragmentWithBundle(new LoadingFragment(),"Cancallation Policy",bundle);
        }else if (id==R.id.nav_faq){
            bundle.putString(AppConstants.EXTRA_LOADABLE_URL,AppConstants.EXTRA_FAQ_URL);
            replaceFragmentWithBundle(new LoadingFragment(),"FAQ",bundle);
        }else if (id==R.id.nav_changepwd){
            Intent intent=new Intent(UpdateNavigationActivity.this,ForgotPasswordActivity.class);
            intent.putExtra(AppConstants.EXTRA_USER_PASSWORD_OPTION,AppConstants.EXTRA_CHANGE_PASSWORD);
            startActivity(intent);
        }
        else if (id==R.id.nav_logout){
            AlertDialog.Builder alBuilder = new AlertDialog.Builder(UpdateNavigationActivity.this,R.style.AlertDialogCustom);
            alBuilder.setTitle("Logout");
            alBuilder.setMessage("Are You Sure?");
            alBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    appSharedPrefference.clearSharedPreff();
                    Intent newIntent = new Intent(UpdateNavigationActivity.this,LoginActivity.class);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(newIntent);
                    finish();
                }
            });
            alBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alBuilder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void replaceFragment(Fragment fragment,String title){
        getSupportActionBar().setTitle(title);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,fragment).commit();
    }
    public void replaceFragmentWithBundle(Fragment fragment,String title,Bundle bundle){
        getSupportActionBar().setTitle(title);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,fragment).commit();
    }
}
