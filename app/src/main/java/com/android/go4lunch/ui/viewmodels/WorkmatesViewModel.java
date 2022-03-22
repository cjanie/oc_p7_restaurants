package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.gateways_impl.WorkmateGatewayImpl;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.usecases.models_vo.WorkmateVO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class WorkmatesViewModel extends ViewModel {

    private GetWorkmatesUseCase getWorkmatesUseCase;

    private GetWorkmateSelectionUseCase getWorkmateSelectionUseCase;

    private MutableLiveData<List<WorkmateVO>> workmates;

    private Disposable disposable;

    public WorkmatesViewModel(GetWorkmateSelectionUseCase getWorkmateSelectionUseCase) {
        this.getWorkmatesUseCase = new GetWorkmatesUseCase(
                new WorkmateGatewayImpl()
        );
        this.getWorkmateSelectionUseCase = getWorkmateSelectionUseCase;
        this.workmates = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<WorkmateVO>> list() {
        this.setWorkmates(this.getWorkmatesUseCase.list());
        return this.workmates;
    }

    private void setWorkmates(Observable<List<WorkmateVO>> observableWorkmates) {
        this.disposable = observableWorkmates.subscribeWith(new DisposableObserver<List<WorkmateVO>>() {
            @Override
            public void onNext(@NonNull List<WorkmateVO> list) {
                for(WorkmateVO w: list) {
                    w = decorWorkmate(w);
                }
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

    private WorkmateVO decorWorkmate(WorkmateVO workmateVO) {
        Observable<Selection> selectionObservable = this.getWorkmateSelectionUseCase.handle(workmateVO.getWorkmate().getId());
        if(selectionObservable != null) {
            List<Selection> selectionResults = new ArrayList<>();
            selectionObservable.subscribe(selectionResults::add);
            workmateVO.setSelection(new Restaurant(selectionResults.get(0).getRestaurantName(), "address"));
        }

        return workmateVO;

    }
}
