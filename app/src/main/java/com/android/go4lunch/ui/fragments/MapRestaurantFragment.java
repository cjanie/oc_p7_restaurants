package com.android.go4lunch.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.ui.viewmodels.MapViewModel;
import com.android.go4lunch.ui.viewmodels.factories.MapViewModelFactory;
import com.android.go4lunch.ui.viewmodels.SharedViewModel;
import com.android.go4lunch.ui.viewmodels.factories.SharedViewModelFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapRestaurantFragment extends Fragment implements OnMapReadyCallback {

    // View models
    private SharedViewModel sharedViewModel;

    private MapViewModel mapViewModel;


    // UI
    @BindView(R.id.map_view)
    MapView mapView;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    // Constructor
    public MapRestaurantFragment(SharedViewModel sharedViewModel) {
        this.sharedViewModel = sharedViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        MapViewModelFactory mapViewModelFactory = ((Launch) this.getActivity().getApplication()).mapViewModelFactory();
        this.mapViewModel = new ViewModelProvider(this, mapViewModelFactory).get(MapViewModel.class);

        // UI
        View root = inflater.inflate(R.layout.fragment_restaurant_map, container, false);
        ButterKnife.bind(this, root);

        // *** MAP ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if(savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        // Call the Map View Model Update Action when My Position is available
        // The result of the action is listened in the call back on map ready
        this.updateRestaurantsMarkersAtInitMyPosition();

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if(mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Listening to the result of my position to show it on the map
        this.sharedViewModel.getGeolocation().observe(this, geolocation -> {
            if(geolocation != null) {
                googleMap.setMyLocationEnabled(true);
            }
        });

        // Listening to the result of the view model action to show markers on the map
        this.mapViewModel.getRestaurantsMarkersLiveData().observe(this, markers -> {
            if(!markers.isEmpty()) {
                for(MarkerOptions marker: markers) {
                    googleMap.addMarker(marker);
                }
            }
        });
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void updateRestaurantsMarkersAtInitMyPosition() {
        this.sharedViewModel.getGeolocation().observe(this.getViewLifecycleOwner(), geolocation -> {
            // Action of the Map View Model to update data when geolocation is available;
            this.mapViewModel.fetchRestaurantsToUpdateRestaurantsMarkersLiveData(
                    geolocation.getLatitude(),
                    geolocation.getLongitude(),
                    1000);
        });
    }
}
