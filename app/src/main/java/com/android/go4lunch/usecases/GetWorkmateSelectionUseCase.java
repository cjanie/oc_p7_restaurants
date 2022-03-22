package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetWorkmateSelectionUseCase {

    private VisitorsGateway visitorsGateway;

    public GetWorkmateSelectionUseCase(VisitorsGateway visitorsGateway) {
        this.visitorsGateway = visitorsGateway;
    }

    public Observable<Selection> handle(String workmateId) {
        Selection selection = null;
        List<Selection> selectionsResults = new ArrayList<>();
        this.visitorsGateway.getSelections().subscribe(selectionsResults::addAll);
        if(!selectionsResults.isEmpty()) {
            for(Selection s: selectionsResults) {
                if(s.getWorkmateId() == workmateId) {
                    selection = s;
                    return Observable.just(selection);
                }
            }
        }
        return null;
    }


}
