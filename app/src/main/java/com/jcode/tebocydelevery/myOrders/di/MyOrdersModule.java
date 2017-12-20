package com.jcode.tebocydelevery.myOrders.di;

import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.lib.base.EventBus;
import com.jcode.tebocydelevery.myOrders.MyOrdersInteractor;
import com.jcode.tebocydelevery.myOrders.MyOrdersInteractorImp;
import com.jcode.tebocydelevery.myOrders.MyOrdersPresenter;
import com.jcode.tebocydelevery.myOrders.MyOrdersPresenterImp;
import com.jcode.tebocydelevery.myOrders.MyOrdersRepository;
import com.jcode.tebocydelevery.myOrders.MyOrdersRepositoryImp;
import com.jcode.tebocydelevery.myOrders.ui.MyOrdersView;

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
