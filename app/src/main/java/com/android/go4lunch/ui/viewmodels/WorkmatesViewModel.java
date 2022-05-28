package com.android.go4lunch.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.businesslogic.valueobjects.WorkmateValueObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WorkmatesViewModel extends ViewModel {

    private final String TAG = "WORKMATES_VIEW_MODEL";

    private final GetWorkmatesUseCase getWorkmatesUseCase;

    private final MutableLiveData<List<WorkmateValueObject>> workmatesLiveData;

    public WorkmatesViewModel(
            GetWorkmatesUseCase getWorkmatesUseCase
    ) {
        this.getWorkmatesUseCase = getWorkmatesUseCase;
        this.workmatesLiveData = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<WorkmateValueObject>> getWorkmatesLiveData() {
        return this.workmatesLiveData;
    }

    public void fetchWorkmatesToUpdateLiveData() {
        this.getWorkmatesUseCase.handle()
                .subscribe(workmates -> this.workmatesLiveData.postValue(workmates));

    }

}
