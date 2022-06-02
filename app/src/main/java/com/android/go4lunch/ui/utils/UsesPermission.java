package com.android.go4lunch.ui.utils;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.android.go4lunch.BuildConfig;
import com.android.go4lunch.R;

public abstract class UsesPermission extends Fragment {

    protected ActivityResultLauncher createResultActivityLauncher() {
        ActivityResultLauncher launcher = this.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isPermissionGranted -> {
                    if(isPermissionGranted) {
                        handlePermissionIsGranted();
                    } else {
                        goToSettings();
                    }
                }
        );

        return launcher;
    }

    protected abstract void handlePermissionIsGranted();

    protected abstract void goToSettings();

    protected void goToSettingsWithRationale(int title, int message) {
        new AlertDialog.Builder(this.getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                )
                .create().show();
    }
}
