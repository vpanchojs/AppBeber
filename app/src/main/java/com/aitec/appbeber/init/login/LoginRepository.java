package com.aitec.appbeber.init.login;

import com.aitec.appbeber.models.User;

/**
 * Created by victo on 24/09/2016.
 */

public interface LoginRepository {
    void subscribe();

    void unsubscribe();

    void singInCredential(String credential, int cod_servicie, User user);
}
