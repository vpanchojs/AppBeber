package com.jcode.tebocydelevery.historyOrders.di;

import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.historyOrders.HistoryOrderInteractor;
import com.jcode.tebocydelevery.historyOrders.HistoryOrderInteractorImp;
import com.jcode.tebocydelevery.historyOrders.HistoryOrderPresenter;
import com.jcode.tebocydelevery.historyOrders.HistoryOrderPresenterImp;
import com.jcode.tebocydelevery.historyOrders.HistoryOrderRepository;
import com.jcode.tebocydelevery.historyOrders.HistoryOrderRepositoryImp;
import com.jcode.tebocydelevery.historyOrders.ui.HistoryOrderView;
import com.jcode.tebocydelevery.lib.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victor on 17/12/17.
 */
@Module
public class HistoryOrderModule {
    private HistoryOrderView view;

    public HistoryOrderModule(HistoryOrderView  view) {
        this.view = view;
    }

    @Provides
    @Singleton
    HistoryOrderView  providesWelcomeView() {
        return this.view;
    }

    @Provides
    @Singleton
    HistoryOrderPresenter providesMainPresenter(EventBus eventBus, HistoryOrderView  view, HistoryOrderInteractor interactor) {
        return new HistoryOrderPresenterImp(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    HistoryOrderInteractor providesMainInteractor(HistoryOrderRepository repository) {
        return new HistoryOrderInteractorImp(repository);
    }

    @Provides
    @Singleton
    HistoryOrderRepository providesMainRepository(EventBus eventBus, FirebaseApi firebaseApi) {
        return new HistoryOrderRepositoryImp(eventBus, firebaseApi);
    }
}
