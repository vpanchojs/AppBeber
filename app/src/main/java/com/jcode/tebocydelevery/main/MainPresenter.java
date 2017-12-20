package com.jcode.tebocydelevery.main;

import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.User;
import com.jcode.tebocydelevery.main.events.MainEvent;

/**
 * Created by victor on 5/9/17.
 */

public interface MainPresenter {

    void onDestroy();

    void onStart();

    void onStop();

    void subscribeProductsList();

    void unsubscribeProductsList();

    void subscribeMyOrders();

    void unsubscribeMyOrders();

    void subscribeMyProfile();

    void unsubscribeMyProfile();

    void updateUser(User user);

    void updatePhotoUser(String photo);

    void cancelOrder(Order order, String reason);

    void singOut();

    void onEventMainThread(MainEvent event);
}
