package com.jcode.tebocydelevery.main.di;

import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.lib.base.EventBus;
import com.jcode.tebocydelevery.main.MainInteractor;
import com.jcode.tebocydelevery.main.MainInteractorImp;
import com.jcode.tebocydelevery.main.MainPresenter;
import com.jcode.tebocydelevery.main.MainPresenterImp;
import com.jcode.tebocydelevery.main.MainRepository;
import com.jcode.tebocydelevery.main.MainRepositoryImp;
import com.jcode.tebocydelevery.main.ui.MainView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victor on 5/9/17.
 */

@Module
public class MainModule {

    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    MainView providesWelcomeView() {
        return this.view;
    }

    @Provides
    @Singleton
    MainPresenter providesMainPresenter(EventBus eventBus, MainView view, MainInteractor interactor) {
        return new MainPresenterImp(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    MainInteractor providesMainInteractor(MainRepository repository) {
        return new MainInteractorImp(repository);
    }

    @Provides
    @Singleton
    MainRepository providesMainRepository(EventBus eventBus, FirebaseApi firebaseApi) {
        return new MainRepositoryImp(eventBus, firebaseApi);
    }
}
