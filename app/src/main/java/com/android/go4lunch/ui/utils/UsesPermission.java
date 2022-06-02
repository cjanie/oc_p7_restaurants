package com.android.go4lunch.ui.utils;

import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import pub.devrel.easypermissions.EasyPermissions;

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
}
