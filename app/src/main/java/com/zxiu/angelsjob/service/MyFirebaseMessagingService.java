package com.zxiu.angelsjob.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zxiu.angelsjob.R;
import com.zxiu.angelsjob.activity.MainActivity;


/**
 * Created by Xiu on 9/20/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "Firebase";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("Firebase", "remoteMessage=" + remoteMessage);
        Log.i("Firebase", "remoteMessage.getNotification().getBody()=" + remoteMessage.getNotification().getBody());
        Log.i("Firebase", "remoteMessage.getMessageType()=" + remoteMessage.getMessageType());
        Log.i("Firebase", "remoteMessage.getData().size()=" + remoteMessage.getData().size());
        Log.i("Firebase", "From: " + remoteMessage.getFrom());
        showNotification(remoteMessage);

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }

    private void showNotification(RemoteMessage remoteMessage) {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setAutoCancel(true)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody()).setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
