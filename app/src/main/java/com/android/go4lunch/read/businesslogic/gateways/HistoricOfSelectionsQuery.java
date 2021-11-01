package com.android.go4lunch.read.businesslogic.gateways;

import com.android.go4lunch.models.Restaurant;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public interface HistoricOfSelectionsQuery {

    Observable<List<Map<Restaurant, Integer>>> findAll();

    Observable<Integer> getCount(Restaurant restaurant);

}
