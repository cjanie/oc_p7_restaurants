package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.WorkmateGateway;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.models.WorkmateModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetWorkmatesUseCase {

    private WorkmateGateway workmateGateway;

    public GetWorkmatesUseCase(WorkmateGateway workmateGateway) {
        this.workmateGateway = workmateGateway;
    }

    public Observable<List<WorkmateModel>> list() {
        return this.workmateGateway.getWorkmates().map(workmates -> {
            List<WorkmateModel> workmateModels = new ArrayList<>();
            if(!workmates.isEmpty()) {
                for(Workmate workmate: workmates) {
                    WorkmateModel workmateModel = new WorkmateModel(workmate);
                    workmateModels.add(workmateModel);
                }
            }
            return workmateModels;
        });
    }
}
