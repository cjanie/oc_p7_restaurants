package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.gateways.HistoricOfSelectionsQuery;

public class RetrieveSelectionsCountForOneRestaurant {

    private HistoricOfSelectionsQuery historicOfSelectionsQuery;

    private Restaurant restaurant;

    public RetrieveSelectionsCountForOneRestaurant(Restaurant restaurant, HistoricOfSelectionsQuery historicOfSelectionsQuery) {
        this.restaurant = restaurant;
        this.historicOfSelectionsQuery = historicOfSelectionsQuery;
    }

    public int countSelections() {
        return this.historicOfSelectionsQuery.getCount(restaurant);
    }
}
