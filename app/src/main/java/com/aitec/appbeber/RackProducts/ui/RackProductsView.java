package com.aitec.appbeber.RackProducts.ui;

import com.aitec.appbeber.models.Order;
import com.aitec.appbeber.models.Product;

/**
 * Created by victor on 17/12/17.
 */

public interface RackProductsView {
    void showProgressBar(Boolean show);
    void addProdutList(Product product);

    void updateProdut(Product product);

    void showMessage(String menssage);
}
