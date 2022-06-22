package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.businesslogic.usecases.GoForLunchUseCase;
import com.android.go4lunch.businesslogic.usecases.IsInFavoritesRestaurantsUseCase;
import com.android.go4lunch.businesslogic.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.businesslogic.usecases.AddLikeUseCase;

public class RestaurantDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final GoForLunchUseCase goForLunchUseCase;

    private final GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private final IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;

    private final AddLikeUseCase addLikeUseCase;

    private final IsInFavoritesRestaurantsUseCase isInFavoritesRestaurantsUseCase;


    public RestaurantDetailsViewModelFactory(
            GoForLunchUseCase goForLunchUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase,
            AddLikeUseCase addLikeUseCase,
            IsInFavoritesRestaurantsUseCase isInFavoritesRestaurantsUseCase
    ) {
        this.goForLunchUseCase = goForLunchUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.isTheCurrentSelectionUseCase = isTheCurrentSelectionUseCase;
        this.addLikeUseCase = addLikeUseCase;
        this.isInFavoritesRestaurantsUseCase = isInFavoritesRestaurantsUseCase;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantDetailsViewModel.class)) {
            return (T) new RestaurantDetailsViewModel(
                    this.goForLunchUseCase,
                    this.getRestaurantVisitorsUseCase,
                    this.isTheCurrentSelectionUseCase,
                    this.addLikeUseCase,
                    this.isInFavoritesRestaurantsUseCase
            );
        }
        throw new IllegalArgumentException("RestaurantDetailsViewModelFactory: Unknown ViewModel class");
    }
}
