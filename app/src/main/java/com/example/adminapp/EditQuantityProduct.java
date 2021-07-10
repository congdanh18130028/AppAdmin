package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adminapp.api.ApiServices;
import com.example.adminapp.models.ProductEdit;
import com.example.adminapp.utils.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditQuantityProduct extends AppCompatActivity {
    private int ID;
    private EditText edt;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quantity_product);
        Intent intent = getIntent();
        ID = intent.getIntExtra("ID", 0);
        edt = findViewById(R.id.txt_value_quantity_edit);
        btn  = findViewById(R.id.btn_save_quantity_edit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQ = edt.getText().toString().trim();
                ProductEdit pEdit = new ProductEdit(newQ, "/quantity", "replace");
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
}