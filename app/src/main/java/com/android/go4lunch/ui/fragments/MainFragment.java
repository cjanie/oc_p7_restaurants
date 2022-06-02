package com.android.go4lunch.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.android.go4lunch.BuildConfig;
import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.ui.adapters.ViewPagerAdapter;
import com.android.go4lunch.ui.utils.CreateActivityResultLauncher;
import com.android.go4lunch.ui.viewmodels.SharedViewModel;
import com.android.go4lunch.ui.viewmodels.factories.SharedViewModelFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainFragment extends Fragment {

    // DATA

    private SharedViewModel sharedViewModel;

    // UI

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.container)
    ViewPager2 viewPager;

    // FOR LOCATION PERMISSION

    private ActivityResultLauncher locationPermissionsResultLauncher;

    private final String PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    private final int REQUEST_CODE = 123;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.locationPermissionsResultLauncher = this.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isPermissionGranted -> {
                    if(isPermissionGranted) {
                        handleLocationPermissionIsGranted();
                    } else {
                        goToSettings();
                    }
                }
        );
        this.locationPermissionsResultLauncher.launch(this.PERMISSION);

        SharedViewModelFactory sharedViewModelFactory = ((Launch) this.getActivity().getApplication()).sharedViewModelFactory();
        this.sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);

        // ViewPagerAdapter attachs ViewPager to Tablaout
        new ViewPagerAdapter(this.getActivity().getSupportFragmentManager(), this.getLifecycle(), this.tabLayout, this.viewPager, this.sharedViewModel);

        return root;
    }

    @SuppressLint("MissingPermission")
    private void handleLocationPermissionIsGranted() {
        // Get location when permission is not missing
        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this.getActivity());

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if(!locationResult.equals(null) && !locationResult.getLocations().isEmpty()) {
                    stopLocationUpdates(fusedLocationProviderClient, this);
                    saveLocation(locationResult.getLocations().get(0));
                }
            }

            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                if(locationAvailability.isLocationAvailable()) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                        saveLocation(location);
                    });
                } else {
                    requestLocationUpdates(fusedLocationProviderClient, this);
                    Toast.makeText(getActivity(), getText(R.string.location_not_available), Toast.LENGTH_SHORT).show();
                }

            }
        };

        requestLocationUpdates(fusedLocationProviderClient, locationCallback);
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates(
            FusedLocationProviderClient fusedLocationProviderClient,
            LocationCallback locationCallback
    ) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(50);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
        );
    }

    private void stopLocationUpdates(
            FusedLocationProviderClient fusedLocationProviderClient,
            LocationCallback locationCallback
    ) {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void saveLocation(Location location) {
        if(location != null) {
            this.sharedViewModel.setGeolocation(new Geolocation(
                    location.getLatitude(),
                    location.getLongitude())
            );
        }
    }

    private void goToSettings() {

        new AlertDialog.Builder(this.getContext())
                .setTitle(R.string.location_rationale)
                .setMessage(R.string.location_permission_rationale_text)
                .setPositiveButton("OK", (dialogInterface, i) -> {
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
