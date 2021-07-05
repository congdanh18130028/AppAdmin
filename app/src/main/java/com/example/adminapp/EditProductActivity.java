package com.example.adminapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminapp.api.ApiServices;
import com.example.adminapp.models.Product;
import com.example.adminapp.utils.FormatCurrence;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductActivity extends AppCompatActivity {

    private TextView txt_name, txt_category, txt_price, txt_quantity, txt_description;
    private ImageView img;
    private ImageButton btn_edit_name, btn_edit_category, btn_edit_quantity, btn_edit_price, btn_edit_img;

    private int ID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Intent i = getIntent();
        ID = i.getIntExtra("ID", 0);
        setView();
        setBtnEditName();
        setBtnEditCategory();
        setbtnEditQuantity();
        setBtnEditPrice();
        setBtnEditImg();
    }

    private void setBtnEditImg() {
        btn_edit_img = findViewById(R.id.btn_edit_img_product);
        btn_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProductActivity.this, EditImgProduct.class);
                intent.putExtra("ID", ID);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        setView();
        super.onResume();
    }


    private void setBtnEditPrice() {
        btn_edit_price = findViewById(R.id.btn_edit_price_product);
        btn_edit_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditPriceProduct.class);
                i.putExtra("ID", ID);
                startActivity(i);
            }
        });
    }

    private void setbtnEditQuantity() {
        btn_edit_quantity = findViewById(R.id.btn_edit_quantity_product);
        btn_edit_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditQuantityProduct.class);
                i.putExtra("ID", ID);
                startActivity(i);
            }
        });
    }

    private void setBtnEditCategory() {
        btn_edit_category = findViewById(R.id.btn_edit_category_product);
        btn_edit_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditCategoryProductActivity.class);
                i.putExtra("ID", ID);
                startActivity(i);
            }
        });
    }

    private void setBtnEditName() {
        btn_edit_name = findViewById(R.id.btn_edit_name_product);
        btn_edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProductNameActivity.class);
                intent.putExtra("ID", ID);
                startActivity(intent);
            }
        });
    }

    private void setView() {
        txt_name = findViewById(R.id.txt_name_product_edit);
        txt_category = findViewById(R.id.txt_category_product_edit);
        txt_price = findViewById(R.id.txt_price_product_edit);
        txt_quantity = findViewById(R.id.txt_quantity_product_edit);
        txt_description = findViewById(R.id.txt_description_product_edit);
        img = findViewById(R.id.img_product_edit);
        ApiServices.apiService.getProduct(ID).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product p = response.body();
                    txt_name.setText(p.getName());
                    txt_category.setText(p.getCategory());
                    String price = FormatCurrence.formatVnCurrence(getApplicationContext(), String.valueOf(p.getPrice()));
                    txt_price.setText(price);
                    txt_quantity.setText(String.valueOf(p.getQuantity()));
                    txt_description.setText(p.getDescripton());
                    Picasso.get().load(p.getImgPath().get(0).getPath()).into(img);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fail api!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}