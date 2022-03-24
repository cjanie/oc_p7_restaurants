package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetWorkmateSelectionUseCase {

    private VisitorsGateway visitorsGateway;

    public GetWorkmateSelectionUseCase(VisitorsGateway visitorsGateway) {
        this.visitorsGateway = visitorsGateway;
    }

    public Observable<String> handle(String workmateId) throws NotFoundException {
        String selectionId = null;
        List<Selection> selectionsResults = new ArrayList<>();
        this.visitorsGateway.getSelections().subscribe(selectionsResults::addAll);
        if(!selectionsResults.isEmpty()) {
            for(Selection s: selectionsResults) {
                if(s.getWorkmateId() == workmateId) {
                    selectionId = s.getRestaurantId();
                    return Observable.just(selectionId);
                }
            }
        }
        throw new NotFoundException();
    }


}
