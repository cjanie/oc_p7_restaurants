package com.android.go4lunch.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantsNearbyUseCase;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MapViewModel extends ViewModel {

    private String TAG = "MAP VIEW MODEL";

    // Use Case
    private GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;

    // Data
    private final MutableLiveData<List<MarkerOptions>> markers;

    // Constructor
    public MapViewModel(GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase) {
        this.getRestaurantsNearbyUseCase = getRestaurantsNearbyUseCase;
        this.markers = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<MarkerOptions>> getRestaurantsMarkers() {
        return this.markers;
    }

    public void fetchRestaurantsMarkers(Double myLatitude, Double myLongitude, int radius) {
        this.getRestaurantsNearbyUseCase.handle(myLatitude, myLongitude, radius).subscribe(restaurants -> {
            List<MarkerOptions> markersOptions = new ArrayList<>();
            if(!restaurants.isEmpty()) {
                for(Restaurant restaurant: restaurants) {
                    if(restaurant.getGeolocation() != null) {
                        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(
                                restaurant.getGeolocation().getLatitude(),
                                restaurant.getGeolocation().getLongitude())
                        ).title(restaurant.getName());
                        markersOptions.add(markerOptions);
                    }
                }
            }
            markers.postValue(markersOptions);

        });
    }

}
