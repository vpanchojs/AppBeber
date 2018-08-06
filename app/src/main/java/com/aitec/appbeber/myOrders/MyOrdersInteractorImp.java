package com.aitec.appbeber.myOrders;

/**
 * Created by victor on 17/12/17.
 */

public class MyOrdersInteractorImp implements MyOrdersInteractor {
    MyOrdersRepository repository;

    public MyOrdersInteractorImp(MyOrdersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribeMyOrders() {
        repository.subscribeMyOrders();
    }

    @Override
    public void unsubscribeMyOrders() {
        repository.unsubscribeMyOrders();
    }
}
