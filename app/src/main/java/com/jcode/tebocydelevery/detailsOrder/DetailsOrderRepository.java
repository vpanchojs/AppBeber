package com.jcode.tebocydelevery.detailsOrder;

import com.jcode.tebocydelevery.models.Order;

/**
 * Created by victor on 29/9/17.
 */

public interface DetailsOrderRepository {
    void subscribeProductsListOrder(String idOrder);

    void unsubscribeProductsListOrder();

    void cancelOrder(Order order, String reason);
}
