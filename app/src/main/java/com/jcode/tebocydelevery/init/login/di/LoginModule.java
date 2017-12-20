package com.jcode.tebocydelevery.init.login.di;

import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.init.login.LoginInteractor;
import com.jcode.tebocydelevery.init.login.LoginInteractorImp;
import com.jcode.tebocydelevery.init.login.LoginPresenter;
import com.jcode.tebocydelevery.init.login.LoginPresenterImp;
import com.jcode.tebocydelevery.init.login.LoginRepository;
import com.jcode.tebocydelevery.init.login.LoginRepositoryImp;
import com.jcode.tebocydelevery.init.login.ui.LoginView;
import com.jcode.tebocydelevery.lib.base.EventBus;

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
