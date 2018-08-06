package com.aitec.appbeber.init.login;


import com.aitec.appbeber.models.User;
import com.aitec.appbeber.init.login.events.LoginEvent;

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
