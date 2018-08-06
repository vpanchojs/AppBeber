package com.aitec.appbeber.buy;

import com.aitec.appbeber.models.Order;

/**
 * Created by victor on 9/9/17.
 */

public interface BuyRepository {
    void changeLotProductOrder(String id, int lot);

    void deleteProductOrder(String id);

    void generateOrder(Order order);
}
