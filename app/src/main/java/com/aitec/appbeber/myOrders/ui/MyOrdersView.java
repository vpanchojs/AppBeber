package com.aitec.appbeber.myOrders.ui;

import com.aitec.appbeber.models.Order;

/**
 * Created by victor on 17/12/17.
 */

public interface MyOrdersView {
    void showProgressBar(Boolean show);
    void addOrderList(Order order);

    void updateOrder(Order order);

    void showMessage(String menssage);
}
