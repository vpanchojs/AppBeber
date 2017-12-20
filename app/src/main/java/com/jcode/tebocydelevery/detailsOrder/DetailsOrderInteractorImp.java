package com.jcode.tebocydelevery.detailsOrder;

import com.jcode.tebocydelevery.models.Order;

/**
 * Created by victor on 29/9/17.
 */

public class DetailsOrderInteractorImp implements DetailsOrderInteractor {
    private DetailsOrderRepository repository;

    public DetailsOrderInteractorImp(DetailsOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribeProductsListOrder(String idOrder) {
        repository.subscribeProductsListOrder(idOrder);
    }

    @Override
    public void unsubscribeProductsListOrder() {
        repository.unsubscribeProductsListOrder();
    }

    @Override
    public void cancelOrder(Order order, String reason) {
        repository.cancelOrder(order, reason);
    }
}
