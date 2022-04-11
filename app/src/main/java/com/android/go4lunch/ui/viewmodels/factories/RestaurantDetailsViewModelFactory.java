package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.GetWorkmateByIdUseCase;
import com.android.go4lunch.usecases.LikeUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;

public class RestaurantDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final GetSessionUseCase getSessionUseCase;

    private final LikeUseCase likeUseCase;

    private final GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private final GetWorkmateByIdUseCase getWorkmateByIdUseCase;



    public RestaurantDetailsViewModelFactory(
            GetSessionUseCase getSessionUseCase,
            LikeUseCase likeUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            GetWorkmateByIdUseCase getWorkmateByIdUseCase) {
        this.getSessionUseCase = getSessionUseCase;
        this.likeUseCase = likeUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
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
                    this.getWorkmateByIdUseCase
            );
        }
        throw new IllegalArgumentException("RestaurantDetailsViewModelFactory: Unknown ViewModel class");
    }
}
