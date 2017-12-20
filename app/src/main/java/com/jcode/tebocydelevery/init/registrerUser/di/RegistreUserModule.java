package com.jcode.tebocydelevery.init.registrerUser.di;

import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.init.registrerUser.RegisterUserInteractor;
import com.jcode.tebocydelevery.init.registrerUser.RegisterUserInteractorImp;
import com.jcode.tebocydelevery.init.registrerUser.RegisterUserPresenter;
import com.jcode.tebocydelevery.init.registrerUser.RegisterUserPresenterImp;
import com.jcode.tebocydelevery.init.registrerUser.RegisterUserRepository;
import com.jcode.tebocydelevery.init.registrerUser.RegisterUserRepositoryImp;
import com.jcode.tebocydelevery.init.registrerUser.ui.RegisterUserView;
import com.jcode.tebocydelevery.lib.base.EventBus;

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
