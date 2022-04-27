package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

public class GoForLunchUseCase {

    private final VisitorGateway visitorGateway;

    public GoForLunchUseCase(VisitorGateway visitorGateway) {
        this.visitorGateway = visitorGateway;
    }

    public void handle(String restaurantId, String workmateId) {

        List<Selection> selectionsResults = new ArrayList<>();

        this.visitorGateway.getSelections().subscribe(selectionsResults::addAll);

        Selection selection = new Selection(restaurantId, workmateId);

        if(selectionsResults.isEmpty()) {
            this.visitorGateway.addSelection(selection);
        } else {
            boolean found = false;
            for(int i=0; i<selectionsResults.size(); i++) {
                if(selectionsResults.get(i).getWorkmateId().equals(workmateId)) {
                    found = true;
                    if(selectionsResults.get(i).getRestaurantId().equals(restaurantId)) {
                        this.visitorGateway.removeSelection(selectionsResults.get(i).getWorkmateId());
                    } else {
                        this.visitorGateway.removeSelection(selectionsResults.get(i).getWorkmateId());
                        this.visitorGateway.addSelection(selection);
                    }
                    break;
                }
            }
            if(!found) {
                this.visitorGateway.addSelection(selection);
            }

        }

    }
}
