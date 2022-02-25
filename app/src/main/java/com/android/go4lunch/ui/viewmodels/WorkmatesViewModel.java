package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.gateways_impl.WorkmateRepository;
import com.android.go4lunch.usecases.GetWorkmates;
import com.android.go4lunch.usecases.models_vo.WorkmateVO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class WorkmatesViewModel extends ViewModel {

    private GetWorkmates getWorkmates;

    private MutableLiveData<List<WorkmateVO>> workmates;

    private Disposable disposable;

    public WorkmatesViewModel() {
        this.getWorkmates = new GetWorkmates(
                new WorkmateRepository()
        );
        this.workmates = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<WorkmateVO>> list() {
        this.setWorkmates(this.getWorkmates.list());
        return this.workmates;
    }

    private void setWorkmates(Observable<List<WorkmateVO>> observableWorkmates) {
        this.disposable = observableWorkmates.subscribeWith(new DisposableObserver<List<WorkmateVO>>() {
            @Override
            public void onNext(@NonNull List<WorkmateVO> list) {
                workmates.setValue(list);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(this.disposable != null && !this.disposable.isDisposed())
            this.disposable.dispose();
    }
}
