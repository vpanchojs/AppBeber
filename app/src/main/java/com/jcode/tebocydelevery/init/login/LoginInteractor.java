package com.jcode.tebocydelevery.init.login;

import com.jcode.tebocydelevery.models.User;

/**
 * Created by victo on 24/09/2016.
 */

public interface LoginInteractor {
    /*Trabaja los casos de uso*/
    void doSignIn(String email, String password);

    void subscribe();

    void unsubscribe();

    void singInCredential(String credential, int cod_servicie, User user);
}
