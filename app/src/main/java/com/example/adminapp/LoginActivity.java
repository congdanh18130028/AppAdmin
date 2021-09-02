package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adminapp.api.ApiServices;
import com.example.adminapp.models.Token;
import com.example.adminapp.models.User;
import com.example.adminapp.utils.DataLocalManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_name, edt_pass;
    private Button btn_login, btn_create;
    private Token token;
    private ProgressBar progressBar;
    private static final String TAG_TOKEN = "Bearer ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_name = findViewById(R.id.edt_username);
        edt_pass = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_create = findViewById(R.id.btn_create);
        progressBar = findViewById(R.id.progressBarLogin);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiServices.apiService.createAdmin().enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "fail api", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = edt_name.getText().toString();
                String pass = edt_pass.getText().toString();
                ApiServices.apiService.authenticateUser(email, pass).enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if(response.isSuccessful()){
                            token = response.body();
                            String result = token.getToken();
                            DataLocalManager.setToken(TAG_TOKEN+result);
                            progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Tên tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "api fail!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}