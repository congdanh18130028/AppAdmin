package com.example.adminapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapp.models.Product;
import com.example.adminapp.utils.FormatCurrence;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private Context mContext;
    private List<Product> mListProduct;

    public ProductAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Product> list) {
        this.mListProduct = list;
        notifyDataSetChanged();
    }
    public void removeData(){
        this.mListProduct.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ProductDetailsActivity.class);
//                TextView textView = view.findViewById(R.id.txt_id_list);
//                String id = textView.getText().toString();
//                intent.putExtra("id",id);
//                mContext.startActivity(intent);
//            }
//        });
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if (product == null) {
            return;
        }
        holder.id.setText(String.valueOf(product.getId()));
        Picasso.get().load(product.getImgPath().get(0).getPath()).into(holder.img);
        holder.title.setText(product.getName());

    }

    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView id, title;
        private ImageButton btn_edit, btn_delete;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.product_item_id);
            img = itemView.findViewById(R.id.product_item_img);
            title = itemView.findViewById(R.id.product_item_name);
            btn_edit = itemView.findViewById(R.id.btn_edit_product_item);
            btn_delete = itemView.findViewById(R.id.btn_delete_product_item);
        }
    }
}
