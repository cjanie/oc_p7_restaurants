package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.businesslogic.models.WorkmateModel;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesViewModel extends ViewModel {

    private final String TAG = "WORKMATES_VIEW_MODEL";

    private final GetWorkmatesUseCase getWorkmatesUseCase;

    private final GetWorkmateSelectionUseCase getWorkmateSelectionUseCase;

    private final WorkmateListModel workmateListModel;

    private final MutableLiveData<List<WorkmateModel>> workmates;

    public WorkmatesViewModel(
            GetWorkmatesUseCase getWorkmatesUseCase,
            GetWorkmateSelectionUseCase getWorkmateSelectionUseCase

    ) {
        this.getWorkmatesUseCase = getWorkmatesUseCase;
        this.getWorkmateSelectionUseCase = getWorkmateSelectionUseCase;

        this.workmateListModel = new WorkmateListModel(this.getWorkmatesUseCase, this.getWorkmateSelectionUseCase);
        this.workmates = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<WorkmateModel>> getWorkmates() {
        this.workmateListModel.getWorkmatesWithTheirSelectedRestaurants().subscribe(
                workmateModels -> this.workmates.postValue(workmateModels));
        return this.workmates;
    }

}
