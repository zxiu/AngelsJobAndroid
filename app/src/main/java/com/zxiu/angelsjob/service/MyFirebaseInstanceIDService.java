package com.zxiu.angelsjob.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Xiu on 9/20/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    String TAG = "FirebaseInstanceId";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    public void sendRegistrationToServer(String refreshedToken) {

    }
}
