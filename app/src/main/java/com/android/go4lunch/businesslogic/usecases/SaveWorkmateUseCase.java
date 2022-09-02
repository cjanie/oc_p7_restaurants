package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.gateways.WorkmateGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;

public class SaveWorkmateUseCase {

    private WorkmateGateway workmateGateway;

    public SaveWorkmateUseCase(WorkmateGateway workmateGateway) {
        this.workmateGateway = workmateGateway;
    }

    public void handle(Workmate workmate) {
       this.workmateGateway.saveWorkmate(workmate);
    }
}
