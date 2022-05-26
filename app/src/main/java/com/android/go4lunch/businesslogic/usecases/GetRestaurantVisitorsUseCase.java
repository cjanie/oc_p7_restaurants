package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.models.WorkmateEntityModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantVisitorsUseCase {

    private final String TAG = "GET VISITORS USE CASE";

    private VisitorGateway visitorGateway;

    private WorkmateEntityModel workmateEntityModel;

    public GetRestaurantVisitorsUseCase(VisitorGateway visitorGateway) {
        this.visitorGateway = visitorGateway;
        this.workmateEntityModel = new WorkmateEntityModel();
    }

    public Observable<List<Workmate>> handle(String restaurantId) {
        return this.getVisitors(restaurantId);
    }

    private Observable<List<Workmate>> getVisitors(String restaurantId) {
        return this.visitorGateway.getSelections()
                .map(selections ->
                        this.findVisitorsInSelectionsByRestaurantId(selections, restaurantId)

        );
    }

    private List<Workmate> findVisitorsInSelectionsByRestaurantId(List<Selection> selections, String restaurantId) {
        List<Workmate> visitors = new ArrayList<>();
        if (!selections.isEmpty()) {
            for (Selection selection : selections) {
                if (selection.getRestaurantId().equals(restaurantId)) {
                    Workmate visitor = this.workmateEntityModel.createVisitor(
                            selection.getWorkmateId(),
                            selection.getWorkmateName(),
                            selection.getWorkmateUrlPhoto()
                    );
                    visitors.add(visitor);
                }
            }
        }
        return visitors;
    }

}

