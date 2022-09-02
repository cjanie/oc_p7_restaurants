package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.businesslogic.usecases.restaurant.SearchRestaurantUseCase;
import com.android.go4lunch.ui.viewmodels.MapViewModel;
import com.android.go4lunch.businesslogic.usecases.restaurant.GetRestaurantsNearbyUseCase;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    private final GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;

    private final SearchRestaurantUseCase searchRestaurantUseCase;

    public MapViewModelFactory(GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase, SearchRestaurantUseCase searchRestaurantUseCase) {
        this.getRestaurantsNearbyUseCase = getRestaurantsNearbyUseCase;
        this.searchRestaurantUseCase = searchRestaurantUseCase;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(this.getRestaurantsNearbyUseCase, this.searchRestaurantUseCase);
        }
        throw new IllegalArgumentException("MapViewModelFactory: Unknown ViewModel class");
    }
}
