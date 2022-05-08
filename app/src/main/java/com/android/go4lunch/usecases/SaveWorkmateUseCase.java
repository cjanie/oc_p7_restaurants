package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.WorkmateGateway;
import com.android.go4lunch.models.Workmate;

public class SaveWorkmateUseCase {

    private WorkmateGateway workmateGateway;

    public SaveWorkmateUseCase(WorkmateGateway workmateGateway) {
        this.workmateGateway = workmateGateway;
    }

    public void handle(Workmate workmate) {
       this.workmateGateway.saveWorkmate(workmate);
    }
}
