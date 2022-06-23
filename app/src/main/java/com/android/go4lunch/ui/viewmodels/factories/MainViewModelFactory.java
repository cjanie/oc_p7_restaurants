package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.businesslogic.usecases.GetMyLunchUseCase;
import com.android.go4lunch.ui.viewmodels.SessionViewModel;
import com.android.go4lunch.businesslogic.usecases.GetSessionUseCase;
import com.android.go4lunch.businesslogic.usecases.SignOutUseCase;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final GetSessionUseCase getSessionUseCase;

    private final SignOutUseCase signOutUseCase;

    private final GetMyLunchUseCase getMyLunchUseCase;

    public MainViewModelFactory(
            GetSessionUseCase getSessionUseCase,
            SignOutUseCase signOutUseCase,
            GetMyLunchUseCase getMyLunchUseCase
    ) {
        this.getSessionUseCase = getSessionUseCase;
        this.signOutUseCase = signOutUseCase;
        this.getMyLunchUseCase = getMyLunchUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SessionViewModel.class)) {
            return (T) new SessionViewModel(
                    this.getSessionUseCase,
                    this.signOutUseCase,
                    this.getMyLunchUseCase
            );
        }
        throw new IllegalArgumentException("MainViewModelFactory: Unknown ViewModel class");
    }
}
