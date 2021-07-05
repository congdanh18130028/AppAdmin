package com.example.adminapp;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter {
    public UserAdapter(Context context) {
    }
    public class UserViewHonder extends RecyclerView.ViewHolder{
        private TextView email;
        
        public UserViewHonder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
