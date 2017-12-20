package com.jcode.tebocydelevery.domain;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public interface FirebaseActionListenerCallback {
    void onSuccess();

    void onSuccess(DocumentSnapshot snapshot);

    void onSuccess(QuerySnapshot snapshot);

    void onError(String error);
}
