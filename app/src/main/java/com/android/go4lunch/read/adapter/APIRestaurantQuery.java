package com.android.go4lunch.read.adapter;

import com.android.go4lunch.read.businesslogic.gateways.RestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class APIRestaurantQuery implements RestaurantQuery {
    @Override
    public List<Restaurant> findAll() {
        return new ArrayList<>(); // TODO
    }
}
