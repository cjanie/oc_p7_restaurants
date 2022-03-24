package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.List;

public class LikeUseCase {

    private final VisitorsGateway visitorsGateway;

    public LikeUseCase(VisitorsGateway visitorsGateway) {
        this.visitorsGateway = visitorsGateway;
    }

    public void handle(String restaurantId, String workmateId) {

        List<Selection> selectionsResults = new ArrayList<>();

        this.visitorsGateway.getSelections().subscribe(selectionsResults::addAll);

        Selection selection = new Selection(restaurantId, workmateId);

        if(selectionsResults.isEmpty()) {
            this.visitorsGateway.addSelection(selection);
        } else {
            boolean found = false;
            for(int i=0; i<selectionsResults.size(); i++) {
                if(selectionsResults.get(i).getWorkmateId().equals(workmateId)) {
                    found = true;
                    if(selectionsResults.get(i).getRestaurantId().equals(restaurantId)) {
                        this.visitorsGateway.removeSelection(selectionsResults.get(i).getWorkmateId());
                        break;
                    } else {
                        this.visitorsGateway.removeSelection(selectionsResults.get(i).getWorkmateId());
                        this.visitorsGateway.addSelection(selection);
                        break;
                    }
                }

            }
            if(!found) {
                this.visitorsGateway.addSelection(selection);
            }

        }






        /*
        if (isTheCurrentSelection == false) {
            this.visitorsGateway.addSelection(selection);

        } else {

        }

         */
    }
}
