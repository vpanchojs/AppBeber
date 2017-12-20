package com.jcode.tebocydelevery.init.login;

import com.jcode.tebocydelevery.models.User;

/**
 * Created by victo on 24/09/2016.
 */

public class LoginInteractorImp implements LoginInteractor {
    private LoginRepository repository;

    public LoginInteractorImp(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void doSignIn(String email, String password) {
        //repository.signIn(email, password);
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void singInCredential(String credential, int cod_servicie, User user) {
        repository.singInCredential(credential, cod_servicie, user);
    }
}
