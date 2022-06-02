package com.android.go4lunch.ui.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String endpoint = this.getInputData().getString(NotificationConfig.NOTIFICATION_ENDPOINT_REQUEST);
        if(endpoint != null) {
            Data inputData = this.getData(endpoint);
        }

        Data outputData = new Data.Builder()
                .putString(NotificationConfig.NOTIFICATION_DATA, "Hi data")
                .build();
        return Result.success(outputData);
    }

    private Data getData(String endpoint) {
        return new Data.Builder().build();
    }

}
