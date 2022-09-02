package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.businesslogic.gateways.MyPositionGateway;
import com.android.go4lunch.businesslogic.usecases.GetMyPositionUseCase;
import com.android.go4lunch.businesslogic.usecases.SaveMyPositionUseCase;
import com.android.go4lunch.ui.viewmodels.SharedViewModel;
import com.android.go4lunch.ui.viewmodels.SignInViewModel;

public class SharedViewModelFactory implements ViewModelProvider.Factory {

    private final SaveMyPositionUseCase saveMyPositionUseCase;
    private final GetMyPositionUseCase getMyPositionUseCase;

    public SharedViewModelFactory(
            SaveMyPositionUseCase saveMyPositionUseCase,
            GetMyPositionUseCase getMyPositionUseCase
    ) {
        this.saveMyPositionUseCase = saveMyPositionUseCase;
        this.getMyPositionUseCase = getMyPositionUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel(this.saveMyPositionUseCase, this.getMyPositionUseCase);
        }
        throw new IllegalArgumentException("SharedViewModelFactory: Unknown ViewModel class");
    }
}
