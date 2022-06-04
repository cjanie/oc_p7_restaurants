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
import androidx.work.WorkManager;
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

    private WorkManager workManager;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.workManager= WorkManager.getInstance(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        new ShowNotificationAction(this.getApplicationContext()).run();

        Data data = this.createData(new NotificationValueObject(1, "Hy"));

        int notificationId = this.getInputData().getInt(NotificationConfig.NOTIFICATION_ID, 0);
        String notificationText = this.getInputData().getString(NotificationConfig.NOTIFICATION_TEXT);
        if(notificationText != null) {
            NotificationValueObject notificationVO = new NotificationValueObject(notificationId, notificationText);


            data = this.createData(notificationVO);
            WorkRequest workRequest = this.createPeriodicWorkRequest(data);
            this.workManager.enqueue(workRequest);

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
}
