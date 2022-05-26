package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.businesslogic.usecases.GetWorkmateByIdUseCase;
import com.android.go4lunch.businesslogic.usecases.GoForLunchUseCase;
import com.android.go4lunch.businesslogic.usecases.GetSessionUseCase;
import com.android.go4lunch.businesslogic.usecases.IsOneOfTheUserFavoriteRestaurantsUseCase;
import com.android.go4lunch.businesslogic.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.businesslogic.usecases.AddLikeUseCase;

public class RestaurantDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final GetSessionUseCase getSessionUseCase;

    private final GoForLunchUseCase goForLunchUseCase;

    private final GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private final GetWorkmateByIdUseCase getWorkmateByIdUseCase;

    private final IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;

    private final AddLikeUseCase addLikeUseCase;

    private final IsOneOfTheUserFavoriteRestaurantsUseCase isOneOfTheUserFavoriteRestaurantsUseCase;


    public RestaurantDetailsViewModelFactory(
            GetSessionUseCase getSessionUseCase,
            GoForLunchUseCase goForLunchUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            GetWorkmateByIdUseCase getWorkmateByIdUseCase,
            IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase,
            AddLikeUseCase addLikeUseCase,
            IsOneOfTheUserFavoriteRestaurantsUseCase isOneOfTheUserFavoriteRestaurantsUseCase
    ) {
        this.getSessionUseCase = getSessionUseCase;
        this.goForLunchUseCase = goForLunchUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.getWorkmateByIdUseCase = getWorkmateByIdUseCase;
        this.isTheCurrentSelectionUseCase = isTheCurrentSelectionUseCase;
        this.addLikeUseCase = addLikeUseCase;
        this.isOneOfTheUserFavoriteRestaurantsUseCase = isOneOfTheUserFavoriteRestaurantsUseCase;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantDetailsViewModel.class)) {
            return (T) new RestaurantDetailsViewModel(
                    this.getSessionUseCase,
                    this.goForLunchUseCase,
                    this.getRestaurantVisitorsUseCase,
                    this.getWorkmateByIdUseCase,
                    this.isTheCurrentSelectionUseCase,
                    this.addLikeUseCase,
                    this.isOneOfTheUserFavoriteRestaurantsUseCase
            );
        }
        throw new IllegalArgumentException("RestaurantDetailsViewModelFactory: Unknown ViewModel class");
    }
}
