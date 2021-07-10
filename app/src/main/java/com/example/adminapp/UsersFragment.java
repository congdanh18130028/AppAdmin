package com.example.adminapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adminapp.api.ApiServices;
import com.example.adminapp.models.User;
import com.example.adminapp.utils.DataLocalManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {
    RecyclerView rcvUser;
    UserAdapter userAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        rcvUser = view.findViewById(R.id.rcv_users);
        userAdapter = new UserAdapter(view.getContext());
        GridLayoutManager manager = new GridLayoutManager(view.getContext(), 1);
        rcvUser.setLayoutManager(manager);
        setListUser();
        rcvUser.setAdapter(userAdapter);

        return view;
    }


    private void setListUser() {
       ApiServices.apiService.getUsers(DataLocalManager.getToken()).enqueue(new Callback<List<User>>() {
           @Override
           public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               if(response.isSuccessful()){
                   List<User> list = response.body();
                   userAdapter.setData(list);
               }

           }

           @Override
           public void onFailure(Call<List<User>> call, Throwable t) {
               Toast.makeText(getContext(), "fail api!", Toast.LENGTH_SHORT).show();
           }
       });
    }
}