package com.aitec.appbeber.detailsOrder;

import com.aitec.appbeber.detailsOrder.event.DetailsOrderEvent;
import com.aitec.appbeber.models.Order;

/**
 * Created by victor on 29/9/17.
 */

public interface DetailsOrderPresenter {

    void onDestroy();

    void onStart();

    void onStop();

    void subscribeProductsListOrder(String idOrder);

    void unsubscribeProductsListOrder();

    void onEventMainThread(DetailsOrderEvent event);

    void cancelOrder(Order order, String reason);
}
