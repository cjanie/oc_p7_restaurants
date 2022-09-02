package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.businesslogic.usecases.restaurant.FilterSelectedRestaurantsUseCase;
import com.android.go4lunch.ui.viewmodels.MapSelectedRestaurantsViewModel;

public class MapSelectedRestaurantsViewModelFactory implements ViewModelProvider.Factory {

    private FilterSelectedRestaurantsUseCase filterSelectedRestaurantsUseCase;

    public MapSelectedRestaurantsViewModelFactory(FilterSelectedRestaurantsUseCase filterSelectedRestaurantsUseCase) {
        this.filterSelectedRestaurantsUseCase = filterSelectedRestaurantsUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MapSelectedRestaurantsViewModel.class)) {
            return (T) new MapSelectedRestaurantsViewModel(
                    this.filterSelectedRestaurantsUseCase
            );
        }
        throw new IllegalArgumentException("MapSelectedRestaurantsViewModelFactory: Unknown ViewModel class");
    }
}
