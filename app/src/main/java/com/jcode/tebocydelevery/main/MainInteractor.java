package com.jcode.tebocydelevery.main;

import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.User;

/**
 * Created by victor on 5/9/17.
 */

public interface MainInteractor {

    void singOut();

    void subscribeProductsList();

    void unsubscribeProductsList();

    void subscribeMyOrders();

    void unsubscribeMyOrders();

    void subscribeMyProfile();

    void updatePhotoUser(String photo);

    void unsubscribeMyProfile();

    void updateUser(User user);

    void cancelOrder(Order order, String reason);
}
