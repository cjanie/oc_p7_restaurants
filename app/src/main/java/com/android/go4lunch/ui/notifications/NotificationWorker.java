package com.android.go4lunch.ui.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NotificationWorker extends Worker {

    private NotificationManagerCompat notificationManager;

    private NotificationWorkManagerProgram workManagerProgram;

    private List<String> notificationTexts;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.notificationManager = NotificationManagerCompat.from(context);

        this.workManagerProgram = new NotificationWorkManagerProgram(context);

        this.notificationTexts = this.getNotificationTexts();

    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = this.createData(new NotificationValueObject(1, "Hy"));

        int notificationId = this.getInputData().getInt(NotificationConfig.NOTIFICATION_ID, 0);
        String notificationText = this.getInputData().getString(NotificationConfig.NOTIFICATION_TEXT);
        if(notificationText != null) {
            NotificationValueObject notificationVO = new NotificationValueObject(notificationId, notificationText);
            NotificationCompat.Builder notificationBuilder = this.configureNotificationBuilder(notificationVO);
            this.notificationManager.notify(notificationVO.getId(), notificationBuilder.build());


            data = this.createData(notificationVO);
            WorkRequest workRequest = this.createPeriodicWorkRequest(data);
            this.workManagerProgram.enqueueWorkRequest(workRequest);

        }

        return Result.success(data);
    }

    private Data createData(NotificationValueObject notificationVO) {
        return new Data.Builder()
                .putInt(NotificationConfig.NOTIFICATION_ID, notificationVO.getId())
                .putString(NotificationConfig.NOTIFICATION_TEXT, notificationVO.getText())
                .build();
    }

    private PeriodicWorkRequest createPeriodicWorkRequest(Data inputData) {
        // Constraints
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(false)
                .build();
        // Work request
        return new PeriodicWorkRequest
                .Builder(NotificationWorker.class,15, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();
    }

    private NotificationCompat.Builder configureNotificationBuilder(NotificationValueObject notificationVO) {
        // Configure Notification.Builder
        return new NotificationCompat.Builder(this.getApplicationContext(), NotificationConfig.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_person_24)
                .setContentTitle("Go4Lunch Time")
                .setContentText(notificationVO.getText())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(this.createNotificationContentIntent(notificationVO))
                .setAutoCancel(true);
    }

    private PendingIntent createNotificationContentIntent(NotificationValueObject notificationVO) {
        // Create Intent and return PendingIntent
        Intent notifyIntent = new Intent(this.getApplicationContext(), ActivityResult.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifyIntent.putExtra(NotificationConfig.NOTIFICATION_ID, notificationVO.getId());
        notifyIntent.putExtra(NotificationConfig.NOTIFICATION_TEXT, notificationVO.getText());

        return PendingIntent.getActivity(
                this.getApplicationContext(), 0, notifyIntent, PendingIntent.FLAG_IMMUTABLE
        );
    }

    private List<NotificationValueObject> getNotificationValueObjects() {
        List<NotificationValueObject> notificationVOs = new ArrayList<>();
        if(!this.notificationTexts.isEmpty()) {
            int notificationId = 0;
            for(String notificationText: this.notificationTexts) {
                notificationId += 1;
                NotificationValueObject notificationVO = new NotificationValueObject(
                    notificationId, notificationText
                );
                notificationVOs.add(notificationVO);
            }
        }
        return notificationVOs;
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
