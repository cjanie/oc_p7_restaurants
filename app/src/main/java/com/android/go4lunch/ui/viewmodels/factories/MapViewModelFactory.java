package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.ui.viewmodels.MapViewModel;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantsNearbyUseCase;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    private final GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;

    public MapViewModelFactory(GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase) {
        this.getRestaurantsNearbyUseCase = getRestaurantsNearbyUseCase;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(this.getRestaurantsNearbyUseCase);
        }
        throw new IllegalArgumentException("MapViewModelFactory: Unknown ViewModel class");
    }
}
