package com.aitec.appbeber.RackProducts;

import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.domain.FirebaseEventListenerCallback;
import com.aitec.appbeber.lib.base.EventBus;
import com.aitec.appbeber.main.events.MainEvent;
import com.aitec.appbeber.models.Product;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

/**
 * Created by victor on 17/12/17.
 */

public class RackProductsRepositoryImp implements RackProductsRepository {

    private static final String TAG = "ProfileRepository";
    private EventBus eventBus;
    private FirebaseApi firebaseApi;

    public RackProductsRepositoryImp(EventBus eventBus, FirebaseApi firebaseApi) {
        this.eventBus = eventBus;
        this.firebaseApi = firebaseApi;
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
                postEvent(MainEvent.onProductReadError, error, null, null);
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

    private void postEvent(int type, String message, Object object, List<Object> objectList) {

        MainEvent event = new MainEvent();
        event.setEvent(type);
        event.setMenssage(message);
        event.setObject(object);
        event.setObjectList(objectList);
        eventBus.post(event);
    }
}
