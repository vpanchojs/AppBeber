package com.aitec.appbeber.domain;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public interface FirebaseActionListenerCallback {
    void onSuccess();

    void onSuccess(DocumentSnapshot snapshot);

    void onSuccess(QuerySnapshot snapshot);

    void onError(String error);
}
