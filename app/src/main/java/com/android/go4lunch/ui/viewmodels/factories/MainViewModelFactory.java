package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.ui.viewmodels.MainViewModel;
import com.android.go4lunch.usecases.GetSessionUseCase;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final GetSessionUseCase getSessionUseCase;

    public MainViewModelFactory(GetSessionUseCase getSessionUseCase) {
        this.getSessionUseCase = getSessionUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(this.getSessionUseCase);
        }
        throw new IllegalArgumentException("MainViewModelFactory: Unknown ViewModel class");
    }
}
