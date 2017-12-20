package com.jcode.tebocydelevery.detailsOrder;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jcode.tebocydelevery.detailsOrder.event.DetailsOrderEvent;
import com.jcode.tebocydelevery.domain.FirebaseActionListenerCallback;
import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.lib.base.EventBus;
import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.Product;

import java.util.ArrayList;

/**
 * Created by victor on 29/9/17.
 */

public class DetailsOrderRepositoryImp implements DetailsOrderRepository {
    private FirebaseApi firebaseApi;
    private EventBus eventBus;

    public DetailsOrderRepositoryImp(EventBus eventBus, FirebaseApi firebaseApi) {
        this.eventBus = eventBus;
        this.firebaseApi = firebaseApi;
    }

    @Override
    public void subscribeProductsListOrder(String idOrder) {
        firebaseApi.getProductOrder(idOrder, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
               /*
                ArrayList<Object> products = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    products.add(s.getValue(Product.class));
                }
                postEvent(DetailsOrderEvent.onProductsReadSuccess, null, null, products);
                */
            }

            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                ArrayList<Object> products = new ArrayList<>();
                for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                    Log.e("a", documentSnapshot.getData().toString());
                    Product p = documentSnapshot.toObject(Product.class);
                    p.setId(documentSnapshot.getId());
                    products.add(p);
                }
                postEvent(DetailsOrderEvent.onProductsReadSuccess, null, null, products);
            }


            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void unsubscribeProductsListOrder() {

    }

    @Override
    public void cancelOrder(Order order, String reason) {
        firebaseApi.cancelOrder(order, reason, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(DetailsOrderEvent.onOrderCancelSuccess, "Pedido Cancelado", null, null);
            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

            }

            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onError(String error) {
                postEvent(DetailsOrderEvent.onOrderCancelError, error, null, null);
            }
        });
    }

    private void postEvent(int type, String message, Object object, ArrayList<Object> objectList) {
        DetailsOrderEvent event = new DetailsOrderEvent();
        event.setEvent(type);
        event.setMenssage(message);
        event.setObject(object);
        event.setObjectList(objectList);
        eventBus.post(event);
    }
}
