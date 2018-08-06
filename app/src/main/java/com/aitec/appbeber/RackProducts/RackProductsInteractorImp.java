package com.aitec.appbeber.RackProducts;

/**
 * Created by victor on 17/12/17.
 */

public class RackProductsInteractorImp implements RackProductsInteractor {
    RackProductsRepository repository;

    public RackProductsInteractorImp(RackProductsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribeProductsList() {
        repository.subscribeProductsList();
    }

    @Override
    public void unsubscribeProductsList() {
        repository.unsubscribeProductsList();
    }
}
