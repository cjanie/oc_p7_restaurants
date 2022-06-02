package com.android.go4lunch.ui.utils;

import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import pub.devrel.easypermissions.EasyPermissions;

public class CreateActivityResultLauncher {

    public ActivityResultLauncher create(
            Fragment receiver,
            int requestCode,
            String permission
    ) {
        ActivityResultLauncher launcher = receiver.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isPermissionGranted -> {
                    if(isPermissionGranted) {
                        EasyPermissions.onRequestPermissionsResult(
                                requestCode,
                                new String[] {permission},
                                new int[]{PackageManager.PERMISSION_GRANTED},
                                receiver
                        );
                    } else {
                        EasyPermissions.onRequestPermissionsResult(
                                requestCode,
                                new String[] {permission},
                                new int[]{PackageManager.PERMISSION_DENIED},
                                receiver
                        );
                    }
                }
        );
        return launcher;
    }
}
