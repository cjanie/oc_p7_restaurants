package com.android.go4lunch.read.businesslogic.gateways;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.util.List;

public interface RestaurantQuery {

    public List<Restaurant> findAll();
}
