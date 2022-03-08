package com.android.go4lunch.gateways;

import com.android.go4lunch.models.Restaurant;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public interface HistoricOfSelectionsGateway {

    Observable<List<Map<Restaurant, Integer>>> findAll();

    Observable<Integer> getCount(Restaurant restaurant);

    void add(Map<Restaurant, Integer> map);

}
