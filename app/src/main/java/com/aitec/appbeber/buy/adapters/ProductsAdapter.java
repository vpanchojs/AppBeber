package com.aitec.appbeber.buy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aitec.appbeber.R;
import com.aitec.appbeber.models.Product;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by victo on 5/7/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> data;
    private onClickListener listener;

    public ProductsAdapter(List<Product> data, onClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_car_buy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Product p = data.get(position);
        holder.tvName.setText(p.getName() + "(" + p.getDescription() + ")");
        holder.tvPrecio.setText("$ " + getPriceFormmater(p.getPrice() * p.getLot()));
        holder.etLot.setText(p.getLot() + "");
        holder.setOnItemClickListener(listener, position, p);

        /*
        holder.etLot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                listener.moraLotCantidad(holder.etLot.getText().toString(),position);
            }
        });
        */
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public double getPriceFormmater(double price) {
        DecimalFormat df = new DecimalFormat("#.00");
        return price = Double.parseDouble(df.format(price).replace(',', '.'));
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ib_remove_product)
        ImageButton ibRemoveProduct;
        @BindView(R.id.ib_more_lot)
        ImageButton ibMoreLot;
        @BindView(R.id.ib_less_lot)
        ImageButton ibLessLot;
        @BindView(R.id.et_lot)
        TextView etLot;
        @BindView(R.id.tv_precio)
        TextView tvPrecio;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setOnItemClickListener(final onClickListener listener, final int position, final Product p) {

            ibRemoveProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.removeProduct(position, p.getName() + "(" + p.getContent() + ")");
                }
            });

            ibLessLot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.lessLot(position);
                }
            });

            ibMoreLot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.moreLot(position);
                }
            });
        }

    }


}
