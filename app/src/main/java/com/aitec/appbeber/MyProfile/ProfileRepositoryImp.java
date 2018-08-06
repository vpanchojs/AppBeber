package com.aitec.appbeber.MyProfile;

import android.util.Log;

import com.aitec.appbeber.MyProfile.event.ProfileEvent;
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

public class ProfileRepositoryImp implements ProfileRepository {
    private static final String TAG = "ProfileRepository";
    private EventBus eventBus;
    private FirebaseApi firebaseApi;

    public ProfileRepositoryImp(EventBus eventBus, FirebaseApi firebaseApi) {
        this.eventBus = eventBus;
        this.firebaseApi = firebaseApi;
    }

    @Override
    public void singOut() {
        firebaseApi.logout();
        postEvent(ProfileEvent.onSignOutSucces, null, null, null);
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
                    postEvent(ProfileEvent.onGetUserSuccess, null, user, null);
                } else {
                    postEvent(ProfileEvent.onGetUserError, "Usuario no encontrado", null, null);
                }
            }

            @Override
            public void onError(String error) {
                postEvent(ProfileEvent.onGetUserError, error, null, null);

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
                postEvent(ProfileEvent.onUpdateUserSuccess, "Actualizado correctamente", null, null);
            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

            }

            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onError(String error) {
                postEvent(ProfileEvent.onUpdateUserError, error, null, null);
            }
        });
    }

    @Override
    public void updatePhotoUser(String photo) {
        firebaseApi.updatePhoto(photo, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(ProfileEvent.onUpdatePhotoSuccess, "Foto actualizada correctamente", null, null);
            }

            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

            }

            @Override
            public void onSuccess(QuerySnapshot snapshot) {

            }

            @Override
            public void onError(String error) {
                postEvent(ProfileEvent.onUpdatePhotoError, error, null, null);
            }
        });
    }



    private void postEvent(int type, String message, Object object, List<Object> objectList) {
        ProfileEvent event = new ProfileEvent();
        event.setEvent(type);
        event.setMenssage(message);
        event.setObject(object);
        event.setObjectList(objectList);
        eventBus.post(event);
    }
}
