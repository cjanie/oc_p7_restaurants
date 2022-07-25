package com.android.go4lunch.ui.loader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.android.go4lunch.R;

public class LoadingDialog {

    private Activity activity;

    private AlertDialog dialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.activity);
        LayoutInflater inflater = this.activity.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.loader, null));
        dialogBuilder.setCancelable(false);
        this.dialog = dialogBuilder.create();
    }

    public void showLoadingDialog() {
        this.dialog.show();
    }

    public void dismissDialog() {
        this.dialog.dismiss();
    }
}
