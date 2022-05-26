package com.android.go4lunch.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.ui.adapters.ViewPagerAdapter;
import com.android.go4lunch.ui.viewmodels.SharedViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainFragment extends Fragment {

    // FOR LOCATION PERMISSION

    private ActivityResultLauncher launcher;

    private final String[] PERMISSIONS = new String[] {Manifest.permission.ACCESS_FINE_LOCATION};

    private final int REQUEST_CODE = 123;

    // DATA

    private SharedViewModel sharedViewModel;

    // UI

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.container)
    ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.launcher = this.createActivityResultLauncher();
        this.requestLocationPermission();
        this.initMyPosition();

        this.sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);

        // ViewPagerAdapter attachs ViewPager to Tablaout
        new ViewPagerAdapter(this.getActivity().getSupportFragmentManager(), this.getLifecycle(), this.tabLayout, this.viewPager, this.sharedViewModel);

        return root;
    }

    private ActivityResultLauncher createActivityResultLauncher() {
        return this.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if(isGranted) {
                        EasyPermissions.onRequestPermissionsResult(
                                this.REQUEST_CODE,
                                this.PERMISSIONS,
                                new int[]{PackageManager.PERMISSION_GRANTED},
                                this
                        );
                    } else {
                        EasyPermissions.onRequestPermissionsResult(
                                this.REQUEST_CODE,
                                this.PERMISSIONS,
                                new int[]{PackageManager.PERMISSION_DENIED},
                                this
                        );
                    }
                }
        );
    }

    private void requestLocationPermission() {
        this.launcher.launch(this.PERMISSIONS[0]);
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(123)
    private void initMyPosition() {
        // Control
        if(EasyPermissions.hasPermissions(this.getActivity(), this.PERMISSIONS)) {
            // Get location when permission is not missing
            FusedLocationProviderClient fusedlocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(this.getActivity());
            fusedlocationProviderClient.getLastLocation().addOnSuccessListener(this.getActivity(), location -> {
                if(location != null) {
                    this.sharedViewModel.setGeolocation(new Geolocation(
                            location.getLatitude(),
                            location.getLongitude())
                    );
                } else {
                    Toast.makeText(this.getActivity(), this.getText(R.string.location_not_available), Toast.LENGTH_SHORT).show();
                }
            });
        } else if(EasyPermissions.permissionPermanentlyDenied(this, this.PERMISSIONS[0])) {
            // Demand permission if missing, explaining that a permission is needed to get the user location
            /*
            EasyPermissions.requestPermissions(
                    this,
                    this.getString(R.string.location_permission_rationale_text),
                    123,
                    this.PERMISSIONS);
             */
                new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
