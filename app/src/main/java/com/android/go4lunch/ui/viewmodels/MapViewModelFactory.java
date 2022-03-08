package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.usecases.GetRestaurantsForMap;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    private final GetRestaurantsForMap getRestaurantsForMap;

    public MapViewModelFactory(GetRestaurantsForMap getRestaurantsForMap) {
        this.getRestaurantsForMap = getRestaurantsForMap;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(this.getRestaurantsForMap);
        }
        throw new IllegalArgumentException("MapViewModelFactory: Unknown ViewModel class");
    }
}
