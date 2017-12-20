package com.jcode.tebocydelevery.myOrders.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.models.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by victor on 16/9/17.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {


    private ArrayList<Order> data;
    private onItemClickListener listener;
    private Context context;

    public OrdersAdapter(ArrayList<Order> data, onItemClickListener listener, Context context) {
        this.data = data;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = data.get(position);
        switch (order.getState_order()) {
            case Order.ORDER_PROCESSANDO:
                holder.tvStateOrder.setText("Procesando");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.ibMoreInfo.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.procesando)));
                }
                break;
            case Order.ORDER_CANCELATE:
                holder.tvStateOrder.setText("Cancelado");
                holder.btnCancel.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.ibMoreInfo.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cancelado)));
                }
                break;
            case Order.ORDER_ENTREGATE:
                holder.tvStateOrder.setText("Entregado");
                holder.btnCancel.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.ibMoreInfo.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.entregado)));
                }
                break;
            case Order.ORDER_PREPARADA:
                holder.tvStateOrder.setText("Preparado");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.ibMoreInfo.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.preparado)));
                }
                break;
            case Order.ORDER_ENVIADA:
                holder.tvStateOrder.setText("Enviado");
                holder.btnCancel.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.ibMoreInfo.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.enviando)));
                }
                break;
        }
        holder.etPrecio.setText("$ " + order.getPrice_total());
        holder.etSendAddress.setText(order.getAddress());
        SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("ES"));
        try {
            holder.etDateCreation.setText(formateador.format(order.getDate_emision()));
        } catch (Exception e) {
            Log.e("adapter", e.toString());
        }
        holder.setOnItemClickListener(order, listener, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_state_order)
        TextView tvStateOrder;
        @BindView(R.id.ib_more_info)
        ImageButton ibMoreInfo;
        @BindView(R.id.et_precio)
        TextInputEditText etPrecio;
        @BindView(R.id.et_date_creation)
        TextInputEditText etDateCreation;
        @BindView(R.id.et_send_address)
        TextInputEditText etSendAddress;
        @BindView(R.id.btn_cancel)
        Button btnCancel;
        @BindView(R.id.btn_datails)
        Button btnDatails;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final Order order, final onItemClickListener listener, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.detailsOrder(order);
                }
            });

            btnDatails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.detailsOrder(order);
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.cancelOrder(order);
                }
            });

            ibMoreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.moreInfoState("Descripcion del Estado", order.getDescription_state_order());
                }
            });
        }


    }

}
