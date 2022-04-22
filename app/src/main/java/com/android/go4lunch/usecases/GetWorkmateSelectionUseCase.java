package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetWorkmateSelectionUseCase {

    private VisitorGateway visitorGateway;

    public GetWorkmateSelectionUseCase(VisitorGateway visitorGateway) {
        this.visitorGateway = visitorGateway;
    }

    public Observable<String> handle(String workmateId) throws NotFoundException {
        String selectionId = null;
        List<Selection> selectionsResults = new ArrayList<>();
        this.visitorGateway.getSelections().subscribe(selectionsResults::addAll);
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
