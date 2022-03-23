package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.usecases.GetRestaurantsForListUseCase;
import com.android.go4lunch.usecases.decorators.TimeInfoDecorator;

public class RestaurantsViewModelFactory implements ViewModelProvider.Factory {

    private final GetRestaurantsForListUseCase getRestaurantsForListUseCase;

    private final TimeProvider timeProvider;

    private final DateProvider dateProvider;

    public RestaurantsViewModelFactory(
            GetRestaurantsForListUseCase getRestaurantsForListUseCase,
            TimeProvider timeProvider,
            DateProvider dateProvider
    ) {
        this.getRestaurantsForListUseCase = getRestaurantsForListUseCase;
        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantsViewModel.class)) {
            return (T) new RestaurantsViewModel(
                    this.getRestaurantsForListUseCase,
                    this.timeProvider,
                    this.dateProvider
            );
        }
        throw new IllegalArgumentException("RestaurantsViewModelFactory: Unknown ViewModel class");
    }
}
