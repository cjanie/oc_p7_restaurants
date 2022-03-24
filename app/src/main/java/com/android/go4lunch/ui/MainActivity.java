package com.android.go4lunch.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.android.go4lunch.R;


import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.ui.adapters.ViewPagerAdapter;

import com.android.go4lunch.ui.viewmodels.SharedViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity {

    // UI
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.container)
    ViewPager2 viewPager;

    private final String[] permissions = new String[] {Manifest.permission.ACCESS_FINE_LOCATION};

    public final int requestCode = 123;

    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // UI
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // ViewPagerAdapter attachs ViewPager to Tablaout
        new ViewPagerAdapter(this.getSupportFragmentManager(), this.getLifecycle(), this.tabLayout, this.viewPager, this.sharedViewModel);

        // Location
        this.requestLocationPermission();

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.initMyPosition();
    }

    private void requestLocationPermission() {
        ActivityResultLauncher launcher = this.registerForActivityResult(
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
        launcher.launch(this.permissions[0]);
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(123)
    private void initMyPosition() {
        // Control
        if(EasyPermissions.hasPermissions(this, this.permissions)) {
            // Get location when permission is not missing
            FusedLocationProviderClient fusedlocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(this);
            fusedlocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
                if(location != null) {
                    this.sharedViewModel.setGeolocation(new Geolocation(
                            location.getLatitude(),
                            location.getLongitude())
                    );
                }
            });
        } else {
            // Demand permission if missing, explaining that a permission is needed to get the user location
            EasyPermissions.requestPermissions(
                    this,
                    this.getString(R.string.permission_rationale_text),
                    123,
                    this.permissions);
        }
    }

}