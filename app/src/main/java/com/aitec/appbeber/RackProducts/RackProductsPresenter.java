package com.aitec.appbeber.RackProducts;

import com.aitec.appbeber.main.events.MainEvent;

/**
 * Created by victor on 17/12/17.
 */

public interface RackProductsPresenter {
    void onStart();

    void onStop();

    void subscribeProductsList();

    void unsubscribeProductsList();

    void onEventMainThread(MainEvent event);
}
