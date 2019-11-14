package com.barberapp.barberuser.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.utils.AppUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class ProfileImageActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_CODE = 201;
    private static final int REQUEST_GALLERY_CODE = 202;
    private static final int REQUEST_IMAGE_CAPTURE = 203;
    private static final int REQUEST_GALLERY_IMAGE = 204;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coorinateProfile)
    CoordinatorLayout coorinateProfile;
    BottomSheetDialog mBottomSheetDialog;
    private String mCurrentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile Photo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        if (item.getItemId()==R.id.actionEdit){
            showOptionDialog();
            return true;
        }
        return false;
    }

    private void showOptionDialog() {
        mBottomSheetDialog = new BottomSheetDialog(ProfileImageActivity.this);
        View sheetView = getLayoutInflater().inflate(R.layout.profile_bottom_sheet, null);
        mBottomSheetDialog.setContentView(sheetView);
        View bottomSheet = coorinateProfile.findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });
        mBottomSheetDialog.show();
        LinearLayout linCamera =  sheetView.findViewById(R.id.linCamera);
        LinearLayout linGallery =  sheetView.findViewById(R.id.linGallery);
        TextView txtCancel = sheetView.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetDialog.isShowing()){
                    mBottomSheetDialog.dismiss();
                }

            }
        });
        linCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (AppUtils.checkPermission(Manifest.permission.CAMERA,ProfileImageActivity.this)){
                        takePicFromCamera();
                    }else {
                        AppUtils.requestPermission(ProfileImageActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_CODE);
                    }
                }else {
                    takePicFromCamera();
                }
            }
        });
        linGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (AppUtils.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,ProfileImageActivity.this)){
                        takePicFromGallery();
                    }else {
                        AppUtils.requestPermission(ProfileImageActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_GALLERY_CODE);
                    }
                }else {
                    takePicFromGallery();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_pic,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CAMERA_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takePicFromCamera();
            }else {
                Toast.makeText(this, "App need CAMERA permission to work", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode==REQUEST_GALLERY_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takePicFromGallery();
            }else {
                Toast.makeText(this, "App need STORAGE permission to work", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void takePicFromCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        getString(R.string.authority),
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takePicFromGallery(){
        Intent picIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picIntent,REQUEST_GALLERY_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mBottomSheetDialog!=null && mBottomSheetDialog.isShowing()){
            mBottomSheetDialog.dismiss();
        }
        if (requestCode==REQUEST_IMAGE_CAPTURE){
            if (resultCode ==RESULT_OK){
                Glide.with(ProfileImageActivity.this).load(mCurrentPhotoPath).into(imgProfile);
            }
        }else if (requestCode == REQUEST_GALLERY_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                   /*Uri pictureUri = data.getData();
                    if (pictureUri!=null){
                        mCurrentPhotoPath = pictureUri.getPath();
                        Glide.with(ProfileImageActivity.this).load(pictureUri.getPath()).into(imgProfile);
                    }*/
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    if (!TextUtils.isEmpty(picturePath)){
                        mCurrentPhotoPath = picturePath;
                        Glide.with(ProfileImageActivity.this).load(mCurrentPhotoPath).into(imgProfile);
                    }

                }
            }
        }else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
