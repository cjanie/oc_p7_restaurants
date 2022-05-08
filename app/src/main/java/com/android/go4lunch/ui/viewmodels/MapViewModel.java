package com.android.go4lunch.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.usecases.GetRestaurantsForMapUseCase;
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

    // For stream data (Observable)
    private Disposable disposable;

    // Constructor
    public MapViewModel(GetRestaurantsForMapUseCase getRestaurantsForMapUseCase) {
        this.getRestaurantsForMapUseCase = getRestaurantsForMapUseCase;
        this.markers = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<MarkerOptions>> getMarkers(Double myLatitude, Double myLongitude, int radius) {
        this.setMarkers(
                this.getRestaurantsForMapUseCase.getRestaurantsMarkers(new Geolocation(myLatitude, myLongitude), radius)
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

}
