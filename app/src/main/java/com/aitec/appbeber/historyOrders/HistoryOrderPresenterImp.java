package com.aitec.appbeber.historyOrders;

import com.aitec.appbeber.historyOrders.ui.HistoryOrderView;
import com.aitec.appbeber.lib.base.EventBus;
import com.aitec.appbeber.main.events.MainEvent;
import com.aitec.appbeber.models.Order;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by victor on 17/12/17.
 */

public class HistoryOrderPresenterImp implements HistoryOrderPresenter {

    private EventBus eventBus;
    private HistoryOrderView view;
    private HistoryOrderInteractor interactor;

    public HistoryOrderPresenterImp(EventBus eventBus, HistoryOrderView view, HistoryOrderInteractor interactor) {
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
                ArrayList<Order> products = new ArrayList<>();
                for (Object o : event.getObjectList()) {
                    products.add((Order) o);
                }
                view.setOrders(products);

                break;
            case MainEvent.onOrderReadError:
                view.showMessage(event.getMenssage());
                break;

        }
    }
}
