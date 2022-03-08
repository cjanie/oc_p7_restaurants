package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.HistoricOfSelectionsGateway;
import com.android.go4lunch.models.Restaurant;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class HistoricOfSelectionsGatewayImpl implements HistoricOfSelectionsGateway {
    @Override
    public Observable<List<Map<Restaurant, Integer>>> findAll() {
        return null;
    }

    @Override
    public Observable<Integer> getCount(Restaurant restaurant) {
        return null;
    }

    @Override
    public void add(Map<Restaurant, Integer> map) {

    }
}
