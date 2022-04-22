package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.ui.viewmodels.SignInViewModel;
import com.android.go4lunch.usecases.SaveWorkmateUseCase;

public class SignInViewModelFactory implements ViewModelProvider.Factory {

    private final SaveWorkmateUseCase saveWorkmateUseCase;

    public SignInViewModelFactory(SaveWorkmateUseCase saveWorkmateUseCase) {
        this.saveWorkmateUseCase = saveWorkmateUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SignInViewModel.class)) {
            return (T) new SignInViewModel(
                    this.saveWorkmateUseCase
            );
        }
        throw new IllegalArgumentException("SignInViewModelFactory: Unknown ViewModel class");
    }
}
