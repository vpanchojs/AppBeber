package com.aitec.appbeber.init.login.di;

import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.init.login.LoginInteractor;
import com.aitec.appbeber.init.login.LoginInteractorImp;
import com.aitec.appbeber.init.login.LoginPresenter;
import com.aitec.appbeber.init.login.LoginPresenterImp;
import com.aitec.appbeber.init.login.LoginRepository;
import com.aitec.appbeber.init.login.LoginRepositoryImp;
import com.aitec.appbeber.init.login.ui.LoginView;
import com.aitec.appbeber.lib.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victo on 16/03/2017.
 */

@Module
public class LoginModule {
    private LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginView providesWelcomeView() {
        return this.view;
    }

    @Provides
    @Singleton
    LoginPresenter providesLoginPresenter(EventBus eventBus, LoginView view, LoginInteractor interactor) {
        return new LoginPresenterImp(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository repository) {
        return new LoginInteractorImp(repository);
    }

    @Provides
    @Singleton
    LoginRepository providesLoginRepository(EventBus eventBus, FirebaseApi firebaseApi) {
        return new LoginRepositoryImp(eventBus, firebaseApi);
    }
}
