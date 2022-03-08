package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.usecases.GetRestaurantsForList;

public class RestaurantsViewModelFactory implements ViewModelProvider.Factory {

    private final GetRestaurantsForList getRestaurantsForList;

    public RestaurantsViewModelFactory(GetRestaurantsForList getRestaurantsForList) {
        this.getRestaurantsForList = getRestaurantsForList;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantsViewModel.class)) {
            return (T) new RestaurantsViewModel(this.getRestaurantsForList);
        }
        throw new IllegalArgumentException("RestaurantsViewModelFactory: Unknown ViewModel class");
    }
}
