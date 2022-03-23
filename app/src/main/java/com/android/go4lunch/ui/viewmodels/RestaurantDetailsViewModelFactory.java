package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.GetWorkmateByIdUseCase;
import com.android.go4lunch.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.usecases.LikeUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;

public class RestaurantDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final GetSessionUseCase getSessionUseCase;

    private final LikeUseCase likeUseCase;

    private final GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private final IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;

    private final GetWorkmateByIdUseCase getWorkmateByIdUseCase;



    public RestaurantDetailsViewModelFactory(
            GetSessionUseCase getSessionUseCase,
            LikeUseCase likeUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase,
            GetWorkmateByIdUseCase getWorkmateByIdUseCase) {
        this.getSessionUseCase = getSessionUseCase;
        this.likeUseCase = likeUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.isTheCurrentSelectionUseCase = isTheCurrentSelectionUseCase;
        this.getWorkmateByIdUseCase = getWorkmateByIdUseCase;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantDetailsViewModel.class)) {
            return (T) new RestaurantDetailsViewModel(
                    this.getSessionUseCase,
                    this.likeUseCase,
                    this.getRestaurantVisitorsUseCase,
                    this.isTheCurrentSelectionUseCase,
                    this.getWorkmateByIdUseCase
            );
        }
        throw new IllegalArgumentException("RestaurantDetailsViewModelFactory: Unknown ViewModel class");
    }
}
