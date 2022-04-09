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
        Disposable disposable = this.getWorkmatesUseCase.handle().subscribe(
            workmates -> {
                List<WorkmateModel> workmateModels = new ArrayList<>();
                for(Workmate w: workmates) {
                    WorkmateModel workmateModel = this.decorWorkmate(w);
                    workmateModels.add(workmateModel);
                }
                this.workmates.postValue(workmateModels);
            },
                Throwable::printStackTrace
        );
        return this.workmates;
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

}
