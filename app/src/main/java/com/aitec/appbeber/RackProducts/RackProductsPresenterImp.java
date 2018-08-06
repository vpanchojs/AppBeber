package com.aitec.appbeber.RackProducts;

import com.aitec.appbeber.RackProducts.ui.RackProductsView;
import com.aitec.appbeber.lib.base.EventBus;
import com.aitec.appbeber.main.events.MainEvent;
import com.aitec.appbeber.models.Product;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by victor on 17/12/17.
 */

public class RackProductsPresenterImp implements RackProductsPresenter {

    private EventBus eventBus;
    private RackProductsView view;
    private RackProductsInteractor interactor;

    public RackProductsPresenterImp(EventBus eventBus, RackProductsView view, RackProductsInteractor interactor) {
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
    public void subscribeProductsList() {
        view.showProgressBar(true);
        interactor.subscribeProductsList();
    }

    @Override
    public void unsubscribeProductsList() {
        interactor.unsubscribeProductsList();
    }

    @Subscribe
    @Override
    public void onEventMainThread(MainEvent event) {
        view.showProgressBar(false);
        switch (event.getEvent()) {
            case MainEvent.onProductReadSuccess:
                if (event.getObject() != null) {
                    view.addProdutList((Product) event.getObject());
                }
                break;
            case MainEvent.onProductChangeSuccess:
                view.updateProdut((Product) event.getObject());
                break;
            case MainEvent.onProductReadError:
                view.showMessage(event.getMenssage());
                break;
        }
    }
}
