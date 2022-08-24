package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.usecases.GetMyLunchUseCase;
import com.android.go4lunch.businesslogic.usecases.GetSessionUseCase;
import com.android.go4lunch.businesslogic.usecases.SignOutUseCase;
import com.android.go4lunch.businesslogic.exceptions.NoWorkmateForSessionException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SessionViewModel extends ViewModel {

    private GetSessionUseCase getSessionUseCase;

    private SignOutUseCase signOutUseCase;

    private GetMyLunchUseCase getMyLunchUseCase;

    private MutableLiveData<Workmate> sessionWorkmate;

    private MutableLiveData<Restaurant> myLunch;

    public SessionViewModel(
            GetSessionUseCase getSessionUseCase,
            SignOutUseCase signOutUseCase,
            GetMyLunchUseCase getMyLunchUseCase
    ) {
        this.getSessionUseCase = getSessionUseCase;
        this.signOutUseCase = signOutUseCase;
        this.getMyLunchUseCase = getMyLunchUseCase;
        this.sessionWorkmate = new MutableLiveData<>();
        this.myLunch = new MutableLiveData<>();
    }

    public void fetchSessionToUpdateLiveData() {
        this.getSessionUseCase.handle()
                .subscribe(
                        sessionWorkmate -> {
                            if(sessionWorkmate != null)
                                this.sessionWorkmate.postValue(sessionWorkmate);
                        },
                        error -> error.printStackTrace()
                );
    }

    public LiveData<Workmate> getSession() throws NoWorkmateForSessionException {
        return this.sessionWorkmate;
    }

    public void fetchMyLunchToUpdateLiveData() {
        this.getMyLunchUseCase.handle()
                .subscribe(
                        restaurant -> this.myLunch.postValue(restaurant),
                        error -> error.printStackTrace()
                );
    }


    public LiveData<Restaurant> getMyLunch() {
        return this.myLunch;
    }

    public void signOut() {
        this.signOutUseCase.handle();
    }
}
