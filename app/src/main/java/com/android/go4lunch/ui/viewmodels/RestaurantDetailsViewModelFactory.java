package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.usecases.ToggleSelection;
import com.android.go4lunch.usecases.GetSession;

public class RestaurantDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final ToggleSelection toggleSelection;

    private final GetSession getSession;

    public RestaurantDetailsViewModelFactory(ToggleSelection toggleSelection, GetSession getSession) {
        this.toggleSelection = toggleSelection;
        this.getSession = getSession;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantDetailsViewModel.class)) {
            return (T) new RestaurantDetailsViewModel(this.toggleSelection, this.getSession);
        }
        throw new IllegalArgumentException("RestaurantDetailsViewModelFactory: Unknown ViewModel class");
    }
}
