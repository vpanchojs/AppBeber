package com.jcode.tebocydelevery.myOrders.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.StateOrder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by victor on 16/9/17.
 */

public class GuideStateAdapter extends RecyclerView.Adapter<GuideStateAdapter.ViewHolder> {

    private ArrayList<StateOrder> data;
    private Context context;

    public GuideStateAdapter(ArrayList<StateOrder> data,  Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide_state, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StateOrder stateOrder = data.get(position);
        holder.tvDescriptionState.setText(stateOrder.getDescripcion());
        switch (stateOrder.getState()) {
            case Order.ORDER_PROCESSANDO:
                holder.tvStateOrder.setText("Procesando");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.ibMoreInfo.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.procesando)));
                }
                break;
            case Order.ORDER_CANCELATE:
                holder.tvStateOrder.setText("Cancelado");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.ibMoreInfo.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cancelado)));
                }
                break;
            case Order.ORDER_ENTREGATE:
                holder.tvStateOrder.setText("Entregado");
                ;
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.ibMoreInfo.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.enviando)));
                }
                break;
        }
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
        @BindView(R.id.tv_description_state)
        TextView tvDescriptionState;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
