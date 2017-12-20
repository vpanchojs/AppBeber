package com.jcode.tebocydelevery.main;

import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.User;

/**
 * Created by victor on 5/9/17.
 */

public interface MainRepository {
    void singOut();

    void subscribeProductsList();

    void unsubscribeProductsList();

    void subscribeMyOrders();

    void unsubscribeMyOrders();

    void subscribeMyProfile();

    void unsubscribeMyProfile();

    void updateUser(User user);

    void updatePhotoUser(String photo);

    void cancelOrder(Order order, String reason);

}
