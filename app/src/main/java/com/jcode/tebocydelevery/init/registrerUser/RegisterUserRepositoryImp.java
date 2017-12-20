package com.jcode.tebocydelevery.init.registrerUser;

import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.models.User;
import com.jcode.tebocydelevery.init.registrerUser.events.RegisterUserEvents;
import com.jcode.tebocydelevery.lib.base.EventBus;

/**
 * Created by victo on 19/01/2017.
 */

public class RegisterUserRepositoryImp implements RegisterUserRepository {
    private EventBus eventBus;
    private FirebaseApi firebaseApi;

    public RegisterUserRepositoryImp(EventBus eventBus, FirebaseApi firebaseApi) {
        this.eventBus = eventBus;
        this.firebaseApi = firebaseApi;
    }

    @Override
    public void doRegister(User user) {
/*
            firebaseApi.registerUser(user, new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    postEvent(RegisterUserEvents.onSucces, null);
                }

                @Override
                public void onSuccess(DataSnapshot snapshot) {

                }

                @Override
                public void onError(String error) {
                    postEvent(RegisterUserEvents.onError, error);
                }
            });
            */

    }

    private void postEvent(int type, String errorMessage) {
        RegisterUserEvents event = new RegisterUserEvents();
        event.setEvent(type);
        if (errorMessage != null) {
            event.setMenssage(errorMessage);
        }
        eventBus.post(event);
    }
}
