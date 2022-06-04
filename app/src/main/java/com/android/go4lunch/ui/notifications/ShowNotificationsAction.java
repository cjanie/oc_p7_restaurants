package com.android.go4lunch.ui.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;

import androidx.activity.result.ActivityResult;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.usecases.ReceiveNotificationsUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class ShowNotificationsAction {

    private Context context;

    // To show notif
    private NotificationManagerCompat notificationManager;

    private ReceiveNotificationsUseCase receiveNotificationsUseCase;

    public ShowNotificationsAction(Context context, ReceiveNotificationsUseCase receiveNotificationsUseCase) {

        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NotificationConfig.NOTIFICATION_CHANNEL_ID,
                    NotificationConfig.NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);

            this.notificationManager.createNotificationChannel(notificationChannel);
        }

        this.receiveNotificationsUseCase = receiveNotificationsUseCase;
    }

    public void run() {
        this.receiveNotificationsUseCase.handle()
                .subscribe(notifications -> {
                    if(!notifications.isEmpty()) {
                        for (com.android.go4lunch.businesslogic.entities.Notification notification: notifications) {
                            this.notifyWorkmatesSelections(
                                    notification.getId(),
                                    notification.getSelection().getWorkmateName() + " "
                                            + this.context.getString(R.string.is_eating_at) + " "
                                            + notification.getSelection().getRestaurantName()
                            );
                        }
                    }
                });
    }

    private void notifyWorkmatesSelections(int id, String text) {
        Notification notification = new NotificationCompat
                .Builder(this.context, NotificationConfig.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(this.context.getString(R.string.app_title))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(this.createNotificationContentIntent(id, text))
                .setAutoCancel(true)
                .build();

        this.notificationManager.notify(id, notification);

    }

    private PendingIntent createNotificationContentIntent(int id, String text) {
        // Create Intent and return PendingIntent
        Intent notifyIntent = new Intent(this.context, ActivityResult.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifyIntent.putExtra(NotificationConfig.NOTIFICATION_ID, id);
        notifyIntent.putExtra(NotificationConfig.NOTIFICATION_TEXT, text);

        return PendingIntent.getActivity(
                this.context, 0, notifyIntent, PendingIntent.FLAG_IMMUTABLE
        );
    }

}
