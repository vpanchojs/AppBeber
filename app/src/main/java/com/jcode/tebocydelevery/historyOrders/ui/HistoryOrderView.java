package com.jcode.tebocydelevery.historyOrders.ui;

import com.jcode.tebocydelevery.models.Order;

import java.util.ArrayList;

/**
 * Created by victor on 17/12/17.
 */

public interface HistoryOrderView {
    void showProgressBar(Boolean show);

    void showMessage(String menssage);

    void setOrders(ArrayList<Order> orders);
}
