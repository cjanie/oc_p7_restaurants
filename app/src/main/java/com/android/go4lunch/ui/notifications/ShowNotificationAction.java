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

import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

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

        if(!this.getNotificationTexts().isEmpty()) {
            for (int i = 0; i < this.getNotificationTexts().size(); i++) {
                this.notifyWorkmatesSelections(i, this.getNotificationTexts().get(i));
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
                .setTimeoutAfter(6000)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(this.createNotificationContentIntent(id, text))
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


    private List<String> getNotificationTexts() {
        List<String> notifications = new ArrayList<>();
        if(!this.getSelections().isEmpty()) {
            for(Selection selection: this.getSelections()) {
                notifications.add(selection.getWorkmateName() + " is eating at " + selection.getRestaurantName());
            }
        }
        return notifications;
    }

    private List<Selection> getSelections() {
        // Mock
        Selection selection1 = new Selection("resto1", "workmate1");
        selection1.setWorkmateName("Janie");
        selection1.setRestaurantName("Chez Jojo");
        selection1.setId("selection1");

        return Arrays.asList(selection1);
    }

}
