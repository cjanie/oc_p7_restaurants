package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.ui.viewmodels.WorkmatesViewModel;
import com.android.go4lunch.businesslogic.usecases.workmate.GetWorkmatesUseCase;

public class WorkmatesViewModelFactory implements ViewModelProvider.Factory {

    private final GetWorkmatesUseCase getWorkmatesUseCase;



    public WorkmatesViewModelFactory(
            GetWorkmatesUseCase getWorkmatesUseCase
    ) {
        this.getWorkmatesUseCase = getWorkmatesUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(WorkmatesViewModel.class)) {
            return (T) new WorkmatesViewModel(
                    this.getWorkmatesUseCase
            );
        }
        throw new IllegalArgumentException("WorkmatesViewModelFactory: Unknown ViewModel class");
    }
}
