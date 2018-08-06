package com.aitec.appbeber.myOrders;

import android.util.Log;

import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.domain.FirebaseEventListenerCallback;
import com.aitec.appbeber.lib.base.EventBus;
import com.aitec.appbeber.main.events.MainEvent;
import com.aitec.appbeber.models.Order;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

/**
 * Created by victor on 17/12/17.
 */

public class MyOrdersRepositoryImp implements MyOrdersRepository {

    private static final String TAG = "MyOrdersRepository";
    private EventBus eventBus;
    private FirebaseApi firebaseApi;

    public MyOrdersRepositoryImp(EventBus eventBus, FirebaseApi firebaseApi) {
        this.eventBus = eventBus;
        this.firebaseApi = firebaseApi;
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

    private void postEvent(int type, String message, Object object, List<Object> objectList) {

        MainEvent event = new MainEvent();
        event.setEvent(type);
        event.setMenssage(message);
        event.setObject(object);
        event.setObjectList(objectList);
        eventBus.post(event);
    }
}
