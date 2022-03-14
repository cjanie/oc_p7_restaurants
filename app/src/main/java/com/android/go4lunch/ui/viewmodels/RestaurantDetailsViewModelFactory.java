package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.usecases.ToggleSelectionUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;

public class RestaurantDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final ToggleSelectionUseCase toggleSelectionUseCase;

    private final GetSessionUseCase getSessionUseCase;

    public RestaurantDetailsViewModelFactory(ToggleSelectionUseCase toggleSelectionUseCase, GetSessionUseCase getSessionUseCase) {
        this.toggleSelectionUseCase = toggleSelectionUseCase;
        this.getSessionUseCase = getSessionUseCase;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantDetailsViewModel.class)) {
            return (T) new RestaurantDetailsViewModel(this.toggleSelectionUseCase, this.getSessionUseCase);
        }
        throw new IllegalArgumentException("RestaurantDetailsViewModelFactory: Unknown ViewModel class");
    }
}
