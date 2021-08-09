package com.android.go4lunch.read.adapter;

import com.android.go4lunch.read.businesslogic.gateways.RestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRestaurantQuery implements RestaurantQuery {

    List<Restaurant> restaurants;

    public InMemoryRestaurantQuery() {
        this.restaurants = new ArrayList<>();
    }

    @Override
    public List<Restaurant> findAll() {
        return this.restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
