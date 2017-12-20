package com.jcode.tebocydelevery.main;

import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.User;

/**
 * Created by victor on 5/9/17.
 */

public class MainInteractorImp implements MainInteractor {
    private MainRepository repository;

    public MainInteractorImp(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void singOut() {
        repository.singOut();
    }

    @Override
    public void subscribeProductsList() {
        repository.subscribeProductsList();
    }

    @Override
    public void unsubscribeProductsList() {
        repository.unsubscribeProductsList();
    }

    @Override
    public void subscribeMyOrders() {
        repository.subscribeMyOrders();
    }

    @Override
    public void unsubscribeMyOrders() {
        repository.unsubscribeMyOrders();
    }

    @Override
    public void subscribeMyProfile() {
        repository.subscribeMyProfile();
    }

    @Override
    public void updatePhotoUser(String photo) {
        repository.updatePhotoUser(photo);
    }

    @Override
    public void unsubscribeMyProfile() {
        repository.unsubscribeMyProfile();
    }

    @Override
    public void updateUser(User user) {
        repository.updateUser(user);
    }

    @Override
    public void cancelOrder(Order order, String reason) {
        repository.cancelOrder(order, reason);
    }
}
