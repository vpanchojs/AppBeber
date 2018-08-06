package com.aitec.appbeber.RackProducts.adapters;

import android.widget.TextView;

import com.aitec.appbeber.models.Product;

/**
 * Created by victor on 7/9/17.
 */

public interface onItemClickListener {
    void addProductCarBuy(Product product, int position, TextView tvProductLot);
}
