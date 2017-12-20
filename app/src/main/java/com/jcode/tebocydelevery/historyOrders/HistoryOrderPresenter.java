package com.jcode.tebocydelevery.historyOrders;

import com.jcode.tebocydelevery.main.events.MainEvent;

/**
 * Created by victor on 17/12/17.
 */

public interface HistoryOrderPresenter {
    void onStart();

    void onStop();

    void subscribeMyOrders();

    void unsubscribeMyOrders();

    void onEventMainThread(MainEvent event);
}
