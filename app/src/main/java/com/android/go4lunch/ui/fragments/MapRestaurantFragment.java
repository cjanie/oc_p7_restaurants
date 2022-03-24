package com.android.go4lunch.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;

import com.android.go4lunch.ui.events.InitMyPositionEvent;
import com.android.go4lunch.ui.viewmodels.MapViewModel;
import com.android.go4lunch.ui.viewmodels.MapViewModelFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapRestaurantFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mapViewModel;

    private MutableLiveData<List<MarkerOptions>> markers;

    @BindView(R.id.map_view)
    MapView mapView;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        MapViewModelFactory mapViewModelFactory = ((Launch) this.getActivity().getApplication()).mapViewModelFactory();
        this.mapViewModel = new ViewModelProvider(this, mapViewModelFactory).get(MapViewModel.class);
        this.markers = new MutableLiveData<>(new ArrayList<>());

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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Show my Position on the map
        googleMap.setMyLocationEnabled(true);
        // Add markers
        this.markers.observe(this, markers -> {
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

    @Subscribe
    public void setMarkersAtInitMyPosition(InitMyPositionEvent event) {
        this.mapViewModel.getMarkers(event.getLatitude(), event.getLongitude(), 1000)
                .observe(this, markers -> {
                   this.markers.setValue(markers);
                });
    }

}
