package com.android.go4lunch.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.usecases.GetWorkmatesUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class WorkmatesViewModel extends ViewModel {

    private final String TAG = "WORKMATES_VIEW_MODEL";

    private final GetWorkmatesUseCase getWorkmatesUseCase;

    private final MutableLiveData<List<Workmate>> workmatesLiveData;

    // For observing observable data
    private Disposable disposable;

    public WorkmatesViewModel(
            GetWorkmatesUseCase getWorkmatesUseCase
    ) {
        this.getWorkmatesUseCase = getWorkmatesUseCase;
        this.workmatesLiveData = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Workmate>> getWorkmatesLiveData() {
        this.updateWorkmatesLiveData();
        return this.workmatesLiveData;
    }

    public void updateWorkmatesLiveData() {
        this.disposable = this.getWorkmatesUseCase.handle().subscribeWith(
                new DisposableObserver<List<Workmate>>() {
                    @Override
                    public void onNext(List<Workmate> workmates) {
                        Log.d(TAG, "updateWorkmatesLiveData workmates size: " + workmates.size());
                        workmatesLiveData.postValue(workmates);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "updateWorkmatesLiveData completed");
                    }
                }
                );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(this.disposable != null && !this.disposable.isDisposed())
            this.disposable.dispose();
    }

}
