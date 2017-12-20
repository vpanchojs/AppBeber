package com.jcode.tebocydelevery.buy.di;

import com.jcode.tebocydelevery.buy.BuyInteractor;
import com.jcode.tebocydelevery.buy.BuyInteractorImp;
import com.jcode.tebocydelevery.buy.BuyPresenter;
import com.jcode.tebocydelevery.buy.BuyPresenterImp;
import com.jcode.tebocydelevery.buy.BuyRepository;
import com.jcode.tebocydelevery.buy.BuyRepositoryImp;
import com.jcode.tebocydelevery.buy.ui.CarBuyView;
import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.lib.base.EventBus;

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
