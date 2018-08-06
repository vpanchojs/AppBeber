package com.aitec.appbeber.historyOrders.di;

import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.historyOrders.HistoryOrderInteractor;
import com.aitec.appbeber.historyOrders.HistoryOrderInteractorImp;
import com.aitec.appbeber.historyOrders.HistoryOrderPresenter;
import com.aitec.appbeber.historyOrders.HistoryOrderPresenterImp;
import com.aitec.appbeber.historyOrders.HistoryOrderRepository;
import com.aitec.appbeber.historyOrders.HistoryOrderRepositoryImp;
import com.aitec.appbeber.historyOrders.ui.HistoryOrderView;
import com.aitec.appbeber.lib.base.EventBus;

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
