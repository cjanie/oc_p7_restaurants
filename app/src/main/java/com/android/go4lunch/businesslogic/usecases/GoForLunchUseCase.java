package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;

import java.util.ArrayList;
import java.util.List;

public class GoForLunchUseCase {

    private final VisitorGateway visitorGateway;

    public GoForLunchUseCase(VisitorGateway visitorGateway) {
        this.visitorGateway = visitorGateway;
    }

    public void handle(String restaurantId, String workmateId, String restaurantName) {

        Selection newSelection = new Selection(restaurantId, workmateId);
        newSelection.setRestaurantName(restaurantName);

        Selection workmateSelection = this.getWorkmateSelection(workmateId);

        if(workmateSelection == null)
            this.visitorGateway.addSelection(newSelection);
        else {
            if(workmateSelection.getRestaurantId().equals(restaurantId)) {
                this.visitorGateway.removeSelection(workmateSelection.getId());
            } else {
                this.visitorGateway.removeSelection(workmateSelection.getId());
                this.visitorGateway.addSelection(newSelection);
            }
        }
    }

    private List<Selection> getSelections() {
        List<Selection> selectionsResults = new ArrayList<>();
        this.visitorGateway.getSelections().subscribe(selectionsResults::addAll);
        return selectionsResults;
    }

    private Selection getWorkmateSelection(String workmateId) {
        List<Selection> selectionsResults = this.getSelections();
        if(!selectionsResults.isEmpty()) {
            for(Selection selection: selectionsResults) {
                if(selection.getWorkmateId().equals(workmateId))
                    return selection;
            }
        }
        return null;
    }
}
