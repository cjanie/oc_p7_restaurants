package com.android.go4lunch.ui.notifications;

import android.content.Context;

import androidx.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    private ShowNotificationAction showNotificationAction;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.showNotificationAction = new ShowNotificationAction(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        this.showNotificationAction.run();
        return Result.success();
    }

}
