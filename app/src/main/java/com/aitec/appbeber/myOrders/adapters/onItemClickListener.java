package com.aitec.appbeber.myOrders.adapters;

import com.aitec.appbeber.models.Order;

/**
 * Created by victor on 16/9/17.
 */

public interface onItemClickListener {
    void detailsOrder(Order order);

    void cancelOrder(Order order);

    void moreInfoState(String state, String stateDescription);

}
