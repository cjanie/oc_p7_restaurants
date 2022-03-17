package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantVisitorsUseCase {

    private VisitorsGateway visitorsGateway;

    public GetRestaurantVisitorsUseCase(VisitorsGateway visitorsGateway) {
        this.visitorsGateway = visitorsGateway;
    }

    public Observable<List<Workmate>> handle(String restaurantId) {
        return this.getVisitors(restaurantId);
    }

    private Observable<List<Workmate>> getVisitors(String restaurantId) {
        return this.visitorsGateway.getSelections().map(selections -> {
            List<Workmate> visitors = new ArrayList<>();
            if(!selections.isEmpty()) {
                for(Selection selection: selections) {
                    if(selection.getRestaurantId().equals(restaurantId)) {
                        visitors.add(new Workmate(selection.getWorkmateName()));
                    }
                }
            }
            return visitors;
        });
    }
}

