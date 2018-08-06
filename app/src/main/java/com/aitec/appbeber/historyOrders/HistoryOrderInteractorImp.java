package com.aitec.appbeber.historyOrders;

/**
 * Created by victor on 17/12/17.
 */

public class HistoryOrderInteractorImp implements HistoryOrderInteractor {
    HistoryOrderRepository repository;

    public HistoryOrderInteractorImp(HistoryOrderRepository repository) {
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
