package com.jcode.tebocydelevery.detailsOrder.di;

import com.jcode.tebocydelevery.detailsOrder.DetailsOrderInteractor;
import com.jcode.tebocydelevery.detailsOrder.DetailsOrderInteractorImp;
import com.jcode.tebocydelevery.detailsOrder.DetailsOrderPresenter;
import com.jcode.tebocydelevery.detailsOrder.DetailsOrderPresenterImp;
import com.jcode.tebocydelevery.detailsOrder.DetailsOrderRepository;
import com.jcode.tebocydelevery.detailsOrder.DetailsOrderRepositoryImp;
import com.jcode.tebocydelevery.detailsOrder.ui.DetailsOrderView;
import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.lib.base.EventBus;

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
