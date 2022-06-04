package com.android.go4lunch.ui.notifications;

import android.content.Context;

import androidx.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.go4lunch.Launch;

public class NotificationWorker extends Worker {

    private ShowNotificationsAction showNotificationsAction;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.showNotificationsAction = ((Launch) context.getApplicationContext()).showNotificationsAction();
    }

    @NonNull
    @Override
    public Result doWork() {
        this.showNotificationsAction.run();
        return Result.success();
    }

}
