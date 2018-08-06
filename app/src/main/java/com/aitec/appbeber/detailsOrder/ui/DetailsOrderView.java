package com.aitec.appbeber.detailsOrder.ui;

import com.aitec.appbeber.models.Product;

import java.util.ArrayList;

/**
 * Created by victor on 29/9/17.
 */

public interface DetailsOrderView {
    void showProgressBar(Boolean show);

    void showMessage(String message);

    void setProducts(ArrayList<Product> products);

    void closeActivity();

}
