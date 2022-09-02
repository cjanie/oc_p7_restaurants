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
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.ui.configs.RestaurantDetailsActivityIntentConfig;
import com.android.go4lunch.ui.utils.CenterCamera;
import com.android.go4lunch.ui.viewmodels.Cache;
import com.android.go4lunch.ui.viewmodels.MapSelectedRestaurantsViewModel;
import com.android.go4lunch.ui.viewmodels.MapViewModel;
import com.android.go4lunch.ui.viewmodels.factories.MapSelectedRestaurantsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.MapViewModelFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MapSelectedRestaurantsFragment extends MapGoogleFragment {

    private MapSelectedRestaurantsViewModel mapSelectedRestaurantsViewModel;

    private Cache cache;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        MapSelectedRestaurantsViewModelFactory viewModelFactory = ((Launch) this.getActivity().getApplication()).mapSelectedRestaurantsViewModelFactory();
        this.mapSelectedRestaurantsViewModel = new ViewModelProvider(this, viewModelFactory).get(MapSelectedRestaurantsViewModel.class);
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

        // Call the Map View Model Update Action when My Position is available
        // The result of the action is listened in the call back on map ready
        this.updateRestaurantsMarkersAtInitMyPosition();

        return root;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Listening to the result of my position to show it on the map
        this.cache.getMyPosition().observe(this, geolocation -> {
            if(geolocation != null) {
                googleMap.setMyLocationEnabled(true);
            }
        });
        this.observeRestaurantsMarkers(googleMap);
    }

    private void observeRestaurantsMarkers(GoogleMap googleMap) {
        this.mapSelectedRestaurantsViewModel.getRestaurantsMarkers().observe(this, markers -> {
            if(!markers.isEmpty()) {

                for(MarkerOptions marker: markers) {
                    googleMap.addMarker(marker);
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.getCenterPosition(markers), 10.0f));
                googleMap.setOnMarkerClickListener(this);
            }
        });
    }

    private void updateRestaurantsMarkersAtInitMyPosition() {
        this.cache.getMyPosition().observe(this.getViewLifecycleOwner(), geolocation -> {
            // Action of the Map View Model to update data when geolocation is available;
            this.mapSelectedRestaurantsViewModel.fetchRestaurantsToUpdateRestaurantsMarkersLiveData(
                    geolocation.getLatitude(),
                    geolocation.getLongitude(),
                    1000);
        });
    }

    public LatLng getCenterPosition(List<MarkerOptions> markers) {
        List<LatLng> latLngs = new ArrayList<>();
        for(MarkerOptions marker: markers) {
            latLngs.add(marker.getPosition());
        }
        return new CenterCamera().getCenter(latLngs);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Restaurant restaurant = this.mapSelectedRestaurantsViewModel.getRestaurantsMarkersMap().getValue().get(marker.getTitle()).getRestaurant();
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
