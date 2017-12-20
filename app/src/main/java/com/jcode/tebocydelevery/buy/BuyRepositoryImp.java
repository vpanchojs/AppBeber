package com.jcode.tebocydelevery.buy;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jcode.tebocydelevery.buy.events.BuyEvents;
import com.jcode.tebocydelevery.domain.FirebaseActionListenerCallback;
import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.lib.base.EventBus;

import java.util.ArrayList;

/**
 * Created by victor on 15/9/17.
 */

public class BuyRepositoryImp implements BuyRepository {
    private EventBus eventBus;
    private FirebaseApi firebaseApi;


    public BuyRepositoryImp(EventBus eventBus, FirebaseApi firebaseApi) {
        this.eventBus = eventBus;
        this.firebaseApi = firebaseApi;
    }

    @Override
    public void changeLotProductOrder(String id, int lot) {

    }

    @Override
    public void deleteProductOrder(String id) {

    }

    @Override
    public void generateOrder(Order order) {

        firebaseApi.generateOrder(order, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(BuyEvents.onGenerateOrderSuccess, null, null, null);
            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

            }
            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onError(String error) {

                postEvent(BuyEvents.onGenerateOrderError, error, null, null);

            }
        });
    }

    private void postEvent(int type, String message, Object object, ArrayList<Object> objectList) {
        BuyEvents event = new BuyEvents();
        event.setEvent(type);
        event.setMenssage(message);
        event.setObject(object);
        event.setObjectList(objectList);
        eventBus.post(event);
    }
}
