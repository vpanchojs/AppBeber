package com.aitec.appbeber.main;

import com.aitec.appbeber.models.Order;
import com.aitec.appbeber.models.User;

/**
 * Created by victor on 5/9/17.
 */

public class MainInteractorImp implements MainInteractor {
    private MainRepository repository;

    public MainInteractorImp(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribeMyProfile() {
        repository.subscribeMyProfile();
    }

    @Override
    public void registreToken(String token) {
        repository.registreToken(token);
    }
}
