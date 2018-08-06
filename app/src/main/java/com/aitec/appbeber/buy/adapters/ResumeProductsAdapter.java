package com.aitec.appbeber.buy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aitec.appbeber.R;
import com.aitec.appbeber.models.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by victor on 15/9/17.
 */

public class ResumeProductsAdapter extends RecyclerView.Adapter<ResumeProductsAdapter.ViewHolder> {

    private ArrayList<Product> data;


    public ResumeProductsAdapter(ArrayList<Product> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_resume, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product p = data.get(position);
        holder.tvName.setText(p.getName() + " (" + p.getLot() + ")");
        holder.tvPrice.setText("$ " + (p.getPrice() * p.getLot()) + "");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
