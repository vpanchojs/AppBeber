package com.aitec.appbeber.buy.di;

import com.aitec.appbeber.buy.BuyInteractor;
import com.aitec.appbeber.buy.BuyInteractorImp;
import com.aitec.appbeber.buy.BuyPresenter;
import com.aitec.appbeber.buy.BuyPresenterImp;
import com.aitec.appbeber.buy.BuyRepository;
import com.aitec.appbeber.buy.BuyRepositoryImp;
import com.aitec.appbeber.buy.ui.CarBuyView;
import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.lib.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BuyModule {
    private CarBuyView view;

    public BuyModule(CarBuyView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    CarBuyView providesCarBuyView() {
        return this.view;
    }

    @Provides
    @Singleton
    BuyPresenter providesCarBuyPresenter(EventBus eventBus, CarBuyView view, BuyInteractor interactor) {
        return new BuyPresenterImp(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    BuyInteractor providesCarBuyInteractor(BuyRepository repository) {
        return new BuyInteractorImp(repository);
    }

    @Provides
    @Singleton
    BuyRepository providesCarBuyRepository(EventBus eventBus, FirebaseApi firebaseApi) {
        return new BuyRepositoryImp(eventBus, firebaseApi);
    }
}
