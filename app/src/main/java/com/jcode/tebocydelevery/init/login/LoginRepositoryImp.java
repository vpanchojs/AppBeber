package com.jcode.tebocydelevery.init.login;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jcode.tebocydelevery.domain.FirebaseActionListenerCallback;
import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.init.login.events.LoginEvent;
import com.jcode.tebocydelevery.lib.base.EventBus;
import com.jcode.tebocydelevery.models.User;


public class LoginRepositoryImp implements LoginRepository {
    private EventBus eventBus;
    private FirebaseApi firebaseApi;

    public LoginRepositoryImp(EventBus eventBus, FirebaseApi firebaseApi) {
        this.eventBus = eventBus;
        this.firebaseApi = firebaseApi;
    }

    @Override
    public void subscribe() {
        firebaseApi.suscribeAuth(new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(LoginEvent.onRecoverySuccess);
            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

            }

            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onError(String error) {
                postEvent(LoginEvent.onRecoveryError);
            }
        });
    }

    @Override
    public void unsubscribe() {
        firebaseApi.unSuscribeAuth();
    }


    @Override
    public void singInCredential(String credential, int cod_servicie, User user) {
        firebaseApi.signIn_Credential(credential, cod_servicie, user, new FirebaseActionListenerCallback() {

            @Override
            public void onSuccess() {
                postEvent(LoginEvent.onSigInSuccess);

            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

            }
            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onError(String error) {
                postEvent(LoginEvent.onSiginError);
            }
        });
    }

    private void postEvent(int type, String message, String userEmail) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEvenType(type);
        loginEvent.setErroMessage(message);
        loginEvent.setEmail(userEmail);
        eventBus.post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null, null);
    }

}

