package com.android.go4lunch.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantsForMapUseCase;
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
    private GetRestaurantsForMapUseCase getRestaurantsForMapUseCase;

    // Data
    private final MutableLiveData<List<MarkerOptions>> markers;

    // For observing observable data
    private Disposable disposable;

    // Constructor
    public MapViewModel(GetRestaurantsForMapUseCase getRestaurantsForMapUseCase) {
        this.getRestaurantsForMapUseCase = getRestaurantsForMapUseCase;
        this.markers = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<MarkerOptions>> getMarkers(Double myLatitude, Double myLongitude, int radius) {
        this.setMarkers(
                this.getRestaurantsMarkers(myLatitude, myLongitude, radius)
        );
        return this.markers;
    }


    private void setMarkers(Observable<List<MarkerOptions>> observableMarkers) {
        this.disposable = observableMarkers.subscribeWith(new DisposableObserver<List<MarkerOptions>>() {
            @Override
            public void onNext(@NonNull List<MarkerOptions> markersOptions) {
                markers.setValue(markersOptions);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public Observable<List<MarkerOptions>> getRestaurantsMarkers(Double myLatitude, Double myLongitude, int radius) {
        return this.getRestaurantsForMapUseCase.handle(myLatitude, myLongitude, radius).map(restaurants -> {
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
            return markersOptions;
        });
    }

}
