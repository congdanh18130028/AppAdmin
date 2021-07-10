package com.example.adminapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapp.api.ApiServices;
import com.example.adminapp.models.Product;
import com.example.adminapp.models.User;
import com.example.adminapp.utils.DataLocalManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mContext;
    private List<User> mListUser;
    private int ID;

    public UserAdapter(Context context) {
        this.mContext = context;

    }
    public void setData(List<User> list) {
        this.mListUser = list;
        notifyDataSetChanged();
    }
    public void removeData(){
        this.mListUser.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);

        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = mListUser.get(position);
        if(user == null){
            return;
        }
        holder.email.setText(user.getEmail());
        holder.id.setText(String.valueOf(user.getId()));

    }

    @Override
    public int getItemCount() {
        if(mListUser != null){
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView email, id;
        private ImageButton btn_delete;

        
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.user_item_id);
            email = itemView.findViewById(R.id.user_item_email);
            btn_delete = itemView.findViewById(R.id.btn_delete_user_item);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Xóa tài khoản này?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int idd) {
                                    ID = Integer.parseInt(id.getText().toString());
                                    ApiServices.apiService.deleteUser(DataLocalManager.getToken(), ID).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if(response.isSuccessful()){
                                                mListUser.remove(getBindingAdapterPosition());
                                                notifyItemRemoved(getBindingAdapterPosition());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Toast.makeText(mContext, "fail api!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            });

        }
    }
}
