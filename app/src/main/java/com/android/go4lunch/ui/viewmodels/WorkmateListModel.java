package com.android.go4lunch.ui.viewmodels;

import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.usecases.models.WorkmateModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class WorkmateListModel {

    private String TAG = "WORKMATE LIST MODEL";

    private GetWorkmatesUseCase getWorkmatesUseCase;
    private GetWorkmateSelectionUseCase getWorkmateSelectionUseCase;

    public WorkmateListModel(
            GetWorkmatesUseCase getWorkmatesUseCase,
            GetWorkmateSelectionUseCase getWorkmateSelectionUseCase
    ) {
        this.getWorkmatesUseCase = getWorkmatesUseCase;
        this.getWorkmateSelectionUseCase = getWorkmateSelectionUseCase;
    }

    private Observable<List<Workmate>> listWorkmates() {
        return this.getWorkmatesUseCase.handle();
    }

    private Observable<List<WorkmateModel>> getformatedWorkmatesAsModels() {
        return this.listWorkmates().map(workmates -> {
            List<WorkmateModel> workmateModels = new ArrayList<>();
            if(!workmates.isEmpty()) {
                for(Workmate w: workmates) {
                    WorkmateModel workmateModel = new WorkmateModel(w);
                    workmateModels.add(workmateModel);
                }
            }

            return workmateModels;
        });
    }

    private WorkmateModel decorWorkmateWithSelection(WorkmateModel workmateModel) {
        WorkmateModel workmateModelCopy = workmateModel;
        this.getWorkmateSelectionUseCase.handle(workmateModel.getWorkmate().getId()).subscribe(selection -> {

            Restaurant restaurant = new Restaurant(selection.getRestaurantName());
            restaurant.setId(selection.getRestaurantId());
            workmateModelCopy.setSelection(restaurant);

        });
        return workmateModelCopy;
    }

    public Observable<List<WorkmateModel>> getWorkmatesWithTheirSelectedRestaurants() {
        return this.getformatedWorkmatesAsModels()
                .map(workmateModels -> {
                            List<WorkmateModel> workmatesWithTheirSelections = new ArrayList<>();
                            if(!workmateModels.isEmpty()) {
                                for(WorkmateModel wm: workmateModels) {
                                    workmatesWithTheirSelections.add(this.decorWorkmateWithSelection(wm));
                                }
                            }
                            return workmatesWithTheirSelections;
                        }
                );
    }

}
