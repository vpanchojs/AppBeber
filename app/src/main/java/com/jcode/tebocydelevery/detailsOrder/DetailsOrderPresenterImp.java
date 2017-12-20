package com.jcode.tebocydelevery.detailsOrder;

import com.jcode.tebocydelevery.detailsOrder.event.DetailsOrderEvent;
import com.jcode.tebocydelevery.detailsOrder.ui.DetailsOrderView;
import com.jcode.tebocydelevery.lib.base.EventBus;
import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.Product;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by victor on 29/9/17.
 */

public class DetailsOrderPresenterImp implements DetailsOrderPresenter {
    private EventBus eventBus;
    private DetailsOrderInteractor interactor;
    private DetailsOrderView view;

    public DetailsOrderPresenterImp(EventBus eventBus, DetailsOrderView view, DetailsOrderInteractor interactor) {
        this.eventBus = eventBus;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void onDestroy() {
        view = null;
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
    public void subscribeProductsListOrder(String idOrder) {
        // view.showProgressBar(true);
        interactor.subscribeProductsListOrder(idOrder);
    }

    @Override
    public void unsubscribeProductsListOrder() {
        interactor.unsubscribeProductsListOrder();
    }


    @Override
    public void cancelOrder(Order order, String reason) {
        view.showProgressBar(true);
        interactor.cancelOrder(order, reason);
    }

    @Subscribe
    @Override
    public void onEventMainThread(DetailsOrderEvent event) {

        switch (event.getEvent()) {
            case DetailsOrderEvent.onProductsReadSuccess:
                ArrayList<Product> products = new ArrayList<>();
                for (Object o : event.getObjectList()) {
                    products.add((Product) o);
                }
                view.setProducts(products);
                break;
            case DetailsOrderEvent.onProductsReadError:
                view.showMessage(event.getMenssage());
                break;
            case DetailsOrderEvent.onOrderCancelSuccess:
                view.showProgressBar(false);
                view.showMessage(event.getMenssage());
                view.closeActivity();
                break;
            case DetailsOrderEvent.onOrderCancelError:
                view.showProgressBar(false);
                view.showMessage(event.getMenssage());
                break;
        }


    }
}
