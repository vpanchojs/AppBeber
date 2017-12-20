package com.jcode.tebocydelevery.main;

import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.Product;
import com.jcode.tebocydelevery.models.User;
import com.jcode.tebocydelevery.lib.base.EventBus;
import com.jcode.tebocydelevery.main.events.MainEvent;
import com.jcode.tebocydelevery.main.ui.MainView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by victor on 5/9/17.
 */

public class MainPresenterImp implements MainPresenter {

    private EventBus eventBus;
    private MainView view;
    private MainInteractor interactor;


    public MainPresenterImp(EventBus eventBus, MainView view, MainInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
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
    public void singOut() {
        interactor.singOut();
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

    @Override
    public void subscribeMyOrders() {
        view.showProgressBar(true);
        interactor.subscribeMyOrders();
    }

    @Override
    public void unsubscribeMyOrders() {
        interactor.unsubscribeMyOrders();
    }

    @Override
    public void subscribeMyProfile() {
        view.showProgressBar(true);
        interactor.subscribeMyProfile();
    }

    @Override
    public void unsubscribeMyProfile() {
        interactor.unsubscribeMyProfile();
    }

    @Override
    public void updateUser(User user) {
        view.showProgressBar(true);
        interactor.updateUser(user);
    }

    @Override
    public void updatePhotoUser(String photo) {
        interactor.updatePhotoUser(photo);
    }

    @Override
    public void cancelOrder(Order order, String reason) {
        view.showProgressDialog(true, "Cancelando Pedido");
        interactor.cancelOrder(order, reason);
    }

    @Subscribe
    @Override
    public void onEventMainThread(MainEvent event) {
        view.showProgressBar(false);
        switch (event.getEvent()) {
            case MainEvent.onSignOutSucces:
                view.navigationLogin();
                break;
            case MainEvent.onSignOutError:
                view.showMessage(event.getMenssage());
                break;

            case MainEvent.onProductReadSuccess:
                view.addProdutList((Product) event.getObject());
                break;
            case MainEvent.onProductChangeSuccess:
                view.updateProdut((Product) event.getObject());
                break;
            case MainEvent.onProductReadError:
                view.showMessage(event.getMenssage());
                break;
            case MainEvent.onOrderReadError:
                view.showMessage(event.getMenssage());
                break;
            case MainEvent.onGetUserSuccess:
                view.setUserData((User) event.getObject());
                break;
            case MainEvent.onGetUserError:
                view.showMessage(event.getMenssage());
                break;
            case MainEvent.onUpdateUserSuccess:
                view.showMessage(event.getMenssage());
                break;
            case MainEvent.onUpdateUserError:
                view.showMessage(event.getMenssage());
                break;
            case MainEvent.onUpdatePhotoSuccess:
                view.showMessage(event.getMenssage());
                break;
            case MainEvent.onUpdatePhotoError:
                view.showMessage(event.getMenssage());
                break;
            case MainEvent.onOrderCancelError:
                view.showProgressDialog(false, "");
                view.showMessage(event.getMenssage());
                break;
            case MainEvent.onOrderCancelSuccess:
                view.showProgressDialog(false, "");
                view.dialogMessage("Pedido Cancelado", "Su pedido se ha cancelado correctamente");
                break;
        }
    }


}
