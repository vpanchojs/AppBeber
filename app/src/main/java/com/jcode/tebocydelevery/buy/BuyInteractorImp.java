package com.jcode.tebocydelevery.buy;

import com.jcode.tebocydelevery.models.Order;

/**
 * Created by victor on 15/9/17.
 */

public class BuyInteractorImp implements BuyInteractor {
    private BuyRepository repository;


    public BuyInteractorImp(BuyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void changeLotProductOrder(String id, int lot) {
        repository.changeLotProductOrder(id, lot);
    }

    @Override
    public void deleteProductOrder(String id) {
        repository.deleteProductOrder(id);
    }

    @Override
    public void generateOrder(Order order) {
        repository.generateOrder(order);
    }
}
