package com.jcode.tebocydelevery.domain;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jcode.tebocydelevery.init.login.events.LoginEvent;
import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.Product;
import com.jcode.tebocydelevery.models.User;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FirebaseApi {


    private final static String USERS_PATH = "users";
    private final static String ORDER_PATH = "orders";
    private final static String PRODUCTS_PATH = "products";
    private final static String CAR_BUY_PATH = "carbuy";
    private final static String USER_ORDERS_PATH = "user-orders";
    private final static String ORDER_PRODUCTS_PATH = "orders-products";
    private final static String STORAGE_USER_PHOTO_PATH = "user-photos";


    private static final String TAG = "FirebaseApi";

    /*----*/

    private DatabaseReference reference;
    private StorageReference storageReference;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ListenerRegistration productsListener;
    private ListenerRegistration ordersListener;

    public FirebaseApi() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        this.db = FirebaseFirestore.getInstance();
        this.db.setFirestoreSettings(settings);
        this.reference = FirebaseDatabase.getInstance().getReference();
        this.mAuth = FirebaseAuth.getInstance();
        this.storageReference = FirebaseStorage.getInstance().getReference();
    }

    public DatabaseReference getDataReference() {
        return reference;
    }

    public String create() {
        return reference.push().getKey();
    }

    public String getAuthEmail() {
        String email = null;
        FirebaseAuth authData = FirebaseAuth.getInstance();
        FirebaseUser providerData = authData.getCurrentUser();
        if (providerData != null) {
            email = providerData.getEmail();
        }

        return email;
    }


    public void suscribeRackProducts(final FirebaseEventListenerCallback callback) {

        productsListener = db.collection(PRODUCTS_PATH).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    callback.onError(e.toString());
                    return;
                }

                for (DocumentChange dc : documentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            // Log.d(TAG, "New city: " + dc.getDocument().getData());
                            callback.onDocumentAdded(dc.getDocument());
                            break;
                        case MODIFIED:
                            //Log.d(TAG, "Modified city: " + dc.getDocument().getData());
                            callback.onDocumentModified(dc.getDocument());
                            break;
                        case REMOVED:
                            //Log.d(TAG, "Removed city: " + dc.getDocument().getData());
                            callback.onDocumentRemoved(dc.getDocument());
                            break;
                    }
                }

            }

        });
    }

    public void unsuscribeRackProducts() {
        if (productsListener != null) {
            productsListener.remove();
            Log.e(TAG, "SE REMOVIO PRODUCTOS");
        }
    }

    public void updatePhoto(final String photo, final FirebaseActionListenerCallback callback) {
        final Uri[] file = {Uri.fromFile(new File(photo))};
        StorageReference filepath = storageReference.child(STORAGE_USER_PHOTO_PATH).child(file[0].getLastPathSegment());

        filepath.putFile(file[0]).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                @SuppressWarnings("VisibleForTests")
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                db.collection(USERS_PATH).document(getUid()).update("url_photo", downloadUrl.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.toString());
                    }
                });

                /*
                reference.child(USERS_PATH).child(getUid()).child("url_photo").setValue(downloadUrl.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e(TAG, "asdasd");

                        if (task.isSuccessful()) {
                            callback.onSuccess();
                        } else {
                            callback.onError("error");
                        }

                    }
                });
                */

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, e.getMessage());
                callback.onError(e.getMessage());
            }
        });
    }

    public void getDataUserSuscribe(final FirebaseActionListenerCallback callback) {
        db.collection(USERS_PATH).document(getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        callback.onSuccess(documentSnapshot);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                        callback.onError(e.toString());
                    }
                });

    }

    public void getDataUserUnsucribe() {

    }

    /*Metodo funcionacion*/
    public String getUid() {
        return mAuth.getCurrentUser().getUid();
    }

    /*metodo funcionando*/
    public void suscribeAuth(final FirebaseActionListenerCallback callback) {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    callback.onSuccess();
                    // Log.i("AuthStateChanged", "User is signed in with uid: " + user.getUid());
                } else {
                    //Log.i("AuthStateChanged", "No user is signed in.");
                    callback.onError("No user is signed in.");
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    /*metodo funcionando*/
    public void unSuscribeAuth() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /*Metodo funcionacion*/
    public void logout() {
        mAuth.signOut();
    }


    public void updateUser(final User user, final FirebaseActionListenerCallback callback) {
        Map<String, Object> userValues = user.toMapPost();
        db.collection(USERS_PATH).document(getUid())
                .update(userValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.toString());
                    }
                });
    }


    public void signIn_Credential(String credential, int cod_servicie, final User user, final FirebaseActionListenerCallback callback) {
        AuthCredential authCredential;
        switch (cod_servicie) {
            case LoginEvent.login_facebook:
                authCredential = FacebookAuthProvider.getCredential(credential);
                break;
            case LoginEvent.login_google:
                authCredential = GoogleAuthProvider.getCredential(credential, null);
                break;
            default:
                authCredential = GoogleAuthProvider.getCredential(credential, null);
        }

        mAuth.signInWithCredential(authCredential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(final AuthResult authResult) {

                        getDataUserSuscribe(new FirebaseActionListenerCallback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onSuccess(QuerySnapshot snapshot) {

                            }

                            @Override
                            public void onSuccess(DocumentSnapshot snapshot) {

                                if (snapshot.exists()) {
                                    Log.e(TAG, "si existe");
                                    callback.onSuccess();
                                } else {
                                    Log.e(TAG, "no existe");
                                    saveUser(user, authResult.getUser().getUid(), new FirebaseActionListenerCallback() {
                                        @Override
                                        public void onSuccess() {
                                            callback.onSuccess();
                                        }

                                        @Override
                                        public void onSuccess(QuerySnapshot snapshot) {

                                        }

                                        @Override
                                        public void onSuccess(DocumentSnapshot snapshot) {

                                        }

                                        @Override
                                        public void onError(String error) {
                                            callback.onError(error);
                                        }
                                    });
                                }

                            }

                            @Override
                            public void onError(String error) {
                                callback.onError(error);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getMessage());
            }
        });
    }


    public void saveUser(User user, String id, final FirebaseActionListenerCallback callback) {
        Map<String, Object> userValues = user.toMapPost();
        db.collection(USERS_PATH).document(id)
                .set(userValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added with ID: ");
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.toString());
                    }
                });
    }


    public void getProductOrder(String idOrder, final FirebaseActionListenerCallback callback) {
        db.collection(ORDER_PATH).document(idOrder).collection(PRODUCTS_PATH)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        Log.e(TAG, documentSnapshots.toString());
                        callback.onSuccess(documentSnapshots);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                    }
                });
    }

    public void generateOrder(Order order, final FirebaseActionListenerCallback callback) {
        WriteBatch batch = db.batch();
        order.setState_order(Order.ORDER_PROCESSANDO);
        order.setDescription_state_order("El vendedor revisara su pedido, para proceder a prepararlo");
        order.setMethod_pay(Order.PAY_EFECTIVO);


        DocumentReference refOrd = db.collection(ORDER_PATH).document();
        batch.set(refOrd, order.toMapOrder());


        DocumentReference refOrdProduct = db.collection(ORDER_PATH).document(refOrd.getId());

        for (Product p : order.getProducts()) {
            batch.set(refOrdProduct.collection(PRODUCTS_PATH).document(p.getId()), p.toMapProductsOrder());
        }

        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "todo bien");
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "malos" + e);
                        callback.onError(e.toString());
                    }
                });
    }


    public void unSuscribeMyOrders() {
        if (ordersListener != null) {
            ordersListener.remove();
        }
    }


    public void suscribeMyOrders(final FirebaseEventListenerCallback callback) {
        ordersListener = db.collection(ORDER_PATH).whereEqualTo("cliente.id_user", getUid()).whereLessThanOrEqualTo("state_order", 2)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        Log.e(TAG, "My orders" + documentSnapshots.isEmpty());

                        if (documentSnapshots.isEmpty()) {
                            callback.onError("No existen pedidos");
                            return;
                        }

                        if (e != null) {
                            Log.e(TAG, "listen:error", e);
                            callback.onError(e.toString());
                            return;
                        }
                        if (documentSnapshots != null || !documentSnapshots.isEmpty()) {
                            Log.e(TAG, "Recibiendo ordenes");
                            for (DocumentChange dc : documentSnapshots.getDocumentChanges()) {
                                switch (dc.getType()) {
                                    case ADDED:
                                        //Log.e(TAG, "New city: " + dc.getDocument().getData());
                                        callback.onDocumentAdded(dc.getDocument());
                                        break;
                                    case MODIFIED:
                                        //Log.e(TAG, "Modified city: " + dc.getDocument().getData());
                                        callback.onDocumentModified(dc.getDocument());
                                        break;
                                    case REMOVED:
                                        //Log.e(TAG, "Removed city: " + dc.getDocument().getData());
                                        callback.onDocumentRemoved(dc.getDocument());
                                        break;
                                }
                            }
                        } else {
                            callback.onError("Lista Vacia");
                        }
                    }
                });
    }

    public void cancelOrder(final Order order, final String reason, final FirebaseActionListenerCallback callback) {
        db.runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                List<DocumentReference> references = new ArrayList<>();
                List<Double> stocksList = new ArrayList<>();
                double stock = 0;

                DocumentReference orderRef = db.collection(ORDER_PATH).document(order.getId_order());
                DocumentSnapshot orderDoc = transaction.get(orderRef);

                if (orderDoc.getLong("state_order") >= Order.ORDER_ENVIADA) {
                    throw new FirebaseFirestoreException("La orden ya no puede ser cancelada",
                            FirebaseFirestoreException.Code.ABORTED);
                }

                if (orderDoc.getLong("state_order") == Order.ORDER_PREPARADA) {
                    for (Product p : order.getProducts()) {
                        DocumentReference reference = db.collection(PRODUCTS_PATH).document(p.getId());
                        DocumentSnapshot documentSnapshot = transaction.get(reference);
                        stock = documentSnapshot.getLong("stock") + p.getLot();
                        references.add(reference);
                        stocksList.add(stock);
                    }

                    for (int i = 0; i < references.size(); i++) {
                        transaction.update(references.get(i), "stock", stocksList.get(i));
                    }
                }
                Map<String, Object> updates = new HashMap<>();
                updates.put("description_state_order", reason);
                updates.put("state_order", Order.ORDER_CANCELATE);
                transaction.update(orderRef, updates);
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                callback.onSuccess();
                /*
                Map<String, Object> updates = new HashMap<>();
                updates.put("description_state_order", reason);
                updates.put("state_order", Order.ORDER_CANCELATE);
                db.collection(ORDER_PATH).document(order.getId_order()).
                        update(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                callback.onSuccess();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callback.onError(e.toString());
                            }
                        });

*/
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, e + " error");
                callback.onError(e.getMessage());
            }
        });

        /*
        if (order.getState_order() == Order.ORDER_PROCESSANDO) {
            //se actualiza normalemente
            Map<String, Object> updates = new HashMap<>();
            updates.put("description_state_order", reason);
            updates.put("state_order", Order.ORDER_CANCELATE);
            db.collection(ORDER_PATH).document(order.getId_order()).
                    update(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            callback.onSuccess();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onError(e.toString());
                        }
                    });
        } else {
            //sumar los productos que se quito
            db.runTransaction(new Transaction.Function<Object>() {
                @Nullable
                @Override
                public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                    List<DocumentReference> references = new ArrayList<>();
                    List<Double> stocksList = new ArrayList<>();
                    double stock = 0;
                    for (Product p : order.getProducts()) {
                        Log.e(TAG, "id " + p.getId());
                        DocumentReference reference = db.collection(PRODUCTS_PATH).document(p.getId());
                        //Log.e(TAG, "id " + p.getId());
                        DocumentSnapshot documentSnapshot = transaction.get(reference);
                        stock = documentSnapshot.getLong("stock") + p.getLot();
                        //Log.e(TAG, "id " + p.getId() + "lot" + stock);
                        references.add(reference);
                        stocksList.add(stock);
                    }
                    for (int i = 0; i < references.size(); i++) {
                        transaction.update(references.get(i), "stock", stocksList.get(i));
                    }
                    return null;
                }
            }).addOnSuccessListener(new OnSuccessListener<Object>() {
                @Override
                public void onSuccess(Object o) {

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("description_state_order", reason);
                    updates.put("state_order", Order.ORDER_CANCELATE);
                    db.collection(ORDER_PATH).document(order.getId_order()).
                            update(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    callback.onSuccess();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    callback.onError(e.toString());
                                }
                            });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, e.toString());
                    callback.onError(e.toString());
                }
            });

        }
        */


    }

    public void getMyHistoryOrders(final FirebaseActionListenerCallback callback) {
        db.collection(ORDER_PATH).whereEqualTo("cliente.id_user", getUid()).whereGreaterThan("state_order", 2)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty())
                            callback.onSuccess(documentSnapshots);
                        else
                            callback.onError("No existen pedidos");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.toString());
                    }
                });
    }
}
