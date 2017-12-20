package com.jcode.tebocydelevery.RackProducts.adapters;

import android.widget.TextView;

import com.jcode.tebocydelevery.models.Product;

/**
 * Created by victor on 7/9/17.
 */

public interface onItemClickListener {
    void addProductCarBuy(Product product, int position, TextView tvProductLot);
}
