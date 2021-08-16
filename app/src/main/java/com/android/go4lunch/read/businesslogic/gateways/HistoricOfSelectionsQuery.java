package com.android.go4lunch.read.businesslogic.gateways;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.util.List;
import java.util.Map;

public interface HistoricOfSelectionsQuery {

    List<Map<Restaurant, Integer>> findAll();

    int getCount(Restaurant restaurant);

}
