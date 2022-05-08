package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantVisitorsUseCase {

    private VisitorGateway visitorGateway;

    public GetRestaurantVisitorsUseCase(VisitorGateway visitorGateway) {
        this.visitorGateway = visitorGateway;
    }

    public Observable<List<String>> handle(String restaurantId) {
        return this.getVisitorsId(restaurantId);
    }

    private Observable<List<String>> getVisitorsId(String restaurantId) {
        return this.visitorGateway.getSelections().map(selections -> {
            List<String> visitorsId = new ArrayList<>();
            if(!selections.isEmpty()) {
                for(Selection selection: selections) {
                    if(selection.getRestaurantId().equals(restaurantId)) {
                        visitorsId.add(selection.getWorkmateId());
                    }
                }
            }
            return visitorsId;
        });
    }
}

