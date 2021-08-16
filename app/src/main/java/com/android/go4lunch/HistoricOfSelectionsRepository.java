package com.android.go4lunch;

import com.android.go4lunch.read.businesslogic.gateways.HistoricOfSelectionsQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.util.Map;

public interface HistoricOfSelectionsRepository extends HistoricOfSelectionsQuery {
    void add(Map<Restaurant, Integer> map);
}
