package com.jcode.tebocydelevery.buy;

import com.jcode.tebocydelevery.models.Order;

/**
 * Created by victor on 9/9/17.
 */

public interface BuyInteractor {
    void changeLotProductOrder(String id, int lot);

    void deleteProductOrder(String id);

    void generateOrder(Order order);
}
