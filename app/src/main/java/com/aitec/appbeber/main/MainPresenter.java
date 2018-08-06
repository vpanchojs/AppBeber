package com.aitec.appbeber.main;

import com.aitec.appbeber.main.events.MainEvent;

/**
 * Created by victor on 5/9/17.
 */

public interface MainPresenter {

    void onDestroy();

    void onStart();

    void onStop();

    void subscribeMyProfile();

    void registreToken(String token);

    void onEventMainThread(MainEvent event);
}
