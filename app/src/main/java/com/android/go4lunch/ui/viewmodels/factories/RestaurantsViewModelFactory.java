package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.businesslogic.usecases.UpdateRestaurantWithDistanceUseCase;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.ui.viewmodels.RestaurantsViewModel;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantsForListUseCase;

public class RestaurantsViewModelFactory implements ViewModelProvider.Factory {

    private final GetRestaurantsForListUseCase getRestaurantsForListUseCase;

    private final UpdateRestaurantWithDistanceUseCase updateRestaurantWithDistanceUseCase;

    private final TimeProvider timeProvider;

    private final DateProvider dateProvider;

    public RestaurantsViewModelFactory(
            GetRestaurantsForListUseCase getRestaurantsForListUseCase,
            UpdateRestaurantWithDistanceUseCase updateRestaurantWithDistanceUseCase,
            TimeProvider timeProvider,
            DateProvider dateProvider
    ) {
        this.getRestaurantsForListUseCase = getRestaurantsForListUseCase;
        this.updateRestaurantWithDistanceUseCase = updateRestaurantWithDistanceUseCase;
        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantsViewModel.class)) {
            return (T) new RestaurantsViewModel(
                    this.getRestaurantsForListUseCase,
                    this.updateRestaurantWithDistanceUseCase,
                    this.timeProvider,
                    this.dateProvider
            );
        }
        throw new IllegalArgumentException("RestaurantsViewModelFactory: Unknown ViewModel class");
    }
}
