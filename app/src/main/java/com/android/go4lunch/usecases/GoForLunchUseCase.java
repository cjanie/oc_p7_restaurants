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

        Selection newSelection = new Selection(restaurantId, workmateId);

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
