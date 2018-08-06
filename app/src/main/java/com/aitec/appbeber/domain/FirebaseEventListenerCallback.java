package com.aitec.appbeber.domain;

import com.google.firebase.firestore.DocumentSnapshot;

public interface FirebaseEventListenerCallback {

    void onDocumentRemoved(DocumentSnapshot snapshot);

    void onError(String error);

    void onDocumentAdded(DocumentSnapshot snapshot);

    void onDocumentModified(DocumentSnapshot snapshot);
}
