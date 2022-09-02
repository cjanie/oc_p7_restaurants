package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.businesslogic.usecases.GetDistanceFromMyPositionToRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.GetNumberOfLikesPerRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.FilterSelectedRestaurantsUseCase;
import com.android.go4lunch.ui.viewmodels.SelectedRestaurantsViewModel;

public class SelectedRestaurantsViewModelFactory implements ViewModelProvider.Factory {

    FilterSelectedRestaurantsUseCase filterSelectedRestaurantsUseCase;
    GetNumberOfLikesPerRestaurantUseCase likeUseCase;
    GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase;

    public SelectedRestaurantsViewModelFactory(FilterSelectedRestaurantsUseCase filterSelectedRestaurantsUseCase, GetNumberOfLikesPerRestaurantUseCase likeUseCase, GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase) {
        this.filterSelectedRestaurantsUseCase = filterSelectedRestaurantsUseCase;
        this.likeUseCase = likeUseCase;
        this.distanceUseCase = distanceUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SelectedRestaurantsViewModel.class)) {
            return (T) new SelectedRestaurantsViewModel(
                    this.filterSelectedRestaurantsUseCase,
                    this.likeUseCase,
                    this.distanceUseCase
            );
        }
        throw new IllegalArgumentException("SelectedRestaurantsViewModelFactory: Unknown ViewModel class");
    }
}
