package com.aitec.appbeber.myOrders;

import com.aitec.appbeber.lib.base.EventBus;
import com.aitec.appbeber.main.events.MainEvent;
import com.aitec.appbeber.models.Order;
import com.aitec.appbeber.myOrders.ui.MyOrdersView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by victor on 17/12/17.
 */

public class MyOrdersPresenterImp implements MyOrdersPresenter {

    private EventBus eventBus;
    private MyOrdersView view;
    private MyOrdersInteractor interactor;

    public MyOrdersPresenterImp(EventBus eventBus, MyOrdersView view, MyOrdersInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onStart() {
        eventBus.registrer(this);
    }

    @Override
    public void onStop() {
        eventBus.unregistrer(this);
    }

    @Override
    public void subscribeMyOrders() {
        view.showProgressBar(true);
        interactor.subscribeMyOrders();
    }

    @Override
    public void unsubscribeMyOrders() {
        interactor.unsubscribeMyOrders();
    }

    @Subscribe
    @Override
    public void onEventMainThread(MainEvent event) {
        view.showProgressBar(false);
        switch (event.getEvent()) {
            case MainEvent.onOrderReadSuccess:
                if (event.getObject() != null) {
                    view.addOrderList((Order) event.getObject());
                }
                break;
            case MainEvent.onOrderChangeSuccess:
                view.updateOrder((Order) event.getObject());
                break;
            case MainEvent.onOrderReadError:
                view.showMessage(event.getMenssage());
                break;

        }
    }
}
