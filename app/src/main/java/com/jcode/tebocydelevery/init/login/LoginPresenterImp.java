package com.jcode.tebocydelevery.init.login;


import com.jcode.tebocydelevery.models.User;
import com.jcode.tebocydelevery.init.login.events.LoginEvent;
import com.jcode.tebocydelevery.init.login.ui.LoginView;
import com.jcode.tebocydelevery.lib.base.EventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by victo on 24/09/2016.
 */

public class LoginPresenterImp implements LoginPresenter {
    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImp(EventBus eventBus, LoginView loginView, LoginInteractor interactor) {
        this.eventBus = eventBus;
        this.loginView = loginView;
        this.interactor = interactor;
    }

    @Override
    public void singInCredential(String credential, int cod_servicie, User user) {
        interactor.singInCredential(credential, cod_servicie, user);
        loginView.showProgress(true);
    }

    @Override
    public void onDestroy() {
        loginView = null;
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
    public void subscribe() {
        interactor.subscribe();
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }


    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        loginView.showProgress(false);
        switch (event.getEvenType()) {
            case LoginEvent.onSigInSuccess:
                loginView.navigateToMainScreen();

                break;
            case LoginEvent.onSigUp:
                loginView.navigateToRegisterUser();
                break;
            case LoginEvent.onSiginError:
                loginView.showMessage(event.getErroMessage());
                break;
            case LoginEvent.onRecoverySuccess:
                loginView.navigateToMainScreen();
                break;
            case LoginEvent.onRecoveryError:

                break;
        }

    }


}
