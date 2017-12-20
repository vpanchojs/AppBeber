package com.jcode.tebocydelevery.myOrders;

import com.jcode.tebocydelevery.main.events.MainEvent;

/**
 * Created by victor on 17/12/17.
 */

public interface MyOrdersPresenter {
    void onStart();

    void onStop();

    void subscribeMyOrders();

    void unsubscribeMyOrders();

    void onEventMainThread(MainEvent event);
}
