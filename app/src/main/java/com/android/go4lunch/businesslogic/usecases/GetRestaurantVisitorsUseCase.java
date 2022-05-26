package com.android.go4lunch.businesslogic.usecases;

import android.util.Log;

import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantVisitorsUseCase {

    private final String TAG = "GET VISITORS USE CASE";

    private VisitorGateway visitorGateway;

    public GetRestaurantVisitorsUseCase(VisitorGateway visitorGateway) {
        this.visitorGateway = visitorGateway;
    }

    public Observable<List<Workmate>> handle(String restaurantId) {
        return this.getVisitors(restaurantId);
    }

    private Observable<List<Workmate>> getVisitors(String restaurantId) {
        return this.visitorGateway.getSelections().map(selections -> {
            List<Workmate> visitors = new ArrayList<>();
            if(!selections.isEmpty()) {
                for(Selection selection: selections) {
                    if(selection.getRestaurantId().equals(restaurantId)) {
                        Workmate visitor = new Workmate("TODO IN GET VISITORS USE CASE");
                        visitor.setId(selection.getWorkmateId());
                        visitors.add(visitor);
                    }
                }
            }

            return visitors;
        });
    }
}

