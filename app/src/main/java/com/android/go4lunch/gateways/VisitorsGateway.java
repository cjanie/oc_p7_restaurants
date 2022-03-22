package com.android.go4lunch.gateways;

import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import java.util.List;

import io.reactivex.Observable;

public interface VisitorsGateway {
    void addSelection(Selection selection);

    void removeSelection(String workmateId);

    Observable<List<Selection>> getSelections();

    Observable<List<Selection>> getVisitors(String restaurantId);

}