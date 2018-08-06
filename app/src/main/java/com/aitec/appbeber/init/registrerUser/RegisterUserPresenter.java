package com.aitec.appbeber.init.registrerUser;


import com.aitec.appbeber.models.User;
import com.aitec.appbeber.init.registrerUser.events.RegisterUserEvents;

/**
 * Created by victo on 19/01/2017.
 */

public interface RegisterUserPresenter {

    void onDestroy();

    void onResume();

    void onPause();

    void doRegister(User user);

    void navigationToMain(RegisterUserEvents events);
}
