package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetSessionUseCase;
import com.android.go4lunch.usecases.SignOutUseCase;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.usecases.models.WorkmateModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainViewModel extends ViewModel {

    private GetSessionUseCase getSessionUseCase;

    private SignOutUseCase signOutUseCase;

    private MutableLiveData<Workmate> sessionWorkmate;

    public MainViewModel(
            GetSessionUseCase getSessionUseCase,
            SignOutUseCase signOutUseCase) {
        this.getSessionUseCase = getSessionUseCase;
        this.signOutUseCase = signOutUseCase;
        this.sessionWorkmate = new MutableLiveData<>();
    }

    public LiveData<Workmate> getSession() throws NoWorkmateForSessionException {

        Disposable disposable = this.getSessionUseCase.handle().subscribe(
                sessionWorkmate -> {
                    if(sessionWorkmate != null)
                        this.sessionWorkmate.setValue(sessionWorkmate);
                },
                Throwable::printStackTrace
        );
        return this.sessionWorkmate;
    }

    public void signOut() {
        this.signOutUseCase.handle();
    }
}
