package com.android.go4lunch.ui.viewmodels.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.ui.viewmodels.WorkmatesViewModel;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;

public class WorkmatesViewModelFactory implements ViewModelProvider.Factory {

    private final GetWorkmatesUseCase getWorkmatesUseCase;

    private final GetWorkmateSelectionUseCase getWorkmateSelectionUseCase;



    public WorkmatesViewModelFactory(
            GetWorkmatesUseCase getWorkmatesUseCase,
            GetWorkmateSelectionUseCase getWorkmateSelectionUseCase

    ) {
        this.getWorkmatesUseCase = getWorkmatesUseCase;
        this.getWorkmateSelectionUseCase = getWorkmateSelectionUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(WorkmatesViewModel.class)) {
            return (T) new WorkmatesViewModel(
                    this.getWorkmatesUseCase,
                    this.getWorkmateSelectionUseCase
            );
        }
        throw new IllegalArgumentException("WorkmatesViewModelFactory: Unknown ViewModel class");
    }
}
