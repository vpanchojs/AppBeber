package com.jcode.tebocydelevery.RackProducts.adapters;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.models.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by victo on 5/7/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {


    private List<Product> data;
    private onItemClickListener listener;
    private onItemLongClickListener listenerLong;

    public ProductsAdapter(List<Product> data, onItemClickListener listener, onItemLongClickListener listenerLong) {
        this.data = data;
        this.listener = listener;
        this.listenerLong = listenerLong;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product p = data.get(position);
        holder.setOnItemClickListener(p, listener, position,holder.tvProductLot);
        holder.setOnItemLongClickListener(p, listenerLong, position);
        //holder.civLogoTerminal
        holder.etContent.setText(p.getDescription());
        holder.tvName.setText(p.getName());
        holder.btnPrice.setText(p.getPrice() + "");
        holder.etStock.setText(p.getStock() + "");
        if (p.getLot() > 0) {
            holder.tvProductLot.setText(p.getLot() + "");
            holder.tvProductLot.setVisibility(View.VISIBLE);
        } else {
            holder.tvProductLot.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public void addProduct(Product product) {
        data.add(product);
        notifyDataSetChanged();
    }

    public void removeProduct(Product product) {
        data.remove(product);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_photo_product)
        ImageView civPhotoProduct;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.et_content)
        TextInputEditText etContent;
        @BindView(R.id.btn_price)
        Button btnPrice;
        @BindView(R.id.et_stock)
        TextInputEditText etStock;
        @BindView(R.id.tv_product_lot)
        TextView tvProductLot;
        @BindView(R.id.ib_add_product)
        Button ibAddProduct;
        @BindView(R.id.rl_product)
        LinearLayout rlProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setOnItemClickListener(final Product product, final onItemClickListener listener, final int position, final TextView tvProductLot) {
            ibAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.addProductCarBuy(product, position,tvProductLot);
                }
            });
        }

        public void setOnItemLongClickListener(final Product product, final onItemLongClickListener listener, final int position) {
            ibAddProduct.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.addProductCarBuyLot(product, position);
                    return true;
                }
            });
        }


    }


}
