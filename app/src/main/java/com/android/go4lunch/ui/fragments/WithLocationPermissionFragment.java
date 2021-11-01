package com.android.go4lunch.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.go4lunch.ui.events.InitMyPositionEvent;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class WithLocationPermissionFragment<T> extends Fragment {

    protected String[] permissions;

    public int requestCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        this.requestCode = 123;
        this.startLocationPermissionRequest();
    }

    private void startLocationPermissionRequest() {
        // Forward results to EasyPermissions
        ActivityResultLauncher<String> permissionResultLauncher = this.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if(isGranted) {
                        EasyPermissions.onRequestPermissionsResult(
                                this.requestCode,
                                this.permissions,
                                new int[]{PackageManager.PERMISSION_GRANTED},
                                this);
                    } else {
                        EasyPermissions.onRequestPermissionsResult(
                                this.requestCode,
                                this.permissions,
                                new int[]{PackageManager.PERMISSION_DENIED},
                                this);
                    }
                }
        );

        // Identify permission
        permissionResultLauncher.launch(this.permissions[0]);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fire init my position event
        // received by subscription in Child fragments:
        // ListRestaurantFragment, MapRestaurantFragment
        this.initMyPosition();
    }

    // MY POSITION WITH LOCATION PERMISSIONS

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(123)
    private void initMyPosition() {
        // Control
        if(EasyPermissions.hasPermissions(this.getActivity(), this.permissions)) {
            // Get location when permission is not missing
            FusedLocationProviderClient fusedlocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(this.getActivity());
            fusedlocationProviderClient.getLastLocation().addOnSuccessListener(this.getActivity(), location -> {
                if(location != null) {
                    Double myLatitude = location.getLatitude();
                    Double myLongitude = location.getLongitude();
                    // Fire init my position event
                    EventBus.getDefault().post(new InitMyPositionEvent(myLatitude, myLongitude));
                }
            });
        } else {
            // Demand permission if missing, explaining that a permission is needed to get the user location
            EasyPermissions.requestPermissions(
                    this,
                    "We need permission to get your location",
                    123,
                    this.permissions);
        }
    }

}
