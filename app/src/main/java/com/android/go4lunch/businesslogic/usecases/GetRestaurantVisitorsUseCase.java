package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.models.WorkmateModel;

import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantVisitorsUseCase {

    private final String TAG = "GET VISITORS USE CASE";

    private VisitorGateway visitorGateway;

    private WorkmateModel workmateModel;

    public GetRestaurantVisitorsUseCase(VisitorGateway visitorGateway) {
        this.visitorGateway = visitorGateway;
        this.workmateModel = new WorkmateModel();
    }

    public Observable<List<Workmate>> handle(String restaurantId) {
        return this.extractVisitorsByRestaurantId(restaurantId);
    }

    private Observable<List<Workmate>> extractVisitorsByRestaurantId(String restaurantId) {
        return this.getSelections()
                .map(selections ->
                        this.workmateModel.extractVisitorsByRestaurantId(selections, restaurantId)

        );
    }

    private Observable<List<Selection>> getSelections() {
        return this.visitorGateway.getSelections();
    }

}

