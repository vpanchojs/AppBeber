package com.aitec.appbeber.init.registrerUser;


import com.aitec.appbeber.models.User;
import com.aitec.appbeber.init.registrerUser.events.RegisterUserEvents;
import com.aitec.appbeber.init.registrerUser.ui.RegisterUserView;
import com.aitec.appbeber.lib.base.EventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by victo on 19/01/2017.
 */

public class RegisterUserPresenterImp implements RegisterUserPresenter {
    private EventBus eventBus;
    private RegisterUserView view;
    private RegisterUserInteractor interactor;

    public RegisterUserPresenterImp(EventBus eventBus, RegisterUserView view, RegisterUserInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onResume() {
        eventBus.registrer(this);
    }

    @Override
    public void onPause() {
        eventBus.unregistrer(this);
    }


    @Override
    public void doRegister(User user) {
        interactor.Register(user);
    }

    @Subscribe
    @Override
    public void navigationToMain(RegisterUserEvents events) {
        switch (events.getEvent()) {
            case RegisterUserEvents.onSucces:
                view.navigationToMain();
                break;
            case RegisterUserEvents.onError:
                view.showMessage(events.getMenssage().toString().trim());
                break;
        }
    }
}
