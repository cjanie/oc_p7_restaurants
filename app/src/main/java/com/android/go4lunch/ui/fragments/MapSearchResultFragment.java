package com.android.go4lunch.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.ui.Cache;
import com.android.go4lunch.ui.configs.RestaurantDetailsActivityIntentConfig;
import com.android.go4lunch.ui.viewmodels.MapViewModel;
import com.android.go4lunch.ui.viewmodels.factories.MapViewModelFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import butterknife.ButterKnife;

public class MapSearchResultFragment extends MapGoogleFragment {

    private MapViewModel mapViewModel;

    private Cache cache;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        MapViewModelFactory mapViewModelFactory = ((Launch) this.getActivity().getApplication()).mapViewModelFactory();
        this.mapViewModel = new ViewModelProvider(this, mapViewModelFactory).get(MapViewModel.class);
        this.cache = ((Launch)this.getActivity().getApplication()).cache();
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

        // The result of the action is listened in the call back on map ready
        this.updateSearchResultMarker();

        return root;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Listening to the result of my position to show it on the map
        this.cache.getMyPosition().observe(this.getViewLifecycleOwner(), geolocation -> {
            googleMap.setMyLocationEnabled(true);
        });
        this.observeSearchResultMarker(googleMap);
    }

    private void observeSearchResultMarker(GoogleMap googleMap) {
        this.mapViewModel.getSearchResultMarker().observe(this, marker -> {
            googleMap.addMarker(marker);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            googleMap.setOnMarkerClickListener(this);
        });

    }

    private void updateSearchResultMarker() {
        this.cache.getRestaurantIdForSearch().observe(this.getViewLifecycleOwner(), id -> {
            this.mapViewModel.fetchSearchResultToUpdateData(id);
        });
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Restaurant restaurant = this.mapViewModel.getSearchResultMarkerMap().getValue().get(marker.getTitle()).getRestaurant();
        Intent intent = RestaurantDetailsActivityIntentConfig.getIntent(
                this.getContext(),
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhone(),
                restaurant.getWebSite(),
                restaurant.getPhotoUrl()
        );

        this.getContext().startActivity(intent);

        return true;
    }

}
