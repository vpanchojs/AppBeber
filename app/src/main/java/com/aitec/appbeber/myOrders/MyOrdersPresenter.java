package com.aitec.appbeber.myOrders;

import com.aitec.appbeber.main.events.MainEvent;

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
