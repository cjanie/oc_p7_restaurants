package com.android.go4lunch.ui;

import androidx.fragment.app.Fragment;

import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public abstract class WithLocationPermissionFragment extends Fragment {

    protected FusedLocationProviderClient fusedlocationProviderClient;

    protected abstract void initMyPosition(List<RestaurantVO> restaurants);

    protected void showEducationalUI() {
        // Explain to the user that
        // the feature is unavailable because the features requires a permission that the user has denied.
        System.out.println("education UI ****************");
    }
}
