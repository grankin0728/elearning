package com.suusoft.elistening.service.fcm;

import com.suusoft.elistening.R;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.suusoft.elistening.view.activity.MainActivity;

/**
 * Created by ASUS on 5/8/2017.
 */

public class MyFireBaseCloudMessage extends FirebaseMessagingService {
    final static String TAG = MyFireBaseCloudMessage.class.getSimpleName();
    private static int REQUEST_CODE = 0;
    private static int NOTIFICATION_ID = 0;
    private static String notifi_chanel = "notify_001";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getNotification());
    }


    private void sendNotification(RemoteMessage.Notification notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stop)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(uri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1103, notificationBuilder.build());
    }

//    private void sendNotification(RemoteMessage.Notification notification) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createNotificationChannel();
//        }
//        Intent intent = new Intent(this, MainActivity.class);
//        REQUEST_CODE++;
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, notifi_chanel)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(notification.getTitle())
//                .setContentText(notification.getBody())
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(notification.getBody()))
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        NOTIFICATION_ID++;
//        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
//    }


    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager
                mNotificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
        // The id of the channel.
        String id = notifi_chanel;
        // The user-visible name of the channel.
        CharSequence name = "Notification";
        // The user-visible description of the channel.
        String description = "Notification Application";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        // Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.setShowBadge(false);
        mNotificationManager.createNotificationChannel(mChannel);
    }
}
