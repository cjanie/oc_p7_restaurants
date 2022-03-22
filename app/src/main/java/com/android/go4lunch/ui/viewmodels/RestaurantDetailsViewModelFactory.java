package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.usecases.LikeForLunchUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;

public class RestaurantDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final GetSessionUseCase getSessionUseCase;

    private final LikeForLunchUseCase likeForLunchUseCase;

    private final GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private final IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;



    public RestaurantDetailsViewModelFactory(
            GetSessionUseCase getSessionUseCase,
            LikeForLunchUseCase likeForLunchUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase) {
        this.getSessionUseCase = getSessionUseCase;
        this.likeForLunchUseCase = likeForLunchUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.isTheCurrentSelectionUseCase = isTheCurrentSelectionUseCase;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantDetailsViewModel.class)) {
            return (T) new RestaurantDetailsViewModel(
                    this.getSessionUseCase,
                    this.likeForLunchUseCase,
                    this.getRestaurantVisitorsUseCase,
                    this.isTheCurrentSelectionUseCase
            );
        }
        throw new IllegalArgumentException("RestaurantDetailsViewModelFactory: Unknown ViewModel class");
    }
}
