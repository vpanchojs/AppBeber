package com.aitec.appbeber.init.registrerUser.di;

import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.init.registrerUser.RegisterUserInteractor;
import com.aitec.appbeber.init.registrerUser.RegisterUserInteractorImp;
import com.aitec.appbeber.init.registrerUser.RegisterUserPresenter;
import com.aitec.appbeber.init.registrerUser.RegisterUserPresenterImp;
import com.aitec.appbeber.init.registrerUser.RegisterUserRepository;
import com.aitec.appbeber.init.registrerUser.RegisterUserRepositoryImp;
import com.aitec.appbeber.init.registrerUser.ui.RegisterUserView;
import com.aitec.appbeber.lib.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victo on 16/03/2017.
 */

@Module
public class RegistreUserModule {
    RegisterUserView view;

    public RegistreUserModule(RegisterUserView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    RegisterUserView providesRegisterUserView() {
        return this.view;
    }

    @Provides
    @Singleton
    RegisterUserPresenter providesRegisterUserPresenter(EventBus eventBus, RegisterUserView view, RegisterUserInteractor interactor) {
        return new RegisterUserPresenterImp(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    RegisterUserInteractor providesRegisterUserInteractor(RegisterUserRepository repository) {
        return new RegisterUserInteractorImp(repository);
    }

    @Provides
    @Singleton
    RegisterUserRepository providesRegisterUserRepository(EventBus eventBus, FirebaseApi firebaseApi) {
        return new RegisterUserRepositoryImp(eventBus, firebaseApi);
    }


}
