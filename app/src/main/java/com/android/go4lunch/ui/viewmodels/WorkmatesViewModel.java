package com.android.go4lunch.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.gateways_impl.Mock;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetRestaurantByIdUseCase;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.usecases.exceptions.NotFoundException;
import com.android.go4lunch.usecases.models.WorkmateModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class WorkmatesViewModel extends ViewModel {

    private final String TAG = "WORKMATES_VIEW_MODEL";

    private final GetWorkmatesUseCase getWorkmatesUseCase;

    private final GetWorkmateSelectionUseCase getWorkmateSelectionUseCase;

    private final GetRestaurantByIdUseCase getRestaurantByIdUseCase;

    private final MutableLiveData<List<WorkmateModel>> workmates;

    private Disposable disposable;

    public WorkmatesViewModel(
            GetWorkmatesUseCase getWorkmatesUseCase,
            GetWorkmateSelectionUseCase getWorkmateSelectionUseCase,
            GetRestaurantByIdUseCase getRestaurantByIdUseCase

    ) {
        this.getWorkmatesUseCase = getWorkmatesUseCase;
        this.getWorkmateSelectionUseCase = getWorkmateSelectionUseCase;
        this.getRestaurantByIdUseCase = getRestaurantByIdUseCase;

        this.workmates = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<WorkmateModel>> getWorkmates() {

        List<Workmate> workmatesResults = new ArrayList<>();
        this.getWorkmatesUseCase.handle().subscribe(workmatesResults::addAll);

        List<WorkmateModel> workmateModels = new ArrayList<>();
        if(!workmatesResults.isEmpty()) {
            for(Workmate w: workmatesResults) {
                WorkmateModel workmateModel = new WorkmateModel(w);
                try {
                    List<String> selectionResults = new ArrayList<>();
                    this.getWorkmateSelectionUseCase.handle(w.getId()).subscribe(selectionResults::add);

                    List<Restaurant> restaurantResults = new ArrayList<>();
                    this.getRestaurantByIdUseCase.handle(selectionResults.get(0)).subscribe(restaurantResults::add);

                    workmateModel.setSelection(restaurantResults.get(0));

                } catch (NotFoundException e) {
                    Log.e(this.TAG, "getWorkmateSelectionUseCase: " + e.getClass().getName());
                }

                workmateModels.add(workmateModel);
            }
        }
        this.setWorkmates(Observable.just(workmateModels));

        return this.workmates;
    }

    private void setWorkmates(Observable<List<WorkmateModel>> workmateModelsObservable) {
        this.disposable = workmateModelsObservable.subscribeWith(new DisposableObserver<List<WorkmateModel>>() {
            @Override
            public void onNext(@NonNull List<WorkmateModel> workmates) {
                WorkmatesViewModel.this.workmates.postValue(workmates);
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

    /*

    public LiveData<List<WorkmateModel>> list() {
        this.workmates.postValue(this.getWorkmates());
        return this.workmates;
    }

    private void fetchWorkmates() {
        List<Workmate> workmatesResults = new ArrayList<>();
        this.getWorkmatesUseCase.handle().subscribe(workmatesResults::addAll);

        List<WorkmateModel> workmatesModels = new ArrayList<>();
        if(!workmatesResults.isEmpty()) {
            for(Workmate w: workmatesResults) {
                //WorkmateModel workmateModel = this.decorWorkmate(w);
                WorkmateModel workmateModel = new WorkmateModel(w);
                workmatesModels.add(workmateModel);
            }
        }
        this.workmates.setValue(workmatesModels);
    }

    public List<WorkmateModel> getWorkmates() {
        List<Workmate> workmatesResults = new ArrayList<>();
        this.getWorkmatesUseCase.handle().subscribe(workmatesResults::addAll);
        List<WorkmateModel> workmatesModels = new ArrayList<>();
        if(!workmatesResults.isEmpty()) {
            for(Workmate w: workmatesResults) {
                WorkmateModel workmateModel = this.decorWorkmate(w);
                workmatesModels.add(workmateModel);
            }
        }

        return workmatesModels;
    }


    private WorkmateModel decorWorkmate(Workmate workmate) {
        WorkmateModel workmateModel = new WorkmateModel(workmate);
        List<String> selectionResults = new ArrayList<>();
        try {
            this.getWorkmateSelectionUseCase.handle(workmate.getId()).subscribe(selectionResults::add);
        } catch (NotFoundException e) {
            Log.e(this.TAG, "get workmate selection: not found: " + e.getClass().getName());
        }
        if(!selectionResults.isEmpty()) {
            String restaurantId = selectionResults.get(0);
            try {
                List<Restaurant> restaurantResults = new ArrayList<>();
                this.getRestaurantByIdUseCase.handle(restaurantId).subscribe(restaurantResults::add);
                workmateModel.setSelection(restaurantResults.get(0));
            } catch (NotFoundException e) {
                Log.e(this.TAG, "get restaurant by id: not found: " + e.getClass().getName());
            }
        }

        return workmateModel;

    }

     */
}
