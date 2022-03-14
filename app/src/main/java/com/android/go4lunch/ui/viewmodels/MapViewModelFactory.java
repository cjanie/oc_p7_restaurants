package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.usecases.GetRestaurantsForMapUseCase;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    private final GetRestaurantsForMapUseCase getRestaurantsForMapUseCase;

    public MapViewModelFactory(GetRestaurantsForMapUseCase getRestaurantsForMapUseCase) {
        this.getRestaurantsForMapUseCase = getRestaurantsForMapUseCase;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(this.getRestaurantsForMapUseCase);
        }
        throw new IllegalArgumentException("MapViewModelFactory: Unknown ViewModel class");
    }
}
