package com.android.go4lunch.ui.notifications;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.WorkerParameters;

import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.models.SelectionModel;

import java.util.concurrent.TimeUnit;

public class NotificationWorkManagerProgram {

    private Context context;

    private WorkManager workManager;

    // Constructor
    public NotificationWorkManagerProgram(Context context) {
        this.context = context;
        this.workManager = WorkManager.getInstance(this.context);
    }

    public void enqueueWorkRequest(WorkRequest workRequest) {
        this.workManager.enqueue(workRequest);
    }


}
