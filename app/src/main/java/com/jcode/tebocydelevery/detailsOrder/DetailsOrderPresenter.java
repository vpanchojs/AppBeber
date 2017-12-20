package com.jcode.tebocydelevery.detailsOrder;

import com.jcode.tebocydelevery.detailsOrder.event.DetailsOrderEvent;
import com.jcode.tebocydelevery.models.Order;

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
