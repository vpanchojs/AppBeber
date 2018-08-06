package com.aitec.appbeber.myOrders.di;

import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.lib.base.EventBus;
import com.aitec.appbeber.myOrders.MyOrdersInteractor;
import com.aitec.appbeber.myOrders.MyOrdersInteractorImp;
import com.aitec.appbeber.myOrders.MyOrdersPresenter;
import com.aitec.appbeber.myOrders.MyOrdersPresenterImp;
import com.aitec.appbeber.myOrders.MyOrdersRepository;
import com.aitec.appbeber.myOrders.MyOrdersRepositoryImp;
import com.aitec.appbeber.myOrders.ui.MyOrdersView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victor on 17/12/17.
 */
@Module
public class MyOrdersModule {
    private MyOrdersView view;

    public MyOrdersModule(MyOrdersView  view) {
        this.view = view;
    }

    @Provides
    @Singleton
    MyOrdersView  providesWelcomeView() {
        return this.view;
    }

    @Provides
    @Singleton
    MyOrdersPresenter providesMainPresenter(EventBus eventBus, MyOrdersView  view, MyOrdersInteractor interactor) {
        return new MyOrdersPresenterImp(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    MyOrdersInteractor providesMainInteractor(MyOrdersRepository repository) {
        return new MyOrdersInteractorImp(repository);
    }

    @Provides
    @Singleton
    MyOrdersRepository providesMainRepository(EventBus eventBus, FirebaseApi firebaseApi) {
        return new MyOrdersRepositoryImp(eventBus, firebaseApi);
    }
}
