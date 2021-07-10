package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adminapp.api.ApiServices;
import com.example.adminapp.models.Category;
import com.example.adminapp.models.ProductEdit;
import com.example.adminapp.utils.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCategoryProductActivity extends AppCompatActivity {
    private Spinner spinner ;
    private ImageButton add;
    private Button save;
    private int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category_product);
        Intent i = getIntent();
        ID = i.getIntExtra("ID", 0);

        spinner = findViewById(R.id.spinner_category_edit_product);
        setCategory();
        add = findViewById(R.id.btn_add_category_edit);
        setOnclickBtnShowDialogAddCategory();
        save = findViewById(R.id.btn_save_category_edit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCategory = spinner.getSelectedItem().toString();
                ProductEdit pEdit = new ProductEdit(newCategory, "/category", "replace");
                List<ProductEdit> list = new ArrayList<>();
                list.add(pEdit);
                ApiServices.apiService.updateProduct(DataLocalManager.getToken(), ID, list).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "fail api", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void setOnclickBtnShowDialogAddCategory() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDialogEdit dialog = new CategoryDialogEdit(EditCategoryProductActivity.this);
                dialog.show(getSupportFragmentManager(), "f");

            }
        });

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
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list);
                    spinner.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fail api!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}