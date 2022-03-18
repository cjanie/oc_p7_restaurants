package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.ToggleSelectionUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;

public class RestaurantDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final ToggleSelectionUseCase toggleSelectionUseCase;

    private final GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;



    public RestaurantDetailsViewModelFactory(
            ToggleSelectionUseCase toggleSelectionUseCase,
            GetSessionUseCase getSessionUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase) {
        this.toggleSelectionUseCase = toggleSelectionUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantDetailsViewModel.class)) {
            return (T) new RestaurantDetailsViewModel(
                    this.toggleSelectionUseCase,
                    this.getRestaurantVisitorsUseCase
            );
        }
        throw new IllegalArgumentException("RestaurantDetailsViewModelFactory: Unknown ViewModel class");
    }
}
