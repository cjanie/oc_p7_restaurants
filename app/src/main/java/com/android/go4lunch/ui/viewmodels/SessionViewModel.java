package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.usecases.GetSessionUseCase;
import com.android.go4lunch.businesslogic.usecases.SignOutUseCase;
import com.android.go4lunch.businesslogic.exceptions.NoWorkmateForSessionException;

import io.reactivex.schedulers.Schedulers;

public class SessionViewModel extends ViewModel {

    private GetSessionUseCase getSessionUseCase;

    private SignOutUseCase signOutUseCase;

    private MutableLiveData<Workmate> sessionWorkmate;

    public SessionViewModel(
            GetSessionUseCase getSessionUseCase,
            SignOutUseCase signOutUseCase) {
        this.getSessionUseCase = getSessionUseCase;
        this.signOutUseCase = signOutUseCase;
        this.sessionWorkmate = new MutableLiveData<>();
    }

    public LiveData<Workmate> getSession() throws NoWorkmateForSessionException {

        this.getSessionUseCase.handle().subscribeOn(Schedulers.io())
                .subscribe(
                sessionWorkmate -> {
                    if(sessionWorkmate != null)
                        this.sessionWorkmate.postValue(sessionWorkmate);
                },
                Throwable::printStackTrace
        );
        return this.sessionWorkmate;
    }

    public void signOut() {
        this.signOutUseCase.handle();
    }
}