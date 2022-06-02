package com.android.go4lunch.ui.notifications;

import android.content.Context;

import androidx.annotation.NonNull;
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
        String endPoint = this.getInputData().getString(NotificationConfig.NOTIFICATION_ENDPOINT_REQUEST);
        if(endPoint != null) {
            // getData(endpoint);
        }

        Data outputData = new Data.Builder()
                .putString(NotificationConfig.NOTIFICATION_DATA, "Hi data")
                .build();
        return Result.success();
    }
}
