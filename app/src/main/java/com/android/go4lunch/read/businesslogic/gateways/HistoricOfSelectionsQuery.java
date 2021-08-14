package com.android.go4lunch.read.businesslogic.gateways;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

public interface HistoricOfSelectionsQuery {

    int getCount(Restaurant restaurant);

}
