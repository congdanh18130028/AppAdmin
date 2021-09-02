package com.example.adminapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adminapp.api.ApiServices;
import com.example.adminapp.models.Category;
import com.example.adminapp.models.Product;
import com.example.adminapp.utils.DataLocalManager;
import com.example.adminapp.utils.RealPathUtil;

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

public class AddProductFragment extends Fragment {
    public static final String TAG = Manifest.class.getName();
    private static final int MY_REQUEST_CODE = 10 ;
    private ImageButton btn_add_img_product, btn_add_category;
    private Button commit;
    private ImageView img_product;
    private Uri mUri;
    private Spinner spinner;
    private EditText edt_name, edt_description, edt_quantity, edt_price;

    private ProgressBar progressBar;

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
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getApplicationContext().getContentResolver(), uri);
                            img_product.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        setView(view);
        progressBar = view.findViewById(R.id.progressBarAddProduct);
        setCategory();
        setOnClickBtnAddImgProduct();
        setOnclickBtnShowDialogAddCategory(view);
        setOnClickBtnCommitProduct();
        return view;

    }

    private void setOnclickBtnShowDialogAddCategory(View view) {
        btn_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDialog dialog = new CategoryDialog(AddProductFragment.this);
                dialog.show(getChildFragmentManager(), "Add category dialog");

            }
        });

    }

    private void setOnClickBtnCommitProduct() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUri!=null){
                    callApiUploadProduct();
                }else {
                    Toast.makeText(getContext(), "Hình ảnh không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkField(String name, String description, String quantity, String price, String category){
        if(name.equals("")){
            Toast.makeText(getContext(), "Chưa nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(quantity.equals("")){
            Toast.makeText(getContext(), "Chưa nhập số lượng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(price.equals("")){
            Toast.makeText(getContext(), "Chưa nhập giá", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(category.equals("")){
            Toast.makeText(getContext(), "Chưa nhập loại sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void callApiUploadProduct() {
        progressBar.setVisibility(View.VISIBLE);
        String name = edt_name.getText().toString().trim();
        String description = edt_description.getText().toString().trim();
        String quantity = edt_quantity.getText().toString().trim();
        String price = edt_price.getText().toString().trim();
        String category = spinner.getSelectedItem().toString();
        if(checkField(name, description, quantity, price, category)){


            RequestBody requestBodyName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
            RequestBody requestBodyCategory = RequestBody.create(MediaType.parse("multipart/form-data"), category);
            RequestBody requestBodyDescription = RequestBody.create(MediaType.parse("multipart/form-data"), description);
            RequestBody requestBodyQuantity = RequestBody.create(MediaType.parse("multipart/form-data"), quantity);
            RequestBody requestBodyPrice = RequestBody.create(MediaType.parse("multipart/form-data"), price);

            String strRealPath = RealPathUtil.getRealPath(getContext(), mUri);

            File file = new File(strRealPath);
            RequestBody requestBodyImg = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part multipartBodyImg = MultipartBody.Part.createFormData("file", file.getName(), requestBodyImg);

            ApiServices.apiService.addProducts(DataLocalManager.getToken(), requestBodyName, requestBodyCategory, multipartBodyImg, requestBodyDescription, requestBodyQuantity, requestBodyPrice).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if(response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Product p = response.body();
                        Toast.makeText(getContext(), "Đã thêm sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "fail api!", Toast.LENGTH_SHORT).show();
                }
            });


        }




    }

    public void setCategory() {

        ApiServices.apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    List<Category> categories = response.body();
                    List<String> list = new ArrayList<>();
                    for(Category c : categories){
                        list.add(c.getCategoryName());

                    }
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
                    spinner.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "fail api!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setOnClickBtnAddImgProduct() {
        btn_add_img_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
    }

    private void onClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};

            requestPermissions(permission, MY_REQUEST_CODE);
        }
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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    private void setView(View view) {
        commit = view.findViewById(R.id.btn_commit);
        btn_add_img_product = view.findViewById(R.id.btn_add_img_product);
        img_product = view.findViewById(R.id.img_product);
        spinner = view.findViewById(R.id.spinner_category_product);
        edt_description = view.findViewById(R.id.edt_product_description);
        edt_name = view.findViewById(R.id.edt_product_name);
        edt_price = view.findViewById(R.id.edt_product_price);
        edt_quantity = view.findViewById(R.id.edt_product_quantity);
        btn_add_category = view.findViewById(R.id.btn_add_category);
    }
}