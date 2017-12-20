package com.jcode.tebocydelevery.buy;

import com.jcode.tebocydelevery.buy.events.BuyEvents;
import com.jcode.tebocydelevery.models.Order;

/**
 * Created by victor on 9/9/17.
 */

public interface BuyPresenter {
    //modificar la cantidad de producto
    void changeLotProductOrder(String id, int lot);

    //eliminar producto
    void deleteProductOrder(String id);

    //generar orden
    void generateOrder(Order order);

    void onDestroy();

    void onStart();

    void onStop();

    void onEventMainThread(BuyEvents event);
}
