package com.aitec.appbeber;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstance";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
        SharedPreferences prefs =
                getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", refreshedToken);
        editor.putBoolean("token_send", false);
        editor.commit();
        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
    }
}
