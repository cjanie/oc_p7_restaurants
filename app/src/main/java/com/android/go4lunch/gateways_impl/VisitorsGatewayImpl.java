package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class VisitorsGatewayImpl implements VisitorsGateway {
    @Override
    public void addSelection(Selection selection) {

    }

    @Override
    public void removeSelection(String workmateId) {

    }

    @Override
    public Observable<List<Selection>> getSelections() {
        return Observable.just(new Mock().selections());
    }

    @Override
    public Observable<List<Selection>> getVisitors(String restaurantId) {
        return Observable.just(new ArrayList<>());
    }
}
