package com.aitec.appbeber.RackProducts.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aitec.appbeber.R;
import com.aitec.appbeber.models.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by victo on 5/7/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {


    @BindView(R.id.iv_dinner)
    ImageView ivDinner;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.iv_car_buy)
    ImageView ivCarBuy;
    @BindView(R.id.ib_add_product)
    Button ibAddProduct;
    @BindView(R.id.ib_unavaible_product)
    Button ibUnavaibleProduct;

    private List<Product> data;
    private onItemClickListener listener;
    private onItemLongClickListener listenerLong;
    private Context context;

    public ProductsAdapter(List<Product> data, onItemClickListener listener, onItemLongClickListener listenerLong, Context context) {
        this.data = data;
        this.listener = listener;
        this.listenerLong = listenerLong;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product p = data.get(position);
        holder.setOnItemClickListener(p, listener, position, holder.tvProductLot);
        holder.setOnItemLongClickListener(p, listenerLong, position);
        //holder.civLogoTerminal
        holder.tvContent.setText(p.getDescription());
        holder.tvName.setText(p.getName());
        holder.tvPrice.setText(p.getPrice() + "");
        holder.tvStock.setText(p.getStock() + "");
        Glide.with(context)
                .load(p.getUrl_photo())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.civPhotoProduct);
        //MainActivity.imageLoader.get(p.getUrl_photo(), ImageLoader.getImageListener(holder.civPhotoProduct, R.drawable.ic_person, R.drawable.ic_cancel));
        if (p.getLot() > 0) {
            holder.tvProductLot.setText(p.getLot() + "");
            holder.tvProductLot.setVisibility(View.VISIBLE);
        } else {
            holder.tvProductLot.setVisibility(View.GONE);
        }

        if (p.getStock() > 0) {
            holder.ibUnavaibleProduct.setVisibility(View.GONE);
            holder.ibAddProduct.setVisibility(View.VISIBLE);
            holder.ivCarBuy.setVisibility(View.VISIBLE);
        } else {
            holder.ibUnavaibleProduct.setVisibility(View.VISIBLE);
            holder.ibAddProduct.setVisibility(View.GONE);
            holder.ivCarBuy.setVisibility(View.GONE);
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

    public void setFilter(ArrayList<Product> filter) {
        data = new ArrayList<>();
        data.addAll(filter);
        notifyDataSetChanged();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_photo_product)
        ImageView civPhotoProduct;
        @BindView(R.id.tv_stock)
        TextView tvStock;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_product_lot)
        TextView tvProductLot;
        @BindView(R.id.iv_dinner)
        ImageView ivDinner;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_car_buy)
        ImageView ivCarBuy;
        @BindView(R.id.ib_add_product)
        Button ibAddProduct;
        @BindView(R.id.ib_unavaible_product)
        Button ibUnavaibleProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setOnItemClickListener(final Product product, final onItemClickListener listener, final int position, final TextView tvProductLot) {
            ibAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.addProductCarBuy(product, position, tvProductLot);
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
