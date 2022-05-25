package com.android.go4lunch.businesslogic.usecases;

import android.util.Log;

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

    public Observable<List<String>> handle(String restaurantId) {
        return this.getVisitorsId(restaurantId);
    }

    private Observable<List<String>> getVisitorsId(String restaurantId) {
        return this.visitorGateway.getSelections().map(selections -> {
            Log.d(TAG, "-- getVisitorsId per restaurant -- selections size: " + selections.size());

            List<String> visitorsIds = new ArrayList<>();
            if(!selections.isEmpty()) {
                for(Selection selection: selections) {
                    if(selection.getRestaurantId().equals(restaurantId)) {
                        visitorsIds.add(selection.getWorkmateId());
                    }
                }
            }
            Log.d(TAG, "-- getVisitorsId per restaurant -- visitorsIds size: " + visitorsIds.size());
            return visitorsIds;
        });
    }
}

