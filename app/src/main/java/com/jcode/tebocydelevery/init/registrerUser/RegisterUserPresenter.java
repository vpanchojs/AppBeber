package com.jcode.tebocydelevery.init.registrerUser;


import com.jcode.tebocydelevery.models.User;
import com.jcode.tebocydelevery.init.registrerUser.events.RegisterUserEvents;

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
