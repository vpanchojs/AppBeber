package com.jcode.tebocydelevery.detailsOrder.adapter;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.main.ui.MainActivity;
import com.jcode.tebocydelevery.models.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by victor on 29/9/17.
 */

public class ProductsOrderAdapter extends RecyclerView.Adapter<ProductsOrderAdapter.ViewHolder> {
    private ArrayList<Product> data;


    public ProductsOrderAdapter(ArrayList<Product> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product p = data.get(position);
        holder.tvName.setText(p.getName());
        holder.etPrice.setText("$ " + (p.getPrice() * p.getLot()) + "");
        holder.etLot.setText(p.getLot() + "");
        MainActivity.imageLoader.get(p.getUrl_photo(), ImageLoader.getImageListener(holder.ivProduct, R.drawable.ic_person, R.drawable.ic_cancel));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product)
        ImageView ivProduct;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.et_lot)
        TextInputEditText etLot;
        @BindView(R.id.et_price)
        TextInputEditText etPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
