package com.example.adminapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapp.api.ApiServices;
import com.example.adminapp.models.Bill;
import com.example.adminapp.models.BillDetails;
import com.example.adminapp.models.Product;
import com.example.adminapp.utils.DataLocalManager;
import com.example.adminapp.utils.FormatCurrence;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private Context mContext;
    private List<Bill> mListBillConfirm;
    private List<BillDetails> mListBillDetails;
    private int ID;

    public BillAdapter (Context mContext){
        this.mContext = mContext;
    }

    public void setData(List<Bill> list) {
        this.mListBillConfirm = list;
        notifyDataSetChanged();
    }

    public void setDataLítBillDetails(List<BillDetails> list) {
        this.mListBillDetails = list;
        notifyDataSetChanged();
    }

    public void removeData(){
        this.mListBillConfirm.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "mo bill", Toast.LENGTH_SHORT).show();
            }
        });
        return new BillAdapter.BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.BillViewHolder holder, int position) {
        Bill bill = mListBillConfirm.get(position);
        if(bill == null){
            return;
        }
        holder.id.setText(String.valueOf(bill.getId()));
        String currencyFormat = FormatCurrence.formatVnCurrence(mContext,String.valueOf(bill.getTotalPrice()));
        holder.total_price.setText(currencyFormat);

        getListBillDetails(bill.getId(), holder);



    }

    private void getListBillDetails(int id, BillViewHolder holder) {
        ApiServices.apiService.getListBillDetails(DataLocalManager.getToken(), id).enqueue(new Callback<List<BillDetails>>() {
            @Override
            public void onResponse(Call<List<BillDetails>> call, Response<List<BillDetails>> response) {
                if(response.isSuccessful()){
                    List<BillDetails> list = response.body();
                    mListBillDetails = list;
                    holder.quantity_item.setText(String.valueOf(mListBillDetails.size()+" sản phẩm"));
                    holder.quantity_first_product.setText(String.valueOf("x"+ mListBillDetails.get(0).getQuantity()));
                    if(list.size()==1){
                        holder.txt_view_more.setVisibility(View.GONE);
                    }
                    setFirstProduct(holder,  mListBillDetails.get(0).getProductId());



                }
            }

            @Override
            public void onFailure(Call<List<BillDetails>> call, Throwable t) {
                Toast.makeText(mContext, "fail api!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFirstProduct(BillViewHolder holder, int productId) {
        ApiServices.apiService.getProduct(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    String currencyFormat = FormatCurrence.formatVnCurrence(mContext,String.valueOf(product.getPrice()));
                    holder.price_product.setText(currencyFormat);
                    Picasso.get().load(product.getImgPath().get(0).getPath()).into(holder.img_first_product);
                    holder.title_first_product.setText(product.getName());

                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(mContext, "fail api!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListBillDetails(int billId) {

    }


    @Override
    public int getItemCount() {
        if(mListBillConfirm!= null){
            return mListBillConfirm.size();
        }
        return 0;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_first_product;
        private TextView id, title_first_product, quantity_first_product, price_product, quantity_item, total_price, txt_view_more;
        private Button btn_confirm;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txt_bill_id);
            img_first_product = itemView.findViewById(R.id.img_bill_first_product);
            title_first_product = itemView.findViewById(R.id.txt_title_bill_first_product);
            quantity_first_product = itemView.findViewById(R.id.txt_bill_quantity_product);
            price_product = itemView.findViewById(R.id.txt_bill_price_product);
            quantity_item = itemView.findViewById(R.id.txt_bill_quantity_product_in_bill);
            total_price = itemView.findViewById(R.id.txt_bill_total_price);
            txt_view_more = itemView.findViewById(R.id.txt_view_more);


            btn_confirm = itemView.findViewById(R.id.btn_confirm_bill_item);
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   ID = Integer.parseInt(id.getText().toString());
                   ApiServices.apiService.confirmBill(DataLocalManager.getToken(), ID, 1).enqueue(new Callback<Void>() {
                       @Override
                       public void onResponse(Call<Void> call, Response<Void> response) {
                           if(response.isSuccessful()){
                               mListBillConfirm.remove(getBindingAdapterPosition());
                               notifyItemRemoved(getBindingAdapterPosition());
                           }
                       }

                       @Override
                       public void onFailure(Call<Void> call, Throwable t) {
                           Toast.makeText(mContext, "fail api!", Toast.LENGTH_SHORT).show();
                       }
                   });
                }
            });


        }
    }
}
