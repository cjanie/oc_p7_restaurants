package com.android.go4lunch.businesslogic.gateways;

import com.android.go4lunch.businesslogic.entities.Selection;

import java.util.List;

import io.reactivex.Observable;

public interface VisitorGateway {
    void addSelection(Selection selection);

    void removeSelection(String workmateId);

    Observable<List<Selection>> getSelections();

    Observable<List<Selection>> getVisitors(String restaurantId);

}