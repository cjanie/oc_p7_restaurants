package com.android.go4lunch.gateways;

import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import java.util.List;

public interface VisitorsGateway {
    void addSelection(Selection selection);

    void removeSelection(String workmateId);

    List<Selection> getSelections();

    List<Selection> getVisitors(String restaurantId);

}