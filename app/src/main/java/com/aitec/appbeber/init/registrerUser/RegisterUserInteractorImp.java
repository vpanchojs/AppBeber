package com.aitec.appbeber.init.registrerUser;


import com.aitec.appbeber.models.User;

/**
 * Created by victo on 19/01/2017.
 */

public class RegisterUserInteractorImp implements RegisterUserInteractor {
    RegisterUserRepository repository;

    public RegisterUserInteractorImp(RegisterUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void Register(User user) {
        repository.doRegister(user);
    }
}
