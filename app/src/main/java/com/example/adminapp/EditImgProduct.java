package com.example.adminapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adminapp.api.ApiServices;
import com.example.adminapp.models.FilePath;
import com.example.adminapp.models.ProductEdit;
import com.example.adminapp.utils.DataLocalManager;
import com.example.adminapp.utils.RealPathUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditImgProduct extends AppCompatActivity {
    private int ID;
    private ImageView img;
    private ImageButton btn;
    private Button commit;

    private ProgressDialog mProgressDialog;
    public static final String TAG = Manifest.class.getName();
    private Uri mUri;
    private static final int MY_REQUEST_CODE = 10 ;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                            img.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_img_product);

        Intent i = getIntent();
        ID =  i.getIntExtra("ID", 0);

        mProgressDialog = new ProgressDialog(getApplicationContext());
        mProgressDialog.setMessage("Please wait...");

        img = findViewById(R.id.img_product_edit_select);
        btn = findViewById(R.id.btn_edit_img_product_select);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        commit = findViewById(R.id.btn_save_img_edit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUri!=null){
                    callApiUploadImg();
                }
            }
        });

    }
    

    private void callApiUploadImg() {

        String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);

        File file = new File(strRealPath);
        RequestBody requestBodyImg = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBodyImg = MultipartBody.Part.createFormData("file", file.getName(), requestBodyImg);
        ApiServices.apiService.addImg(DataLocalManager.getToken(), multipartBodyImg).enqueue(new Callback<FilePath>() {
            @Override
            public void onResponse(Call<FilePath> call, Response<FilePath> response) {
                if(response.isSuccessful()){
                    FilePath filePath = response.body();
                    ApiServices.apiService.updateImgProduct(DataLocalManager.getToken(), ID, filePath.getPath()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "success!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "fail api", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FilePath> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "fail api", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};

            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }

    }
}