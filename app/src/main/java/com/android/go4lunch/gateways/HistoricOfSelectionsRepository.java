package com.android.go4lunch.gateways;

import com.android.go4lunch.gateways.HistoricOfSelectionsQuery;
import com.android.go4lunch.models.Restaurant;

import java.util.Map;

public interface HistoricOfSelectionsRepository extends HistoricOfSelectionsQuery {
    void add(Map<Restaurant, Integer> map);
}
