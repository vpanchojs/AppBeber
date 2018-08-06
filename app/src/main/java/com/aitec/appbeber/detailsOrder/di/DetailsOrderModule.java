package com.aitec.appbeber.detailsOrder.di;

import com.aitec.appbeber.detailsOrder.DetailsOrderInteractor;
import com.aitec.appbeber.detailsOrder.DetailsOrderInteractorImp;
import com.aitec.appbeber.detailsOrder.DetailsOrderPresenter;
import com.aitec.appbeber.detailsOrder.DetailsOrderPresenterImp;
import com.aitec.appbeber.detailsOrder.DetailsOrderRepository;
import com.aitec.appbeber.detailsOrder.DetailsOrderRepositoryImp;
import com.aitec.appbeber.detailsOrder.ui.DetailsOrderView;
import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.lib.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victor on 29/9/17.
 */
@Module
public class DetailsOrderModule {
    private DetailsOrderView view;

    public DetailsOrderModule(DetailsOrderView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    DetailsOrderView providesOrderView() {
        return this.view;
    }

    @Provides
    @Singleton
    DetailsOrderPresenter providesDetailsOrderPresenter(EventBus eventBus, DetailsOrderView view, DetailsOrderInteractor interactor) {
        return new DetailsOrderPresenterImp(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    DetailsOrderInteractor providesDetailsOrderInteractor(DetailsOrderRepository repository) {
        return new DetailsOrderInteractorImp(repository);
    }

    @Provides
    @Singleton
    DetailsOrderRepository providesDetailsOrderRepository(EventBus eventBus, FirebaseApi firebaseApi) {
        return new DetailsOrderRepositoryImp(eventBus, firebaseApi);
    }

}
