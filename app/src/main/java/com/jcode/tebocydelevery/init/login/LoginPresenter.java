package com.jcode.tebocydelevery.init.login;


import com.jcode.tebocydelevery.models.User;
import com.jcode.tebocydelevery.init.login.events.LoginEvent;

/**
 * Created by victo on 24/09/2016.
 */

public interface LoginPresenter {
    /*va ligado a la vista*/
    void onDestroy();

    void onStart();

    void onStop();

    void subscribe();

    void unsubscribe();

    void singInCredential(String credential, int cod_servicie, User user);

    void onEventMainThread(LoginEvent event);


}
