package com.android.go4lunch.ui.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.go4lunch.R;

import java.util.Arrays;
import java.util.List;

public class ShowNotificationAction {

    private Context context;

    private NotificationManagerCompat notificationManager;

    public ShowNotificationAction(Context context) {

        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NotificationConfig.NOTIFICATION_CHANNEL_ID,
                    NotificationConfig.NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);

            this.notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    public void run() {
        List<String> notifs = Arrays.asList("notif-1", "notif-2");
        if(!notifs.isEmpty()) {
            for (int i = 0; i < notifs.size(); i++) {
                SystemClock.sleep(2000);
                this.notifyWorkmatesSelections(i, notifs.get(i));
            }
        }
    }

    private void notifyWorkmatesSelections(int id, String text) {


        Notification notification = new NotificationCompat
                .Builder(this.context, NotificationConfig.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle("Go4Lunch Time")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();

        this.notificationManager.notify(id, notification);

    }



}
