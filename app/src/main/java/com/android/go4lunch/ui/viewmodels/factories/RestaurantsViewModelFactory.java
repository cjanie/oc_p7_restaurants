package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.businesslogic.usecases.GetDistanceFromMyPositionToRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.GetNumberOfLikesPerRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.GetRestaurantsNearbyUseCase;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.ui.viewmodels.RestaurantsViewModel;

public class RestaurantsViewModelFactory implements ViewModelProvider.Factory {

    private final GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;

    private final GetNumberOfLikesPerRestaurantUseCase likeUseCase;

    private final GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase;

    private final TimeProvider timeProvider;

    private final DateProvider dateProvider;

    public RestaurantsViewModelFactory(
            GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase,
            GetNumberOfLikesPerRestaurantUseCase likeUseCase,
            GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase,
            TimeProvider timeProvider,
            DateProvider dateProvider
    ) {
        this.getRestaurantsNearbyUseCase = getRestaurantsNearbyUseCase;
        this.likeUseCase = likeUseCase;
        this.distanceUseCase = distanceUseCase;
        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantsViewModel.class)) {
            return (T) new RestaurantsViewModel(
                    this.getRestaurantsNearbyUseCase,
                    this.likeUseCase,
                    this.distanceUseCase,
                    this.timeProvider,
                    this.dateProvider
            );
        }
        throw new IllegalArgumentException("RestaurantsViewModelFactory: Unknown ViewModel class");
    }
}
