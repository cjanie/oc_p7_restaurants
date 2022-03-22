package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class IsTheCurrentSelectionUseCase {

    private VisitorsGateway visitorsGateway;

    public IsTheCurrentSelectionUseCase(VisitorsGateway visitorsGateway) {
        this.visitorsGateway = visitorsGateway;
    }

    public Observable<Boolean> handle(String restaurantId, String workmateId) {
        List<Selection> selectionsResult = new ArrayList<>();
        this.visitorsGateway.getSelections().subscribe(selectionsResult::addAll);
        for(Selection s: selectionsResult) {
            if(s.getRestaurantId() == restaurantId
                    && s.getWorkmateId() == workmateId) {
                return Observable.just(true);
            }
        }
        return Observable.just(false);
    }

}

