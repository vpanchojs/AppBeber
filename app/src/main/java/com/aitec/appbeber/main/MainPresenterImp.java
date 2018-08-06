package com.aitec.appbeber.main;

import com.aitec.appbeber.lib.base.EventBus;
import com.aitec.appbeber.main.events.MainEvent;
import com.aitec.appbeber.main.ui.MainView;
import com.aitec.appbeber.models.User;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by victor on 5/9/17.
 */

public class MainPresenterImp implements MainPresenter {

    private EventBus eventBus;
    private MainView view;
    private MainInteractor interactor;


    public MainPresenterImp(EventBus eventBus, MainView view, MainInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onStart() {
        eventBus.registrer(this);
    }

    @Override
    public void onStop() {
        eventBus.unregistrer(this);
    }


    @Override
    public void registreToken(String token) {
        interactor.registreToken(token);
    }

    @Override
    public void subscribeMyProfile() {
        interactor.subscribeMyProfile();
    }

    @Subscribe
    @Override
    public void onEventMainThread(MainEvent event) {
        switch (event.getEvent()) {
            case MainEvent.onGetUserSuccess:
                view.setUserData((User) event.getObject());
                break;
            case MainEvent.onGetUserError:
                view.showMessage(event.getMenssage());
                break;
            case MainEvent.onUpdateTokenSuccess:
                view.changeStatusToke(true);
                break;
            case MainEvent.onUpdateTokenError:
                break;
        }
    }


}
