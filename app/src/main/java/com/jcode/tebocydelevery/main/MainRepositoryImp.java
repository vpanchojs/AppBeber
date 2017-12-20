package com.jcode.tebocydelevery.main;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jcode.tebocydelevery.domain.FirebaseActionListenerCallback;
import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.domain.FirebaseEventListenerCallback;
import com.jcode.tebocydelevery.lib.base.EventBus;
import com.jcode.tebocydelevery.main.events.MainEvent;
import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.Product;
import com.jcode.tebocydelevery.models.User;

import java.util.List;

/**
 * Created by victor on 5/9/17.
 */

public class MainRepositoryImp implements MainRepository {
    private static final String TAG = "MainRepository";
    private EventBus eventBus;
    private FirebaseApi firebaseApi;

    public MainRepositoryImp(EventBus eventBus, FirebaseApi firebaseApi) {
        this.eventBus = eventBus;
        this.firebaseApi = firebaseApi;
    }

    @Override
    public void singOut() {
        firebaseApi.logout();
        postEvent(MainEvent.onSignOutSucces, null, null, null);
    }

    @Override
    public void subscribeProductsList() {
        firebaseApi.suscribeRackProducts(new FirebaseEventListenerCallback() {

            @Override
            public void onDocumentAdded(DocumentSnapshot snapshot) {
                Product product = snapshot.toObject(Product.class);
                product.setId(snapshot.getId());
                postEvent(MainEvent.onProductReadSuccess, null, product, null);

            }

            @Override
            public void onDocumentRemoved(DocumentSnapshot snapshot) {
              //  Log.e("productos", snapshot.toString());
                Product product = snapshot.toObject(Product.class);
                product.setId(snapshot.getId());
                //postEvent(MainEvent.onOrderReadSuccess,null,product,null);
            }

            @Override
            public void onError(String error) {
                postEvent(MainEvent.onProductReadError, null, null, null);
            }

            @Override
            public void onDocumentModified(DocumentSnapshot snapshot) {
              //  Log.e("change_product", snapshot.toString());
                Product product = snapshot.toObject(Product.class);
                product.setId(snapshot.getId());
                postEvent(MainEvent.onProductChangeSuccess, null, product, null);
            }
        });
    }

    @Override
    public void unsubscribeProductsList() {
        firebaseApi.unsuscribeRackProducts();
    }

    @Override
    public void subscribeMyOrders() {
        Log.e(TAG, "consultar mis ordenes");
        firebaseApi.suscribeMyOrders(new FirebaseEventListenerCallback() {
            @Override
            public void onDocumentAdded(DocumentSnapshot snapshot) {
              //  Log.e(TAG, snapshot.toString());
                Order order = snapshot.toObject(Order.class);
                order.setId_order(snapshot.getId());
                postEvent(MainEvent.onOrderReadSuccess, null, order, null);
            }

            @Override
            public void onDocumentRemoved(DocumentSnapshot snapshot) {

            }

            @Override
            public void onError(String error) {
                postEvent(MainEvent.onOrderReadError, error, null, null);
            }

            @Override
            public void onDocumentModified(DocumentSnapshot snapshot) {
               // Log.e(TAG, snapshot.toString());
                Order order = snapshot.toObject(Order.class);
                order.setId_order(snapshot.getId());
                postEvent(MainEvent.onOrderChangeSuccess, null, order, null);
            }
        });
    }

    @Override
    public void unsubscribeMyOrders() {
        firebaseApi.unSuscribeMyOrders();
    }

    @Override
    public void subscribeMyProfile() {
        firebaseApi.getDataUserSuscribe(new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.toObject(User.class);
                    Log.e(TAG, user.getName());
                    user.setEmail(firebaseApi.getAuthEmail());
                    user.setId_user(snapshot.getId());
                    postEvent(MainEvent.onGetUserSuccess, null, user, null);
                } else {
                    postEvent(MainEvent.onGetUserError, "Usuario no encontrado", null, null);
                }
            }

            @Override
            public void onError(String error) {
                postEvent(MainEvent.onGetUserError, error, null, null);

            }
        });
    }

    @Override
    public void unsubscribeMyProfile() {
        firebaseApi.getDataUserUnsucribe();
    }

    @Override
    public void updateUser(User user) {
        firebaseApi.updateUser(user, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(MainEvent.onUpdateUserSuccess, "Actualizado correctamente", null, null);
            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

            }

            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onError(String error) {
                postEvent(MainEvent.onUpdateUserError, error, null, null);
            }
        });
    }

    @Override
    public void updatePhotoUser(String photo) {
        firebaseApi.updatePhoto(photo, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(MainEvent.onUpdatePhotoSuccess, "Foto actualizada correctamente", null, null);
            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

            }

            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onError(String error) {
                postEvent(MainEvent.onUpdatePhotoError, error, null, null);
            }
        });
    }

    @Override
    public void cancelOrder(Order order, String reason) {
        firebaseApi.cancelOrder(order, reason, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(MainEvent.onOrderCancelSuccess, null, null, null);
            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

            }

            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onError(String error) {
                postEvent(MainEvent.onOrderCancelError, error, null, null);
            }
        });
    }

    private void postEvent(int type, String message, Object object, List<Object> objectList) {

        MainEvent event = new MainEvent();
        event.setEvent(type);
        event.setMenssage(message);
        event.setObject(object);
        event.setObjectList(objectList);
        eventBus.post(event);
    }
}
