package com.aitec.appbeber.historyOrders;

import com.aitec.appbeber.main.events.MainEvent;

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
