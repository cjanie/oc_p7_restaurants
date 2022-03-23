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

    public Observable<List<Workmate>> handle() {
        return this.workmateGateway.getWorkmates();
    }
}
