package com.android.go4lunch.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.usecases.exceptions.NotFoundException;
import com.android.go4lunch.usecases.models.WorkmateModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class WorkmatesViewModel extends ViewModel {

    private final String TAG = "WORKMATES_VIEW_MODEL";

    private final GetWorkmatesUseCase getWorkmatesUseCase;

    private final GetWorkmateSelectionUseCase getWorkmateSelectionUseCase;

    private final MutableLiveData<List<WorkmateModel>> workmates;

    public WorkmatesViewModel(
            GetWorkmatesUseCase getWorkmatesUseCase,
            GetWorkmateSelectionUseCase getWorkmateSelectionUseCase

    ) {
        this.getWorkmatesUseCase = getWorkmatesUseCase;
        this.getWorkmateSelectionUseCase = getWorkmateSelectionUseCase;

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

    private Observable<List<WorkmateModel>> decorWorkmates() {
        return this.getWorkmatesUseCase.handle().flatMap(
                workmates -> {
                    Log.d(TAG, "-- decor workmates -- workmates size: " + workmates.size());
                    return Observable.fromIterable(workmates).flatMap(
                        workmate -> decorWorkmateAsModel(workmate)
                    ).toList().toObservable();
                });
    }

    private Observable<WorkmateModel> decorWorkmateAsModel(Workmate workmate) {
        return this.getWorkmateSelectionUseCase.handle(workmate.getId()).map(selection -> {
            Log.d(TAG, "-- decor workmate as model -- workmate selection: " + selection);

            WorkmateModel workmateModel = new WorkmateModel(workmate);
            Restaurant selected = new Restaurant(selection.getRestaurantName());
            selected.setId(selection.getRestaurantId());
            workmateModel.setSelection(selected);
            return workmateModel;
        });
    }

    private WorkmateModel decorWorkmate(Workmate workmate) {
        WorkmateModel workmateModel = new WorkmateModel(workmate);
        List<Selection> selectionResults = new ArrayList<>();
        this.getWorkmateSelectionUseCase.handle(workmate.getId()).subscribe(selection -> {
            Log.d(TAG, "-- decor workmate -- workmate selection : " + selection);

        });
        this.getWorkmateSelectionUseCase.handle(workmate.getId()).subscribe(selectionResults::add);

        if(!selectionResults.isEmpty()) {
            Selection workmateSelection = selectionResults.get(0);
            Restaurant selected = new Restaurant(workmateSelection.getRestaurantName());
            selected.setId(workmateSelection.getId());
            workmateModel.setSelection(new Restaurant(workmateSelection.getRestaurantName()));
        }
        return workmateModel;
    }

}
