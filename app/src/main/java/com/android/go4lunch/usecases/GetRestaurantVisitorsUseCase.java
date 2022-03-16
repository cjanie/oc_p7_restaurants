package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.List;

public class GetRestaurantVisitorsUseCase {

    private VisitorsGateway visitorsGateway;

    public GetRestaurantVisitorsUseCase(VisitorsGateway visitorsGateway) {
        this.visitorsGateway = visitorsGateway;
    }

    public List<Workmate> handle(String restaurantId) {
        List<Workmate> visitors = new ArrayList<>();
        if(!this.visitorsGateway.getSelections().isEmpty()) {
            for(Selection selection: this.visitorsGateway.getSelections()) {
                if(selection.getRestaurantId().equals(restaurantId)) {
                    visitors.add(new Workmate(selection.getWorkmateName()));
                }
            }
        }

        return visitors;
    }

}

