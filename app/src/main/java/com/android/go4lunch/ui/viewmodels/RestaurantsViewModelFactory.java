package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.usecases.GetRestaurantsForListUseCase;

public class RestaurantsViewModelFactory implements ViewModelProvider.Factory {

    private final GetRestaurantsForListUseCase getRestaurantsForListUseCase;

    public RestaurantsViewModelFactory(GetRestaurantsForListUseCase getRestaurantsForListUseCase) {
        this.getRestaurantsForListUseCase = getRestaurantsForListUseCase;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantsViewModel.class)) {
            return (T) new RestaurantsViewModel(this.getRestaurantsForListUseCase);
        }
        throw new IllegalArgumentException("RestaurantsViewModelFactory: Unknown ViewModel class");
    }
}
