package com.aitec.appbeber.detailsOrder;

import com.aitec.appbeber.models.Order;

/**
 * Created by victor on 29/9/17.
 */

public interface DetailsOrderRepository {
    void subscribeProductsListOrder(String idOrder);

    void unsubscribeProductsListOrder();

    void cancelOrder(Order order, String reason);
}
