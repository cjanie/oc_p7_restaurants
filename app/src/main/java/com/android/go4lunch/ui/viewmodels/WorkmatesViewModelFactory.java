package com.android.go4lunch.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;

public class WorkmatesViewModelFactory implements ViewModelProvider.Factory {

    private final GetWorkmateSelectionUseCase getWorkmateSelectionUseCase;

    public WorkmatesViewModelFactory(GetWorkmateSelectionUseCase getWorkmateSelectionUseCase) {
        this.getWorkmateSelectionUseCase = getWorkmateSelectionUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(WorkmatesViewModel.class)) {
            return (T) new WorkmatesViewModel(this.getWorkmateSelectionUseCase);
        }
        throw new IllegalArgumentException("WorkmatesViewModelFactory: Unknown ViewModel class");
    }
}
