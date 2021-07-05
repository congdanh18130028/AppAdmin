package com.example.adminapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UsersFragment extends Fragment {
    RecyclerView rcvUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        rcvUser = view.findViewById(R.id.rcv_users);
        userAdapter = new UserAdapter(view.getContext());
        GridLayoutManager manager = new GridLayoutManager(view.getContext(), 1);
        rcvProduct.setLayoutManager(manager);
        setListProduct();
        rcvProduct.setAdapter(productAdapter);

        return view;
    }
}