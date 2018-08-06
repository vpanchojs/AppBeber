package com.aitec.appbeber.buy;

import com.aitec.appbeber.buy.events.BuyEvents;
import com.aitec.appbeber.buy.ui.CarBuyView;
import com.aitec.appbeber.models.Order;
import com.aitec.appbeber.lib.base.EventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by victor on 15/9/17.
 */

public class BuyPresenterImp implements BuyPresenter {
    private EventBus eventBus;
    private BuyInteractor interactor;
    private CarBuyView view;


    public BuyPresenterImp(EventBus eventBus, CarBuyView view, BuyInteractor interactor) {
        this.eventBus = eventBus;
        this.interactor = interactor;
        this.view = view;
    }


    @Override
    public void changeLotProductOrder(String id, int lot) {
        interactor.changeLotProductOrder(id, lot);
    }

    @Override
    public void deleteProductOrder(String id) {
        interactor.deleteProductOrder(id);
    }

    @Override
    public void generateOrder(Order order) {
        view.showProgressBar(true);
        interactor.generateOrder(order);
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

    @Subscribe
    @Override
    public void onEventMainThread(BuyEvents event) {
        view.showProgressBar(false);
        switch (event.getEvent()) {
            case BuyEvents.onChangeLotSuccess:

                break;
            case BuyEvents.onChangeLotError:

                break;
            case BuyEvents.onDeleteProductOrderSuccess:

                break;
            case BuyEvents.onDeleteProductOrderError:

                break;
            case BuyEvents.onGenerateOrderSuccess:
                view.dialogMessage("Pedido Exitoso", "El administrador verificara la información, y procedera a enviar sus productos", true);
                //view.navigationToMainOrders();
                break;
            case BuyEvents.onGenerateOrderError:
                view.dialogMessage("Pedido Fallido", "Se produjo un error al procesar su pedido", false);
                break;

        }
    }


}
