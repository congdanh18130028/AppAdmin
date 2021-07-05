package com.example.adminapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.adminapp.api.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDialogEdit extends DialogFragment {
    private EditText edt;
    private Button btn_ok, btn_cancel;

    private EditCategoryProductActivity editCategoryProductActivity;

    public CategoryDialogEdit(EditCategoryProductActivity editCategoryProductActivity) {
        this.editCategoryProductActivity = editCategoryProductActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_category, container, false);
        setView(view);
        setCancel();
        setOk();
        return view;
    }

    private void setOk() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = edt.getText().toString().trim();
                if(!category.equals("")){
                    ApiServices.apiService.addCategory(category).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                editCategoryProductActivity.setCategory();
                                getDialog().dismiss();
                                Toast.makeText(getContext(), "success!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "fail api!", Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "Tên loại mặt hàng trống!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setCancel() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private void setView(View view) {
        btn_ok = view.findViewById(R.id.btn_ok_category_dialog);
        btn_cancel = view.findViewById(R.id.btn_cancel_category_dialog);
        edt = view.findViewById(R.id.edt_category_dialog);
    }
}
