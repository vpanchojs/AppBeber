package com.aitec.appbeber.main;

import android.util.Log;

import com.aitec.appbeber.domain.FirebaseActionListenerCallback;
import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.lib.base.EventBus;
import com.aitec.appbeber.main.events.MainEvent;
import com.aitec.appbeber.models.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    public void registreToken(String token) {
        firebaseApi.registreToken(token, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(MainEvent.onUpdateTokenSuccess, null, null, null);
            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

            }

            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onError(String error) {
                postEvent(MainEvent.onUpdateTokenError, error, null, null);
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
