package com.aitec.appbeber.main.di;

import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.lib.base.EventBus;
import com.aitec.appbeber.main.MainInteractor;
import com.aitec.appbeber.main.MainInteractorImp;
import com.aitec.appbeber.main.MainPresenter;
import com.aitec.appbeber.main.MainPresenterImp;
import com.aitec.appbeber.main.MainRepository;
import com.aitec.appbeber.main.MainRepositoryImp;
import com.aitec.appbeber.main.ui.MainView;

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
