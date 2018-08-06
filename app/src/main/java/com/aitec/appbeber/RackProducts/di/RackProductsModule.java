package com.aitec.appbeber.RackProducts.di;

import com.aitec.appbeber.RackProducts.RackProductsInteractor;
import com.aitec.appbeber.RackProducts.RackProductsInteractorImp;
import com.aitec.appbeber.RackProducts.RackProductsPresenter;
import com.aitec.appbeber.RackProducts.RackProductsPresenterImp;
import com.aitec.appbeber.RackProducts.RackProductsRepository;
import com.aitec.appbeber.RackProducts.RackProductsRepositoryImp;
import com.aitec.appbeber.RackProducts.ui.RackProductsView;
import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.lib.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victor on 17/12/17.
 */
@Module
public class RackProductsModule {
    private RackProductsView view;

    public RackProductsModule(RackProductsView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    RackProductsView providesWelcomeView() {
        return this.view;
    }

    @Provides
    @Singleton
    RackProductsPresenter providesMainPresenter(EventBus eventBus, RackProductsView view, RackProductsInteractor interactor) {
        return new RackProductsPresenterImp(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    RackProductsInteractor providesMainInteractor(RackProductsRepository repository) {
        return new RackProductsInteractorImp(repository);
    }

    @Provides
    @Singleton
    RackProductsRepository providesMainRepository(EventBus eventBus, FirebaseApi firebaseApi) {
        return new RackProductsRepositoryImp(eventBus, firebaseApi);
    }
}
